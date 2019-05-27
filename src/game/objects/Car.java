package game.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import physics.Vector2D;
import util.Constants;

import static util.Constants.CAR_HEIGHT;
import static util.Constants.CAR_WIDTH;


public abstract class Car extends Sprite {


    public Car(Image image, Vector2D position, Vector2D initialVelocity) {
        super(image, position, initialVelocity, CAR_WIDTH, CAR_HEIGHT);
    }

    @Override
    public abstract void update();

    public boolean isOutOfScreen() {
        return getPosition().getY() > Constants.SCREEN_HEIGHT + getHeight();
    }

    public boolean isBehind(Car car) {
        return car.getPosition().getY() - getPosition().getY() < 0;
    }
}
