package game.views;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import physics.Vector2D;

public abstract class Sprite {

    private Image mImage;
    private Vector2D mPosition;
    private Vector2D mVelocity;
    private double mWidth;
    private double mHeight;

    public Sprite(Image image, Vector2D position, Vector2D velocity, double width, double height) {
        mImage = image;
        mPosition = position;
        mVelocity = velocity;
        mWidth = width;
        mHeight = height;
    }

    public abstract void update();


    public void render(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(mImage, mPosition.getX(), mPosition.getY(), mWidth, mHeight);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(mPosition.getX(), mPosition.getY(), mWidth, mHeight);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public Vector2D getPosition() {
        return mPosition;
    }

    public void setPosition(Vector2D position) {
        mPosition = position;
    }

    public Vector2D getVelocity() {
        return mVelocity;
    }

    public void setVelocity(Vector2D velocity) {
        mVelocity = velocity;
    }

    public void setImage(Image image) {
        mImage = image;
    }


    public void setWidth(double width) {
        mWidth = width;
    }

    public void setHeight(double height) {
        mHeight = height;
    }


    public double getWidth() {
        return mWidth;
    }

    public double getHeight() {
        return mHeight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sprite) {
            return ((Sprite) obj).getPosition().equals(getPosition());
        }
        return false;
    }

}