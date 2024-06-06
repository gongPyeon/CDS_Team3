package distributed.cm.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.ReentrantLock;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Square implements Draw{

    @JsonIgnore
    private ReentrantLock updateLock = new ReentrantLock();
    @JsonIgnore
    private ReentrantLock selectLock = new ReentrantLock();
    @JsonIgnore
    private String selectOwner;

    private DrawType type = DrawType.SQUARE;

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

    public Square(Square square) {
        this.x1 = square.getX1();
        this.x2 = square.getX2();
        this.y1 = square.getY1();
        this.y2 = square.getY2();
        this.bold = square.getBold();
        this.boldColor = square.getBoldColor();
        this.isPaint = square.getIsPaint();
        this.paintColor = square.getPaintColor();
    }

    @Override
    public boolean updateDraw(Draw editDraw, String sessionId){
        Square square = (Square) editDraw;
        if (!selectLock.tryLock() && !selectOwner.equals(sessionId)) {
            return false;
        }
        try {
            if (updateLock.tryLock()) {
                try {
                    this.x1 = square.getX1();
                    this.x2 = square.getX2();
                    this.y1 = square.getY1();
                    this.y2 = square.getY2();
                    this.bold = square.getBold();
                    this.boldColor = square.getBoldColor();
                    this.isPaint = square.getIsPaint();
                    this.paintColor = square.getPaintColor();
                    return true;
                } finally {
                    updateLock.unlock();
                }
            }
            return false;
        } finally{
            selectLock.unlock();
            selectOwner = null;
        }
    }

    @Override
    public boolean selectDraw(String sessionId) {
        if (selectLock.tryLock()){
            return true;
        }else if (selectOwner.equals(sessionId)){
            return true;
        }
        return false;
    }
}
