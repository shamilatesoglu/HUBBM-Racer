package game.views;

import javafx.scene.image.Image;
import physics.Vector2D;
import util.Constants;

public class Road extends Sprite {
    public Road(String url, Vector2D position) {
        super(new Image(url), position, new Vector2D(0, 10), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    @Override
    public void update() {
        getPosition().add(getVelocity());
        if (getPosition().getY() > Constants.SCREEN_HEIGHT) {
            getPosition().setY(-Constants.SCREEN_HEIGHT + (getPosition().getY() - Constants.SCREEN_HEIGHT));
        }
    }
}
