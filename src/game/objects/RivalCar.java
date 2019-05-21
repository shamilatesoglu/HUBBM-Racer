package game.objects;

import game.resources.ResourceManager;
import physics.Vector2D;

import static util.Constants.RIVAL_CAR_INITIAL_HOR_VEL;

public class RivalCar extends Car {
    @Override
    public void update() {
        getPosition().add(getVelocity());
    }

    private boolean mHasFallenBehind;

    public RivalCar(Vector2D position) {
        super(ResourceManager.getInstance().getImage("car-yellow"), position, new Vector2D(0, RIVAL_CAR_INITIAL_HOR_VEL));
    }

    public boolean hasAlreadyFallenBehind() {
        return mHasFallenBehind;
    }

    public void setHasAlreadyFallenBehind(boolean b) {
        mHasFallenBehind = b;
    }
}
