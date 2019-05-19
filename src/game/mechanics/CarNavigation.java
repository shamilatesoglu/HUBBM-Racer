package game.mechanics;

import physics.Vector2D;

public class CarNavigation {
    private boolean mUpPressed;
    private boolean mLeftPressed;
    private boolean mDownPressed;
    private boolean mRightPressed;

    public CarNavigation() {
        mUpPressed = false;
        mDownPressed = false;
        mLeftPressed = false;
        mRightPressed = false;
    }

    public Vector2D getAcceleration() {
        Vector2D acceleration = new Vector2D(0, 0);
        if (isLeftPressed())
            acceleration.setX(-2);
        if (isRightPressed())
            acceleration.setX(2);
        if (isUpPressed())
            acceleration.setY(-3.25);
        if (isDownPressed())
            acceleration.setY(3.25);
        return acceleration;
    }

    public boolean isUpPressed() {
        return mUpPressed;
    }

    public void setUpPressed(boolean upPressed) {
        mUpPressed = upPressed;
    }

    public boolean isLeftPressed() {
        return mLeftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        mLeftPressed = leftPressed;
    }

    public boolean isDownPressed() {
        return mDownPressed;
    }

    public void setDownPressed(boolean downPressed) {
        mDownPressed = downPressed;
    }

    public boolean isRightPressed() {
        return mRightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        mRightPressed = rightPressed;
    }

    public boolean hasHorizontalPress() {
        return isLeftPressed() || isRightPressed();
    }

    public boolean hasVerticalPress() {
        return isUpPressed() || isDownPressed();
    }
}
