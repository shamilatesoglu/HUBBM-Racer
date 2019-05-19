package game.views;

import game.mechanics.CarNavigation;
import physics.Vector2D;
import util.Constants;

public class RedCar extends Car {

    private CarNavigation mCarNavigation;
    private int mNumberOfCarsOvertaken;

    public RedCar(String url, Vector2D position) {
        super(url, position);
        mCarNavigation = new CarNavigation();
        mNumberOfCarsOvertaken = 0;
    }

    @Override
    public void update() {
        getVelocity().add(getCarNavigation().getAcceleration());
        getPosition().add(getVelocity());
        boundaryCheck();
    }

    private void boundaryCheck() {
        checkPosition();
    }

    private void checkVelocity() {
        if (getCarNavigation().hasHorizontalPress()) {
            if (getVelocity().getX() > 8) getVelocity().setX(8);
            if (getVelocity().getX() < -8) getVelocity().setX(-8);
        } else {
            if (getVelocity().getX() > 0) getVelocity().addX(-1);
            if (getVelocity().getX() < 0) getVelocity().addX(1);
        }


        if (getCarNavigation().hasVerticalPress()) {
            if (getVelocity().getY() > 10) getVelocity().setY(10);
            if (getVelocity().getY() < -10) getVelocity().setY(-10);
        } else {
            if (getVelocity().getY() > 0) getVelocity().addY(-1);
            if (getVelocity().getY() < 0) getVelocity().addY(1);
        }
    }

    private void checkPosition() {
        checkVelocity();
        if (getPosition().getY() < 0) getPosition().setY(0);
        if (getPosition().getY() > Constants.SCREEN_HEIGHT - getHeight())
            getPosition().setY(Constants.SCREEN_HEIGHT - getHeight());

        if (getPosition().getX() < 135) getPosition().setX(135);
        if (getPosition().getX() > Constants.SCREEN_WIDTH - getWidth() - 135)
            getPosition().setX(Constants.SCREEN_WIDTH - getWidth() - 135);
    }

    public CarNavigation getCarNavigation() {
        return mCarNavigation;
    }

    public void incrementNumberOfCarsOvertaken() {
        mNumberOfCarsOvertaken++;
    }

    public int getNumberOfCarsOvertaken() {
        return mNumberOfCarsOvertaken;
    }

    public void resetNumberOfCarsOvertaken() {
        mNumberOfCarsOvertaken = 0;
    }

}
