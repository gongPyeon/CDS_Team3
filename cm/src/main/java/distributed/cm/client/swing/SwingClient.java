package distributed.cm.client.swing;

import distributed.cm.client.ClientSocketManager;
import distributed.cm.common.domain.*;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import distributed.cm.common.domain.Circle;
import distributed.cm.common.domain.Line;
import distributed.cm.common.domain.Square;
import distributed.cm.common.domain.TextBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class SwingClient {

    private static final Logger logger = LoggerFactory.getLogger(SwingClient.class);

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

    public void paneldrag(Draw draw) { drawingPanel.draggMessage(draw);}

    public void panelEdit(Draw draw){
        drawingPanel.ModifyMessage(draw);
    }

    public void panelLogin(String usrId){
        drawingPanel.loginMessage(usrId);
    }
    public void panelLogout(String usrId){
        drawingPanel.logoutMessage(usrId);
    }

    public void panelListDraw(List<Draw> draw){ drawingPanel.listDrawMessage(draw); }

    public void panelLock(){ drawingPanel.lockMessage(); }

    public static void main(String[] args) throws IOException {
        SwingClient swingClient = SwingClient.getClient();
        String title = swingClient.nameSetting();
        //swing 실행
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    clientSocketManager.userLogout(title);
                }
            });
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
        private DrawingMode drawingMode; // drawingMode는 pen, rec, cir, text, null이 있다
        private boolean allowColorButton = false;

        public DrawingPanel(){
            setPreferredSize(new Dimension(400,400)); // 패널 사이즈 조절

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) { // 마우스를 클릭했을 때 발생하는 이벤트
                    startX = e.getX();
                    startY = e.getY();
                    //logger.info("drawtype:{}", drawingMode);
                    if(drawingMode == DrawingMode.TEXTBOX){ // text인 경우 팝업을 통해 입력받고 그린다
                        SwingText text = new SwingText(startX, startY);
                        Graphics g = getGraphics();
                        text.drawing(g);
                        if(text.getInput() != null) {
                            shapes.add(text);
                            clientSocketManager.text(text.getInput(), startX, startY);
                        }
                    }

                    shapeFind(startX, startY);
                }

                @Override
                public void mouseReleased(MouseEvent e) { // 마우스를 뗐을때 drawing mode가 rec or cir일 경우 도형을 그린다
                    endX = e.getX();
                    endY = e.getY();

                    if (endX == startX && endY == startY)
                        return;

                    if (drawingMode == DrawingMode.RECTANGLE || drawingMode == DrawingMode.CIRCLE) {
                        Graphics g = getGraphics();

                        if (drawingMode == DrawingMode.RECTANGLE) {
                            SwingRectangle rectangle = new SwingRectangle(startX, endX, startY, endY);
                            rectangle.drawingResize();
                            rectangle.draw(g);
                            shapes.add(rectangle);
                            clientSocketManager.rectangle(rectangle.getStartX(), rectangle.getEndX(), rectangle.getStartY(), rectangle.getEndY(), rectangle.getLineWidth(), rectangle.getLineColor(), rectangle.getFillColor(), 4);

                        } else if (drawingMode == DrawingMode.CIRCLE) {
                            SwingCircle circle = new SwingCircle(startX, endX, startY, endY);
                            circle.drawingResize();
                            circle.draw(g);
                            shapes.add(circle);
                            clientSocketManager.circle(circle.getStartX(), circle.getEndX(), circle.getStartY(), circle.getEndY(), circle.getLineWidth(), circle.getLineColor(), circle.getFillColor(), 2);
                        }
                        allowColorButton = false;
                    } else if (drawingMode == DrawingMode.PENCIL) {
                        Graphics g = getGraphics();
                        SwingPencil pencil = new SwingPencil(startX, startY, endX, endY);
                        pencil.draw(g);
                        shapes.add(pencil);
                        clientSocketManager.draw(startX, startY, endX, endY);
                        allowColorButton = false;
                    }
                    repaint();

                }


                });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    endX = e.getX();
                    endY = e.getY();
                    if(drawingMode == DrawingMode.RECTANGLE || drawingMode == DrawingMode.CIRCLE) {
//                        if(drawingMode == DrawingMode.RECTANGLE) drawingMode = DrawingMode.R_DRAG;
//                        else drawingMode = DrawingMode.C_DRAG;
                        //logger.info("StartX: {}, StartY: {}, EndX: {}, EndY: {}", startX, startY, endX, endY);
                        repaint();
                    }
                }
            });

            JButton pencilButton = new JButton("Pencil"); // 각각 버튼을 생성하고 drawingMode를 지정한다
            pencilButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    drawingMode = DrawingMode.PENCIL;
                    startX = 0; startY = 0; endX = 0; endY = 0;
                }
            });

            JButton RecButton = new JButton("Rectangle");
            RecButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingMode = DrawingMode.RECTANGLE;
                    startX = 0; startY = 0; endX = 0; endY = 0;
                }
            });

            JButton cirButton = new JButton("Circle");
            cirButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingMode = DrawingMode.CIRCLE;
                    startX = 0; startY = 0; endX = 0; endY = 0;
                }
            });

            JButton textButton = new JButton("Textbox");
            textButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    drawingMode = DrawingMode.TEXTBOX; //drag = false;
                }
            });

            JButton selectBtn = new JButton("Select");
            selectBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { drawingMode = DrawingMode.SELLECT;}
            });

            JButton storeBtn = new JButton("Store");
            storeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clientSocketManager.store();
                }
            });

            JButton loadBtn = new JButton("load");
            loadBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clientSocketManager.load();
                }
            });

            add(storeBtn);
            add(loadBtn);
            add(pencilButton); // 버튼을 패널에 부착한다
            add(RecButton);
            add(cirButton);
            add(textButton);
            add(selectBtn);

            //도형 수정
            JButton fullcolorButton = new JButton("FullColor");
            fullcolorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(drawingMode == DrawingMode.SELLECT && allowColorButton){
                        Color newColor = JColorChooser.showDialog(fullcolorButton, "Choose full Color", fullcolorButton.getBackground());
                        if (newColor != null) {
                            String hexCode = String.format("#%02X%02X%02X", newColor.getRed(), newColor.getGreen(), newColor.getBlue());
                            currentFillColor = hexCode;
                            Graphics g = getGraphics();
                            setFill(g, false);
                        }
                    }
                    drawingMode = DrawingMode.NULL;
                    allowColorButton = false;
                }
            });

            JButton linecolorButton = new JButton("LineColor");
            linecolorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(drawingMode == DrawingMode.SELLECT && allowColorButton){
                        Color newColor = JColorChooser.showDialog(linecolorButton, "Choose line Color", linecolorButton.getBackground());
                        if (newColor != null) {
                            String hexCode = String.format("#%02X%02X%02X", newColor.getRed(), newColor.getGreen(), newColor.getBlue());
                            currentLineColor = hexCode;
                            Graphics g = getGraphics();
                            setLine(g, false);
                        }
                    }
                    drawingMode = DrawingMode.NULL;
                    allowColorButton = false;
                }
            });

            JButton boldButton = new JButton("Bold");

            boldButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(drawingMode == DrawingMode.SELLECT  && allowColorButton) {
                        String input = JOptionPane.showInputDialog(null, "Enter line width:", "Line Width", JOptionPane.PLAIN_MESSAGE);
                        if (input != null && !input.isEmpty()) {
                            try {
                                int lineWidth = Integer.parseInt(input);
                                if(lineWidth < 0) return;

                                currentlineWidth = lineWidth;
                                Graphics g = getGraphics();
                                setWidth(g, false);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    drawingMode = DrawingMode.NULL;
                    allowColorButton = false;
                }
            });


            add(fullcolorButton);
            add(linecolorButton);
            add(boldButton);

        }

        private int currentlineWidth = 1;

        private String currentFillColor = null;

        private String currentLineColor = "#000000";
        private boolean rec_drag = false, cir_drag = false;
        private int prevStartX = -1, prevStartY = -1, prevEndX = -1, prevEndY = -1;
        private int prevCircleStartX = -1, prevCircleStartY = -1, prevCircleEndX = -1, prevCircleEndY = -1;
        @Override
        protected void paintComponent(Graphics g) { //
            super.paintComponent(g);

            if(shapes.size() != 0) {
                for (SwingShape shape : shapes) {
                    shape.draw(g);
                }
            }

            if(rec_drag) {
                SwingRectangle rectangle = new SwingRectangle(startX, endX, startY, endY);
                rectangle.drawingResize();
                rectangle.draw(g);
                rec_drag = false;
                return;
            }else if(cir_drag) {
                SwingCircle circle = new SwingCircle(startX, endX, startY, endY);
                circle.drawingResize();
                circle.draw(g);
                cir_drag = false;
                return;
            }

            if (drawingMode == DrawingMode.RECTANGLE || drawingMode == DrawingMode.CIRCLE) {
                if (startX == 0 && startY == 0 && endX == 0 && endY == 0)
                    return;

                if (drawingMode == DrawingMode.RECTANGLE) {
                    SwingRectangle rectangle = new SwingRectangle(startX, endX, startY, endY);
                    rectangle.drawingResize();
                    rectangle.draw(g);

                    // 이전 상태와 현재 상태를 비교하여 변경된 경우에만 메시지를 전송합니다.
                    if (startX != prevStartX || startY != prevStartY || endX != prevEndX || endY != prevEndY) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        clientSocketManager.rectangle(rectangle.getStartX(), rectangle.getEndX(), rectangle.getStartY(), rectangle.getEndY(),
                                rectangle.getLineWidth(), rectangle.getLineColor(), rectangle.getFillColor(), 10);

                        // 이전 상태를 현재 상태로 업데이트
                        prevStartX = startX;
                        prevStartY = startY;
                        prevEndX = endX;
                        prevEndY = endY;
                    }

                } else if (drawingMode == DrawingMode.CIRCLE) {
                    SwingCircle circle = new SwingCircle(startX, endX, startY, endY);
                    circle.drawingResize();
                    circle.draw(g);

                    // 이전 상태와 현재 상태를 비교하여 변경된 경우에만 메시지를 전송
                    if (startX != prevCircleStartX || startY != prevCircleStartY || endX != prevCircleEndX || endY != prevCircleEndY) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        clientSocketManager.circle(circle.getStartX(), circle.getEndX(), circle.getStartY(), circle.getEndY(),
                                circle.getLineWidth(), circle.getLineColor(), circle.getFillColor(), 9);

                        // 이전 상태를 현재 상태로 업데이트합니다.
                        prevCircleStartX = startX;
                        prevCircleStartY = startY;
                        prevCircleEndX = endX;
                        prevCircleEndY = endY;
                    }
                }
            }

