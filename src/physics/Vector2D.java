package physics;

public class Vector2D {
    private double mX;
    private double mY;

    public Vector2D(double x, double y) {
        mX = x;
        mY = y;
    }

    public double getX() {
        return mX;
    }

    public void setX(double x) {
        mX = x;
    }

    public double getY() {
        return mY;
    }

    public void setY(double y) {
        mY = y;
    }

    public void addX(double x) {
        mX += x;
    }

    public void addY(double y) {
        mY += y;
    }

    public void add(Vector2D vector) {
        addX(vector.getX());
        addY(vector.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2D) {
            return getX() == ((Vector2D) obj).getX() && getY() == ((Vector2D) obj).getY();
        }
        return super.equals(obj);
    }
}
