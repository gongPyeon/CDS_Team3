package distributed.cm.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.concurrent.locks.ReentrantLock;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Line implements Draw{

    @JsonIgnore
    private ReentrantLock updateLock = new ReentrantLock();

    private DrawType type = DrawType.LINE;

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

    public Line(Line line) {
        this.x1 = line.getX1();
        this.x2 = line.getX2();
        this.y1 = line.getY1();
        this.y2 = line.getY2();
        this.bold = line.getBold();
        this.boldColor = line.getBoldColor();
    }

    @Override
    public boolean updateDraw(Draw editDraw, String sessionId){
        Line line = (Line) editDraw;
        if(updateLock.tryLock()){
            try {
                this.x1 = line.getX1();
                this.x2 = line.getX2();
                this.y1 = line.getY1();
                this.y2 = line.getY2();
                this.bold = line.getBold();
                this.boldColor = line.getBoldColor();
                return true;
            } finally{
                updateLock.unlock();
            }
        }else{
            return false;
        }

    }

    @Override
    public boolean selectDraw(String sessionId) {
        return false;
    }
}
