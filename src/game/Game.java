package game;

import game.objects.*;
import game.resources.AudioPlayer;
import game.resources.ResourceManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import physics.Vector2D;
import util.Util;

import java.util.ArrayList;

import static util.Constants.*;

public class Game {

    private Application mApplication;
    private Scene mGameScene;
    private GraphicsContext mGraphicsContext;
    private GraphicsContext mMenuContext;
    private GameState mState;
    private ArrayList<Sprite> mSprites;
    private RedCar mRedCar;
    private ArrayList<RivalCar> mRivalCars;
    private int mScore;
    private int mLevel = 1;

    public Game(Application application, Scene gameScene) {
        mApplication = application;
        mGameScene = gameScene;
        mSprites = new ArrayList<>();
        mRivalCars = new ArrayList<>();
        initRoad();
        initPrimaryCar();
        initEventHandlers();
        initRivalCars();
        setState(GameState.IDLE);
    }

    private void initRoad() {
        Road road1 = new Road(new Vector2D(0, 0));
        Road road2 = new Road(new Vector2D(0, SCREEN_HEIGHT));

        mSprites.add(road1);
        mSprites.add(road2);
    }

    private void initPrimaryCar() {
        mRedCar = new RedCar(new Vector2D(350, 650));
        mSprites.add(mRedCar);
    }

