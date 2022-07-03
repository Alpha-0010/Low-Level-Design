
class Rectangle {
    protected int width;
    protected int height;

    Rectangle() {}

    Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getArea() {
        return height * width;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
        "width=" + width +
        ", height=" + height +
        '}';
    }

    public boolean isSquare() {
        return height == width;
    }

}

class Square extends Rectangle {
    
    Square(){}

    Square(int side) {
        width = height = side;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }

}

class RectangleFactory {

    public Rectangle newSquare(int side) {
        return new Rectangle(side,side);
    }

    public Rectangle newRectangle(int width,int height) {
        return new Rectangle(width,height);
    }

}

public class LiskovSubstitutionPrinciple {

    static void useIt(Rectangle rect) {
        int width = rect.getWidth();
        rect.setHeight(10);
        System.out.println("Expected area of " + (width*10) + ", got " + rect.getArea());
    }

    public static void main(String[] args) {
        Rectangle rect = new Rectangle(2,3);
        useIt(rect);

        /*  The code will break if we use the same strategy for square which should not happen according
            to Liskov Substitution Principle.
        */
        Rectangle sq = new Square();
        sq.setHeight(5);
        useIt(sq);

        /*  So to avoid this we will make use of the factory pattern. */
        RectangleFactory rf = new RectangleFactory();
        Rectangle r = rf.newSquare(5);
        useIt(r);
    }
}
