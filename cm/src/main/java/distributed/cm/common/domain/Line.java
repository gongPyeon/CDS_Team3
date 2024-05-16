package distributed.cm.common.domain;


import lombok.*;

import java.util.concurrent.locks.ReentrantLock;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Line implements Draw{

    private ReentrantLock lock = new ReentrantLock();

    private int x1, x2, y1, y2;

    private int bold;
    private String boldColor;

    public void updateDraw(Line draw){
        lock.lock();
        try {
            this.x1 = draw.getX1();
            this.x2 = draw.getX2();
            this.y1 = draw.getY1();
            this.y2 = draw.getY2();
            this.bold = draw.getBold();
            this.boldColor = draw.getBoldColor();
        } finally{
            lock.unlock();
        }
    }
}
