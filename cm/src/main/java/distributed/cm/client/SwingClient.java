package distributed.cm.client;

import jakarta.websocket.EncodeException;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

@Slf4j
public class SwingClient {
    private final ClientSocketComm clientSocketComm;

    public SwingClient() {
        clientSocketComm = new ClientSocketComm();
    }

    public static void main(String[] args) throws IOException {
        SwingClient client = new SwingClient();

        //swing 실행
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simple Paint");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(client.new DrawingPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        client.clientSocketComm.openSocket();
    }
    class DrawingPanel extends JPanel {
        private int lastX, lastY;
        private DrawingMode drawingMode = DrawingMode.PENCIL;
        private Color currentColor = Color.BLACK;

        public DrawingPanel() {
            setPreferredSize(new Dimension(600, 400));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    lastX = e.getX();
                    lastY = e.getY();
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    if (drawingMode == DrawingMode.PENCIL || drawingMode == DrawingMode.ERASER) {
                        Graphics g = getGraphics();
                        if (drawingMode == DrawingMode.PENCIL) {
                            g.setColor(currentColor);
                        } else {
                            g.setColor(getBackground());
                        }
                        g.drawLine(lastX, lastY, x, y);
                        g.dispose();
                    }
                    lastX = x;
                    lastY = y;
                    clientSocketComm.draw(x, y);
                }
            });

            JButton pencilButton = new JButton("Pencil");
            pencilButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingMode = DrawingMode.PENCIL;
                }
            });

            JButton eraserButton = new JButton("Eraser");
            eraserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingMode = DrawingMode.ERASER;
                }
            });

            JButton clearButton = new JButton("Clear All");
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearDrawing();
                }
            });

            add(pencilButton);
            add(eraserButton);
            add(clearButton);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Drawing code here if needed
        }

        public void setCurrentColor(Color color) {
            currentColor = color;
        }

        public void clearDrawing() {
            Graphics g = getGraphics();
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.dispose();
        }
    }

    enum DrawingMode {
        PENCIL, ERASER
    }
}