    private void initEventHandlers() {
        mGameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    mRedCar.getCarNavigation().setUpPressed(true);
                    break;
                case LEFT:
                    mRedCar.getCarNavigation().setLeftPressed(true);
                    break;
                case DOWN:
                    mRedCar.getCarNavigation().setDownPressed(true);
                    break;
                case RIGHT:
                    mRedCar.getCarNavigation().setRightPressed(true);
                    break;
            }
        });

        mGameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    mRedCar.getCarNavigation().setUpPressed(false);
                    break;
                case LEFT:
                    mRedCar.getCarNavigation().setLeftPressed(false);
                case DOWN:
                    mRedCar.getCarNavigation().setDownPressed(false);
                    break;
                case RIGHT:
                    mRedCar.getCarNavigation().setRightPressed(false);
                    break;
            }
        });
    }

    public void startGame() {
        setState(GameState.PLAYING);
    }

    public void restart() {
        mSprites.clear();
        mRivalCars.clear();
        initRoad();
        initPrimaryCar();
        initRivalCars();
        setScore(0);
        setLevel(1);
        setState(GameState.PLAYING);
    }

    private void initRivalCars() {
        ArrayList<Rectangle2D> carRects = new ArrayList<>();
        carRects.add(new Rectangle2D(mRedCar.getPosition().getX(), mRedCar.getPosition().getY(), mRedCar.getWidth(), mRedCar.getHeight()));

        int limit = 100;
        while (carRects.size() <= MAX_VISIBLE_NUMBER_OF_CARS && limit-- > 0) {

            int posX = Util.getRandomInt(150, SCREEN_WIDTH - 78 - 300);
            int posY = Util.getRandomInt(-SCREEN_HEIGHT, SCREEN_HEIGHT - 128);

            Vector2D position = new Vector2D(posX, posY);
            RivalCar car = new RivalCar(position);
            Rectangle2D carRect = new Rectangle2D(posX - CAR_WIDTH / 2.0, posY - CAR_HEIGHT / 2.0,
                    car.getWidth() + CAR_WIDTH, car.getHeight() + CAR_HEIGHT);

            boolean intersects = false;
            for (Rectangle2D rectangle2D : carRects) {
                if (rectangle2D.intersects(carRect)) {
                    intersects = true;
                    break;
                }
            }

            if (!intersects) {
                carRects.add(carRect);
                mSprites.add(car);
                mRivalCars.add(car);
            }
        }
    }

    public void render() {
        clearScr();
        mSprites.forEach(sprite -> sprite.render(getGraphicsContext()));
        switch (getState()) {
            case PLAYING:
                update();
                renderScoreBoard();
                break;
            case PAUSED:
                renderScoreBoard();
                showMenu();
                break;
            case LOST:
                showMenu();
                break;
            case IDLE:
                update();
                showMenu();
                break;
        }
    }

    private void renderScoreBoard() {
        Util.fillStrokeText(getApplication(), getGraphicsContext(), String.format("Score: %d", getScore()), 10, 32, 0, 32);
        Util.fillStrokeText(getApplication(), getGraphicsContext(), String.format("Level: %d", getLevel()), 10, 64, 0, 32);
    }

    private void update() {
        switch (getState()) {
            case IDLE:
                for (Sprite sprite : mSprites) {
                    if (sprite instanceof Road || sprite instanceof RedCar) {
                        sprite.update();
                    }
                }
                break;
            case PLAYING:
                for (Sprite sprite : mSprites) {
                    if (sprite instanceof RivalCar) {
                        RivalCar car = (RivalCar) sprite;
                        if (car.isOutOfScreen()) {
                            setNewPositionForCar(car);
                        }
                        if (!car.hasAlreadyFallenBehind() && car.isBehind(mRedCar)) {
                            car.setImage(ResourceManager.getInstance().getImage("car-green"));
                            incrementScore();
                            car.setHasAlreadyFallenBehind(true);
                        }
                        if (mRedCar.intersects(car)) {
                            getAudio("car-crash").play();
                            mRedCar.setImage(ResourceManager.getInstance().getImage("car-totaled"));
                            car.setImage(ResourceManager.getInstance().getImage("car-totaled"));
                            setState(GameState.LOST);
                        }
                    }
                    sprite.update();
                }
                break;
        }

    }

    private void setNewPositionForCar(RivalCar car) {
        car.setImage(ResourceManager.getInstance().getImage("car-yellow"));
        boolean intersects = true;
        int limit = 100;

        while (intersects && limit-- > 0) {
            int newPosX = Util.getRandomInt(150, SCREEN_WIDTH - 78 - 300);
            int newPosY = Util.getRandomInt(-SCREEN_HEIGHT, SCREEN_HEIGHT - 128);
            Vector2D newPos = new Vector2D(newPosX, newPosY);
            car.setPosition(newPos);
            intersects = false;
            Rectangle2D boundaries =
                    new Rectangle2D(newPosX - CAR_WIDTH, newPosY - CAR_HEIGHT, car.getWidth() * 3, car.getHeight() * 3);
            for (Car otherCar : mRivalCars) {
                if (!otherCar.equals(car))
                    if (otherCar.getBoundary().intersects(boundaries)) {
                        intersects = true;
                        break;
                    }
            }
        }

        car.setHasAlreadyFallenBehind(false);
    }

    public Application getApplication() {
        return mApplication;
    }

    public GraphicsContext getGraphicsContext() {
        return mGraphicsContext;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        mGraphicsContext = graphicsContext;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public void incrementScore() {
        mScore += getLevel();
        mRedCar.incrementNumberOfCarsOvertaken();
        if (mRedCar.getNumberOfCarsOvertaken() == mRivalCars.size()) {
            mRedCar.resetNumberOfCarsOvertaken();
            incrementLevel();
        }
    }

    public void clearScr() {
        mMenuContext.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        mGraphicsContext.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void showMenu() {
        switch (getState()) {
            case IDLE:
                Util.fillStrokeText(getApplication(), getMenuContext(), "HUBBM-RACER", 70, 400, 3, 84);
                Util.fillStrokeText(getApplication(), getMenuContext(), "Press ENTER to start", 120, 440, 1, 36);
                break;
            case PAUSED:
                Util.fillStrokeText(getApplication(), getMenuContext(), "PAUSED", 180, 400, 3, 84);
                Util.fillStrokeText(getApplication(), getMenuContext(), "Press ESC to continue", 110, 440, 1, 36);
                break;
            case LOST:
                Util.fillStrokeText(getApplication(), getMenuContext(), "GAME OVER", 120, 400, 3, 84);
                Util.fillStrokeText(getApplication(), getMenuContext(), "Your Score: " + getScore(), 180, 440, 1, 36);
                Util.fillStrokeText(getApplication(), getMenuContext(), "Press ENTER to restart", 100, 480, 1, 36);
                break;
        }
    }

    public double getFPS(long now, long previous) {
        long diffNanosec = now - previous;
        double diffSec = diffNanosec / 1000000000d;
        return 1 / diffSec;
    }

    public GraphicsContext getMenuContext() {
        return mMenuContext;
    }

    public void setMenuContext(GraphicsContext context) {
        mMenuContext = context;
    }

    public GameState getState() {
        return mState;
    }

    public void setState(GameState state) {
        mState = state;
        switch (mState) {
            case IDLE:
                getAudio("menu-theme").play();
                break;
            case PLAYING:
                getAudio("car-crash").stop();
                getAudio("game-over").stop();
                getAudio("menu-theme").stop();
                if (!getAudio("game-theme-2").isPlaying()) getAudio("game-theme").play();
                break;
            case LOST:
                if (getAudio("game-theme").isPlaying())
                    getAudio("game-theme").stop();
                else getAudio("game-theme-2").stop();
                break;
        }

    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public void incrementLevel() {
        mLevel++;
        for (Sprite sprite : mSprites) {
            if (sprite instanceof Road) {
                sprite.getVelocity().addY(1);
            } else if (sprite instanceof Car && !(sprite instanceof RedCar)) {
                sprite.getVelocity().addY(0.5);
            }
        }
    }

    private AudioPlayer getAudio(String name) {
        return ResourceManager.getInstance().getAudio(name);
    }

    public enum GameState {
        PLAYING, PAUSED, LOST, IDLE
    }
}
