package distributed.cm.client.swing;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class SwingText extends SwingShape{
    @Getter
    private int startX, startY;
    @Getter
    private String input;

    public SwingText(String input, int startX, int startY) {
        this.input = input;
        this.startX = startX;
        this.startY = startY;
    }

    public SwingText(int startX, int startY){
        this.startX = startX;
        this.startY = startY;
    }

    public void draw(Graphics g){
        g.drawString(input, startX, startY);
    }

    public void drawing(Graphics g){
        while (true) {
            input = JOptionPane.showInputDialog("텍스트 입력 :"); //null X 눌렀을 때 등등 처리
            if (input != null) { // OK를 눌렀을 때
                if (input.trim().isEmpty()) { //빈칸일 경우 메세지를 띄우고 다시 입력받는다
                    JOptionPane.showMessageDialog(null, "다시 입력해주세요", "경고",
                            JOptionPane.WARNING_MESSAGE);
                } else { // 빈칸이 아닐 경우 사용자 이름을 반환한다
                    break;
                }
            } else { // 이외의 버튼을 눌렀을 때
                return;
            }
        }
        g.drawString(input, startX, startY);
    }

    @Override
    public boolean contains(int mx, int my) { //text의 크기에 따라
        return mx >= startX && mx <= startX && my >= startY && my <= startY;
    }

}
