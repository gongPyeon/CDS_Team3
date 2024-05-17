package distributed.cm.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.ReentrantLock;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Circle implements Draw{

    @JsonIgnore
    private ReentrantLock lock = new ReentrantLock();

    private DrawType type = DrawType.CIRCLE;

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
    public boolean updateDraw(Draw editDraw){
        Circle circle = (Circle) editDraw;
        if(lock.tryLock()){
            try {
                this.x1 = circle.getX1();
                this.x2 = circle.getX2();
                this.y1 = circle.getY1();
                this.y2 = circle.getY2();
                this.bold = circle.getBold();
                this.isPaint = circle.getIsPaint();
                this.paintColor = circle.getPaintColor();
                return true;
            } finally{
                lock.unlock();
            }
        }else {
            return false;
        }
    }
}
