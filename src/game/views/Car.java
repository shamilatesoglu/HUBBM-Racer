package game.views;

import game.resources.ResourceManager;
import physics.Vector2D;
import util.Constants;


public class Car extends Sprite {

    private boolean mHasFallenBehind;

    public Car(Vector2D position) {
        super(ResourceManager.getInstance().getImage("car-yellow"), position, new Vector2D(0, 5), 78, 128);
    }

    @Override
    public void update() {
        getPosition().add(getVelocity());
    }

    public boolean isOutOfScreen() {
        return getPosition().getY() > Constants.SCREEN_HEIGHT + getHeight();
    }

    public boolean isBehind(Car car) {
        return car.getPosition().getY() - getPosition().getY() < 0;
    }

    public boolean hasAlreadyFallenBehind() {
        return mHasFallenBehind;
    }

    public void setHasAlreadyFallenBehind(boolean b) {
        mHasFallenBehind = b;
    }
}
