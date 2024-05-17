package distributed.cm.common.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.ReentrantLock;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Square implements Draw{

    private ReentrantLock lock = new ReentrantLock();

    private int x1, x2, y1, y2;

    private int bold;
    private String boldColor;

    private int isPaint;
    private String paintColor;

    public Square(int x1, int x2, int y1, int y2, int bold, String boldColor, int isPaint, String paintColor) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.bold = bold;
        this.boldColor = boldColor;
        this.isPaint = isPaint;
        this.paintColor = paintColor;
    }

    public void updateDraw(Square draw){
        lock.lock();
        try {
            this.x1 = draw.getX1();
            this.x2 = draw.getX2();
            this.y1 = draw.getY1();
            this.y2 = draw.getY2();
            this.bold = draw.getBold();
            this.boldColor = draw.getBoldColor();
            this.isPaint = draw.getIsPaint();
            this.paintColor = draw.getPaintColor();
        } finally{
            lock.unlock();
        }
    }
}