//            if(drawingMode == DrawingMode.RECTANGLE || drawingMode == DrawingMode.CIRCLE){
//                if( startX == 0 && startY == 0 && endX == 0 && endY == 0)
//                    return;
//                if(drawingMode == DrawingMode.RECTANGLE) {
//                    SwingRectangle rectangle = new SwingRectangle(startX, endX, startY, endY);
//                    rectangle.drawingResize();
//                    rectangle.draw(g);
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    clientSocketManager.rectangle(rectangle.getStartX(), rectangle.getEndX(), rectangle.getStartY(), rectangle.getEndY(), rectangle.getLineWidth(), rectangle.getLineColor(), rectangle.getFillColor(), 10);
//                }else if(drawingMode == DrawingMode.CIRCLE) {
//                    SwingCircle circle = new SwingCircle(startX, endX, startY, endY);
//                    circle.drawingResize();
//                    circle.draw(g);
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    clientSocketManager.circle(circle.getStartX(), circle.getEndX(), circle.getStartY(), circle.getEndY(), circle.getLineWidth(), circle.getLineColor(), circle.getFillColor(), 9);
//                }
//            }

        }

        public void shapeFind(int sx, int sy) {
            if (!shapes.isEmpty()) {
                for (int i = shapes.size()-1; i >= 0; i--) {
                    SwingShape shape = shapes.get(i);
                    if (shape.contains(sx, sy)) {
                        shapeIndex = i;
                        if (!(shape instanceof SwingText) && !(shape instanceof SwingPencil)) {
                            allowColorButton = true;
                        }
                        break;
                    }
                }
            }
        }

        public void setLine(Graphics g, boolean modified){ // message 보내긴
            SwingShape parent = shapes.get(shapeIndex);
            if(parent instanceof SwingCircle){
                SwingCircle cir = (SwingCircle) parent;

                if(!modified) {
                    clientSocketManager.circleEdit(cir.getStartX(), cir.getEndX(), cir.getStartY(), cir.getEndY(), cir.getLineWidth(), currentLineColor, cir.getFillColor());
                    //메세지를 보내기만 하고 리턴하기 (필드에 적용시키지 않기 때문에 repaint를 하지 않는다)
                    return;
                }

                cir.setLineColor(currentLineColor); //해당 객체의 설정값 바꾸기
                cir.draw(g);

            }else if(parent instanceof SwingRectangle){
                SwingRectangle rec = (SwingRectangle) parent;

                if(!modified) {
                    clientSocketManager.rectangleEdit(rec.getStartX(), rec.getEndX(), rec.getStartY(), rec.getEndY(), rec.getLineWidth(), currentLineColor, rec.getFillColor());
                    return;
                }
                rec.setLineColor(currentLineColor);
                rec.draw(g);
            }
            repaint();
        }

        public void setFill(Graphics g, boolean modified){

            SwingShape parent = shapes.get(shapeIndex);
            //logger.info("shape index: {}", shapeIndex);
            //logger.info("dragg~!{}", drag); //drag말고..
            if(parent instanceof SwingCircle){
                SwingCircle cir = (SwingCircle) parent;

                if(!modified) {
                    clientSocketManager.circleEdit(cir.getStartX(), cir.getEndX(), cir.getStartY(), cir.getEndY(), cir.getLineWidth(), cir.getLineColor(), currentFillColor);
                    return;
                }

                cir.setFillColor(currentFillColor);
                cir.draw(g);

            }else if(parent instanceof SwingRectangle){
                SwingRectangle rec = (SwingRectangle) parent;

                if(!modified) {
                    clientSocketManager.rectangleEdit(rec.getStartX(), rec.getEndX(), rec.getStartY(), rec.getEndY(), rec.getLineWidth(), rec.getLineColor(), currentFillColor);
                    return;
                } //현재 클릭된 값을 넘겨보자

                rec.setFillColor(currentFillColor);
                rec.draw(g);
            }
            repaint();
        }

        public void setWidth(Graphics g, boolean modified){
            SwingShape parent = shapes.get(shapeIndex);
            if(parent instanceof SwingCircle){
                SwingCircle cir = (SwingCircle) parent;

                if(!modified) {
                    clientSocketManager.circleEdit(cir.getStartX(), cir.getEndX(), cir.getStartY(), cir.getEndY(), currentlineWidth, cir.getLineColor(), cir.getFillColor());
                    return;
                }

                cir.setLineWidth(currentlineWidth);
                cir.draw(g);

            }else if(parent instanceof SwingRectangle){
                SwingRectangle rec = (SwingRectangle) parent;

                if(!modified) {
                    clientSocketManager.rectangleEdit(rec.getStartX(), rec.getEndX(), rec.getStartY(), rec.getEndY(), currentlineWidth, rec.getLineColor(), rec.getFillColor());
                    return;
                }

                rec.setLineWidth(currentlineWidth);
                rec.draw(g);
            }
            repaint();
        }


        public void receivedMessage(Draw draw){ // draw로부터 domain을 얻어오기
            if(draw instanceof Line){
                Line line = (Line) draw;
                shapes.add(new SwingPencil(line.getX1(), line.getY1(), line.getX2(), line.getY2()));
                drawingMode = DrawingMode.PENCIL;
            }else if(draw instanceof Circle){
                Circle cir = (Circle) draw;
                shapes.add(new SwingCircle(cir.getX1(), cir.getX2(), cir.getY1(), cir.getY2(), cir.getBold(), cir.getBoldColor(), cir.getPaintColor()));
                drawingMode = DrawingMode.CIRCLE;
            }else if(draw instanceof Square){
                Square rec = (Square) draw;
                shapes.add(new SwingRectangle(rec.getX1(), rec.getX2(), rec.getY1(), rec.getY2(), rec.getBold(), rec.getBoldColor(), rec.getPaintColor()));
                drawingMode = DrawingMode.RECTANGLE;
            }else if(draw instanceof TextBox){
                TextBox text = (TextBox) draw;
                shapes.add(new SwingText(text.getText(), text.getX1(), text.getY1()));
                drawingMode = DrawingMode.TEXTBOX;
            }
            //logger.info("Received drawingmode : {}", drawingMode);
            Graphics g = getGraphics();
            shapes.get(shapes.size()-1).draw(g);
            repaint();
        }

        public void draggMessage(Draw draw){
            if(draw instanceof Square) {
                //logger.info("square안 !");
                Square rec = (Square) draw;
                startX = rec.getX1();
                endX = rec.getX2();
                startY = rec.getY1();
                endY = rec.getY2();
                rec_drag = true;
            }else if(draw instanceof Circle){
                //logger.info("circle안 !");
                Circle cir = (Circle) draw;
                startX = cir.getX1();
                endX = cir.getX2();
                startY = cir.getY1();
                endY = cir.getY2();
                cir_drag = true;
            }
            repaint();
            //logger.info("drag drawingmode : {}", drawingMode);
        }


        public void ModifyMessage(Draw draw){
            if(draw instanceof Circle){
                Circle cir = (Circle) draw;
                shapeFind(cir.getX1(), cir.getY1()); // 객체 찾기
                drawingMode = DrawingMode.NULL;

                currentFillColor = cir.getPaintColor(); // 바뀐 사항들 가져오기
                currentLineColor = cir.getBoldColor();
                currentlineWidth = cir.getBold();
                //logger.info("fill:{}, line:{}, width{}", currentFillColor, currentLineColor, currentlineWidth);

                Graphics g = getGraphics();
                setFill(g, true);
                setLine(g, true);
                setWidth(g, true);

            }else if(draw instanceof Square){
                Square rec = (Square) draw;
                shapeFind(rec.getX1(), rec.getY1());
                drawingMode = DrawingMode.NULL;

                //logger.info("received x 값: {}, y 값: {}, index : {}", rec.getX2(), rec.getY2(), shapeIndex);
                currentFillColor = rec.getPaintColor(); // 바뀐 사항들 가져오기
                currentLineColor = rec.getBoldColor();
                currentlineWidth = rec.getBold();

                Graphics g = getGraphics();
                setFill(g, true);
                setLine(g, true);
                setWidth(g, true);

            }
            //logger.info("Modify drawingmode : {}", drawingMode);
        }

        public void listDrawMessage(List<Draw> draw){
            for(int i=0; i<draw.size(); i++){
                receivedMessage(draw.get(i));
            }
        }


        public void loginMessage(String usrId){ //login
            JOptionPane.showMessageDialog(null, usrId+"님이 접속했습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
        }

        public void logoutMessage(String usrId){
            JOptionPane.showMessageDialog(null, usrId+"님이 접속을 해제했습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
        }

        public void lockMessage(){
            JOptionPane.showMessageDialog(null, "다른 사용자가 수정중이므로 접속할 수 없습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    enum DrawingMode {PENCIL, RECTANGLE, CIRCLE, TEXTBOX, SELLECT, NULL}

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
        clientSocketManager.userLogin(usrName);
        return usrName;
    }
}