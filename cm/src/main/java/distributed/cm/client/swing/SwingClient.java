package distributed.cm.client.swing;

import ch.qos.logback.core.net.server.Client;
import distributed.cm.client.ClientSocketManager;
import distributed.cm.server.domain.*;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class SwingClient {
    private static ClientSocketManager clientSocketManager;
    private static SwingClient client = new SwingClient();
    public DrawingPanel drawingPanel = new DrawingPanel();

    private SwingClient() {
        clientSocketManager = new ClientSocketManager();
    }

    public static SwingClient getClient() {
        return client;
    }

    public void panelDraw(Draw draw){
        drawingPanel.receivedMessage(draw);
    }

    public static void main(String[] args) throws IOException {
        SwingClient swingClient = SwingClient.getClient();
        String title = swingClient.nameSetting();
        //swing 실행
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("title");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(swingClient.drawingPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    class DrawingPanel extends JPanel { //drawing panel을 만든다

        private ArrayList<SwingShape> shapes = new ArrayList<>();
        private int shapeIndex = 0;
        private int startX, startY, endX, endY; // 시작점, 끝점
        private int width, height;
        private DrawingMode drawingMode; // drawingMode는 pen, rec, cir, text이 있다
        private boolean allowColorButton = false;

        public DrawingPanel(){
            setPreferredSize(new Dimension(400,400)); // 패널 사이즈 조절

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) { // 마우스를 클릭했을 때 발생하는 이벤트
                    startX = e.getX();
                    startY = e.getY();

                    if(drawingMode == DrawingMode.TEXTBOX){ // text인 경우 팝업을 통해 입력받고 그린다
                        SwingText text = new SwingText(startX, startY);
                        Graphics g = getGraphics();
                        text.drawing(g);
                        shapes.add(text);

                        clientSocketManager.text(text.getInput(), startX, startY);
                    }

                    if(!shapes.isEmpty()){
                        for (int i = 0; i < shapes.size(); i++) {
                            SwingShape shape = shapes.get(i);
                            if (shape.contains(e.getX(), e.getY())) {
                                shapeIndex = i;
                                clientSocketManager.shape(i,i,i,i);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e){ // 마우스를 뗐을때 drawing mode가 rec or cir일 경우 도형을 그린다
                    endX = e.getX();
                    endY = e.getY();

                    if(drawingMode == DrawingMode.RECTANGLE || drawingMode == DrawingMode.CIRCLE){
                        Graphics g = getGraphics();

                        if(drawingMode == DrawingMode.RECTANGLE) {
                            SwingRectangle rectangle = new SwingRectangle(startX, startY, endX, endY);
                            rectangle.drawingResize();
                            rectangle.draw(g);
                            shapes.add(rectangle);

                        }else if(drawingMode == DrawingMode.CIRCLE){
                            SwingCircle circle = new SwingCircle(startX, startY, endX, endY);
                            circle.drawingResize();
                            circle.draw(g);
                            shapes.add(circle);

                        }
                        allowColorButton = true;
                        clientSocketManager.shape(startX, startY, endX, endY);
                    }

                }
            });


            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    //드래그 했을때 drawing mode가 pencil일 경우 선을 그린다
                    if(drawingMode == DrawingMode.PENCIL){
                        Graphics g = getGraphics();
                        SwingPencil pencil = new SwingPencil(startX, startY, e.getX(), e.getY());
                        pencil.draw(g);
                        shapes.add(pencil);

                        startX = pencil.getStartX();
                        startY = pencil.getStartY();
                        clientSocketManager.draw(startX, startY, e.getX(), e.getY());
                    }
                }
            });
            
            //버튼

            JButton pencilButton = new JButton("Pencil"); // 각각 버튼을 생성하고 drawingMode를 지정한다
            pencilButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    allowColorButton = false;
                    drawingMode = DrawingMode.PENCIL;
                }
            });

            JButton RecButton = new JButton("Rectangle");
            RecButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { drawingMode = DrawingMode.RECTANGLE; }
            });

            JButton cirButton = new JButton("Circle");
            cirButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { drawingMode = DrawingMode.CIRCLE;}
            });

            JButton textButton = new JButton("Textbox");
            textButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    allowColorButton = false;
                    drawingMode = DrawingMode.TEXTBOX;
                }
            });

            add(pencilButton); // 버튼을 패널에 부착한다
            add(RecButton);
            add(cirButton);
            add(textButton);

            //도형 수정
            JButton fullcolorButton = new JButton("FullColor");
            fullcolorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(allowColorButton){
                        Color newColor = JColorChooser.showDialog(fullcolorButton, "Choose full Color", fullcolorButton.getBackground());
                        if (newColor != null) {
                            currentFillColor = newColor;
                            repaint();
                        }
                    }
                }
            });

            JButton linecolorButton = new JButton("LineColor");
            linecolorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(allowColorButton){
                        Color newColor = JColorChooser.showDialog(linecolorButton, "Choose line Color", linecolorButton.getBackground());
                        if (newColor != null) {
                            currentLineColor = newColor;
                            repaint();
                        }
                    }
                }
            });

            JButton boldButton = new JButton("Bold");

            boldButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(allowColorButton) {
                        String input = JOptionPane.showInputDialog(null, "Enter line width:", "Line Width", JOptionPane.PLAIN_MESSAGE);
                        if (input != null && !input.isEmpty()) {
                            try {
                                int lineWidth = Integer.parseInt(input);
                                currentlineWidth = lineWidth;
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            });


            add(fullcolorButton);
            add(linecolorButton);
            add(boldButton);

        }

        private int currentlineWidth = 1;
        private Color currentFillColor = Color.black;
        private Color currentLineColor = Color.black;
        @Override
        protected void paintComponent(Graphics g) { // error
            if(shapes.size() == 0) return;

            super.paintComponent(g);

            if(shapes.size() == 0) return;
            else{
                for (SwingShape shape : shapes) {
                    shape.draw(g);
                }
            }

            SwingShape parent = shapes.get(shapeIndex);

            if(parent instanceof SwingCircle){
                SwingCircle cir = (SwingCircle) parent;
                cir.setLineWidth(currentlineWidth);
                cir.setFillColor(currentFillColor);
                cir.setLineColor(currentLineColor);
                cir.draw(g);
            }else if(parent instanceof SwingRectangle){
                SwingRectangle rec = (SwingRectangle) parent;
                rec.setLineWidth(currentlineWidth);
                rec.setFillColor(currentFillColor);
                rec.setLineColor(currentLineColor);
                rec.draw(g);
            }
        }

        public void receivedMessage(Draw draw){
            if(draw instanceof Line){
                Line line = (Line) draw;
                shapes.add(new SwingPencil(line.getX1(), line.getY1(), line.getX2(), line.getY2()));
            }else if(draw instanceof Circle){
                Circle cir = (Circle) draw;
                shapes.add(new SwingCircle(cir.getX1(), cir.getY1(), cir.getX2(), cir.getY2()));
            }else if(draw instanceof Square){
                Square square = (Square) draw;
                shapes.add(new SwingRectangle(square.getX1(), square.getY1(), square.getX2(), square.getY2()));
            }else if(draw instanceof TextBox){
                TextBox text = (TextBox) draw;
                shapes.add(new SwingText(text.getX1(), text.getY1()));
            }
            repaint();
        }

    }
    enum DrawingMode {PENCIL, RECTANGLE, CIRCLE, TEXTBOX}

    public String nameSetting() { // panel생성 전에 사용자 이름을 입력받는다
        String usrName = null;
        while (true) {
            usrName = JOptionPane.showInputDialog("사용자 이름을 입력해주세요 :");
            if (usrName != null) { // OK를 눌렀을 때
                if (usrName.trim().isEmpty()) { //빈칸일 경우 메세지를 띄우고 다시 입력받는다
                    JOptionPane.showMessageDialog(null, "사용자 이름을 입력해주세요", "경고",
                            JOptionPane.WARNING_MESSAGE);
                } else { // 빈칸이 아닐 경우 사용자 이름을 반환한다
                    break;
                }
            } else { // 이외의 버튼을 눌렀을 때
                System.exit(0);
            }

        }
        return usrName;
    }
}