package distributed.cm.common.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Circle implements Draw{

    private ReentrantLock lock = new ReentrantLock();

    private int x1, x2, y1, y2;

    private int bold;
    private String boldColor;

    private int isPaint;
    private String paintColor;

    public Circle(int x1, int x2, int y1, int y2, int bold, String boldColor, int isPaint, String paintColor) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.bold = bold;
        this.boldColor = boldColor;
        this.isPaint = isPaint;
        this.paintColor = paintColor;
    }

    @Override
    public void updateDraw(Draw draw){
        Circle circle = (Circle) draw;
        if(lock.isLocked())
        lock.lock();
        try {
            this.x1 = circle.getX1();
            this.x2 = circle.getX2();
            this.y1 = circle.getY1();
            this.y2 = circle.getY2();
            this.bold = circle.getBold();
            this.isPaint = circle.getIsPaint();
            this.paintColor = circle.getPaintColor();
        } finally{
            lock.unlock();
        }
    }
}
