package game.views;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import physics.Vector2D;
import util.Constants;


public class Car extends Sprite {

    public Car(String url, Vector2D position) {
        super(new Image(url), position, new Vector2D(0, 5), 78, 128);
    }

    @Override
    public void update() {
        getPosition().add(getVelocity());
    }

    public boolean isOutOfScreen() {
        return getPosition().getY() > Constants.SCREEN_HEIGHT + getHeight();
    }

    @Override
    public boolean intersects(Sprite sprite) {
        Rectangle2D thisBoundary = new Rectangle2D(getBoundary().getMinX() + 15, getBoundary().getMinY() + 15, getWidth() - 30, getHeight() - 30);
        return thisBoundary.intersects(sprite.getBoundary());
    }

    public boolean isBehind(Car car) {
        return car.getPosition().getY() - getPosition().getY() < 0;
    }

}
