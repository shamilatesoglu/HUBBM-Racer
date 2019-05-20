package game.views;

import game.resources.ResourceManager;
import physics.Vector2D;
import util.Constants;

public class Road extends Sprite {
    public Road(Vector2D position) {
        super(ResourceManager.getInstance().getImage("road"), position, new Vector2D(0, 10), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    @Override
    public void update() {
        getPosition().add(getVelocity());
        if (getPosition().getY() > Constants.SCREEN_HEIGHT) {
            getPosition().setY(-Constants.SCREEN_HEIGHT + (getPosition().getY() - Constants.SCREEN_HEIGHT));
        }
    }
}
