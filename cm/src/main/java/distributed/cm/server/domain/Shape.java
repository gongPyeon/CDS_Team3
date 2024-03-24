package distributed.cm.server.domain;

public abstract class Shape {

    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;
    protected int bold;
    protected boolean isPaint;
    protected String color;


    public Shape(int x1, int y1, int x2, int y2, int bold, boolean isPaint, String color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.bold = bold;
        this.isPaint = isPaint;
        this.color = color;
    }


}
