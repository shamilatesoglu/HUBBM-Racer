package game.views;

import game.resources.ResourceManager;
import game.mechanics.CarNavigation;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.MediaPlayer;
import physics.Vector2D;
import util.Util;

import static util.Constants.*;

public class RedCar extends Car {

    private CarNavigation mCarNavigation;
    private int mNumberOfCarsOvertaken;
    private MediaPlayer mAudioPlayer;


    public RedCar(Vector2D position) {
        super(position);
        setImage(ResourceManager.getInstance().getImage("car-red"));
        mCarNavigation = new CarNavigation();
        mNumberOfCarsOvertaken = 0;
        //mAudioPlayer = new MediaPlayer(ResourceManager.getInstance().getMedia("car-pass"));
        //mAudioPlayer.play();
       //mAudioPlayer.setOnEndOfMedia(() -> mAudioPlayer.seek(Duration.ZERO));
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
        }

        if (getCarNavigation().hasVerticalPress()) {
            if (getVelocity().getY() > RED_CAR_VERTICAL_MAX_SPEED)
                getVelocity().setY(RED_CAR_VERTICAL_MAX_SPEED);
            if (getVelocity().getY() < -RED_CAR_VERTICAL_MAX_SPEED)
                getVelocity().setY(-RED_CAR_VERTICAL_MAX_SPEED);
        } else {
            if (getVelocity().getY() > 0) getVelocity().addY(-RED_CAR_RATE_OF_SLOWDOWN);
            if (getVelocity().getY() < 0) getVelocity().addY(RED_CAR_RATE_OF_SLOWDOWN);
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
        Util.playAudio("car-pass");
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
