package game.objects;

import game.mechanics.CarNavigation;
import game.resources.ResourceManager;
import javafx.geometry.Rectangle2D;
import physics.Vector2D;

import static util.Constants.*;

public class RedCar extends Car {

    private CarNavigation mCarNavigation;
    private int mNumberOfCarsOvertaken;


    public RedCar(Vector2D position) {
        super(ResourceManager.getInstance().getImage("car-red"), position, new Vector2D(0, 0));
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
            if (getVelocity().getX() > RED_CAR_HORIZONTAL_MAX_SPEED)
                getVelocity().setX(RED_CAR_HORIZONTAL_MAX_SPEED);
            if (getVelocity().getX() < -RED_CAR_HORIZONTAL_MAX_SPEED)
                getVelocity().setX(-RED_CAR_HORIZONTAL_MAX_SPEED);
        } else {
            if (getVelocity().getX() > 0) getVelocity().addX(-RED_CAR_RATE_OF_SLOWDOWN);
            if (getVelocity().getX() < 0) getVelocity().addX(RED_CAR_RATE_OF_SLOWDOWN);
            if (Math.abs(getVelocity().getX() - RED_CAR_RATE_OF_SLOWDOWN) > 0 && Math.abs(getVelocity().getX()) < RED_CAR_RATE_OF_SLOWDOWN)
                getVelocity().setX(0);
        }

        if (getCarNavigation().hasVerticalPress()) {
            if (getVelocity().getY() > RED_CAR_VERTICAL_MAX_SPEED)
                getVelocity().setY(RED_CAR_VERTICAL_MAX_SPEED);
            if (getVelocity().getY() < -RED_CAR_VERTICAL_MAX_SPEED)
                getVelocity().setY(-RED_CAR_VERTICAL_MAX_SPEED);
        } else {
            if (getVelocity().getY() > 0) getVelocity().addY(-RED_CAR_RATE_OF_SLOWDOWN);
            if (getVelocity().getY() < 0) getVelocity().addY(RED_CAR_RATE_OF_SLOWDOWN);
            if (Math.abs(getVelocity().getY() - RED_CAR_RATE_OF_SLOWDOWN) > 0 && Math.abs(getVelocity().getY()) < RED_CAR_RATE_OF_SLOWDOWN)
                getVelocity().setY(0);
        }
    }

    private void checkPosition() {
        checkVelocity();
        if (getPosition().getY() < 0) getPosition().setY(0);
        if (getPosition().getY() > SCREEN_HEIGHT - getHeight())
            getPosition().setY(SCREEN_HEIGHT - getHeight());

        if (getPosition().getX() < ROAD_BOUNDS) getPosition().setX(ROAD_BOUNDS);
        if (getPosition().getX() > SCREEN_WIDTH - getWidth() - ROAD_BOUNDS)
            getPosition().setX(SCREEN_WIDTH - getWidth() - ROAD_BOUNDS);
    }

    public CarNavigation getCarNavigation() {
        return mCarNavigation;
    }

    public void incrementNumberOfCarsOvertaken() {
        mNumberOfCarsOvertaken++;
        ResourceManager.getInstance().playAudioIndependently("car-pass");
    }

    public int getNumberOfCarsOvertaken() {
        return mNumberOfCarsOvertaken;
    }

    public void resetNumberOfCarsOvertaken() {
        mNumberOfCarsOvertaken = 0;
    }

    @Override
    public boolean intersects(Sprite sprite) {
        Rectangle2D thisBoundary = new Rectangle2D(getBoundary().getMinX() + 15, getBoundary().getMinY() + 15, getWidth() - 30, getHeight() - 30);
        return thisBoundary.intersects(sprite.getBoundary());
    }
}
