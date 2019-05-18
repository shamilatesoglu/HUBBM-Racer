package game.views;

import game.mechanics.CarNavigation;
import physics.Vector2D;
import util.Constants;

public class RedCar extends Car {

    private CarNavigation mCarNavigation;

    public RedCar(String url, Vector2D position) {
        super(url, position);
        mCarNavigation = new CarNavigation();
    }

    @Override
    public void update() {
        getPosition().add(getCarNavigation().getVelocity());
        //getPosition().add(getVelocity());
        boundaryCheck();
    }

    private void boundaryCheck() {
        checkPosition();
    }

    private void checkVelocity() {
        if (getVelocity().getX() > 5) getVelocity().setX(5);
        if (getVelocity().getX() < -5) getVelocity().setX(-5);
        if (getVelocity().getY() > 5) getVelocity().setY(5);
        if (getVelocity().getY() < -5) getVelocity().setY(-5);
    }

    private void checkPosition() {
        checkVelocity();
        if (getPosition().getY() < 0) getPosition().setY(0);
        if (getPosition().getY() > Constants.SCREEN_HEIGHT - getHeight())
            getPosition().setY(Constants.SCREEN_HEIGHT - getHeight());

        if (getPosition().getX() < 140) getPosition().setX(140);
        if (getPosition().getX() > Constants.SCREEN_WIDTH - getWidth() - 140)
            getPosition().setX(Constants.SCREEN_WIDTH - getWidth() - 140);
    }

    public CarNavigation getCarNavigation() {
        return mCarNavigation;
    }

}
