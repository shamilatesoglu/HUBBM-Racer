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

    public Vector2D getVelocity() {
        Vector2D velocity = new Vector2D(0, 0);
        if (isLeftPressed())
            velocity.addX(-8);
        if (isRightPressed())
            velocity.addX(8);
        if (isUpPressed())
            velocity.addY(-10);
        if (isDownPressed())
            velocity.addY(10);
        return velocity;
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
}
