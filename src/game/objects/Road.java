package game.objects;

import game.resources.ResourceManager;
import physics.Vector2D;
import util.Constants;

import static util.Constants.ROAD_INITIAL_HOR_VEL;

public class Road extends Sprite {
    public Road(Vector2D position) {
        super(ResourceManager.getInstance().getImage("road"), position, new Vector2D(0, ROAD_INITIAL_HOR_VEL), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    @Override
    public void update() {
        getPosition().add(getVelocity());
        if (getPosition().getY() > Constants.SCREEN_HEIGHT) {
            getPosition().setY(-Constants.SCREEN_HEIGHT + (getPosition().getY() - Constants.SCREEN_HEIGHT));
        }
    }
}
