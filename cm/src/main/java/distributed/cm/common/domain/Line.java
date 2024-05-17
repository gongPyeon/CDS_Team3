package distributed.cm.common.domain;

import lombok.*;

import java.util.concurrent.locks.ReentrantLock;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Line implements Draw{

    private ReentrantLock lock = new ReentrantLock();

    private int x1, x2, y1, y2;

    private int bold;
    private String boldColor;

    public Line(int x1, int x2, int y1, int y2, int bold, String boldColor) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.bold = bold;
        this.boldColor = boldColor;
    }

    @Override
    public void updateDraw(Draw draw){
        Line line = (Line) draw;
        lock.lock();
        try {
            this.x1 = line.getX1();
            this.x2 = line.getX2();
            this.y1 = line.getY1();
            this.y2 = line.getY2();
            this.bold = line.getBold();
            this.boldColor = line.getBoldColor();
        } finally{
            lock.unlock();
        }
    }
}
