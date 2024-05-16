package distributed.cm.common.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Circle implements Draw{

    private static final Logger log = LoggerFactory.getLogger(Circle.class);
    private ReentrantLock lock = new ReentrantLock();

    private int x1, x2, y1, y2;

    private int bold;
    private String boldColor;

    private int isPaint;
    private String paintColor;

    public void updateDraw(Circle draw){
        if(lock.isLocked())
        lock.lock();
        try {
            this.x1 = draw.getX1();
            this.x2 = draw.getX2();
            this.y1 = draw.getY1();
            this.y2 = draw.getY2();
            this.bold = draw.getBold();
            this.isPaint = draw.getIsPaint();
            this.paintColor = draw.getPaintColor();
        } finally{
            lock.unlock();
        }
    }
}
