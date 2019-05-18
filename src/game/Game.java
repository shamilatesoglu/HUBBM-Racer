package game;

import game.views.Car;
import game.views.RedCar;
import game.views.Road;
import game.views.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import physics.Vector2D;
import util.Constants;
import util.Util;

import java.util.ArrayList;

public class Game {

    enum GameState {
        PLAYING, PAUSED, LOST, INITIALIZED
    }

    private Application mApplication;
    private Scene mGameScene;
    private GraphicsContext mGraphicsContext;
    private GraphicsContext mMenuContext;
    private GameState mState;

    private ArrayList<Sprite> mSprites;

    private RedCar mRedCar;

    private ArrayList<Car> mNPCCars;

    private boolean mGameInitialized;

    private boolean mPlaying;

    private int mScore;

    private AnimationTimer mAnimationTimer;

    public Game(Application application, Scene gameScene) {
        mApplication = application;
        mGameScene = gameScene;
        mSprites = new ArrayList<>();
        mNPCCars = new ArrayList<>();
        initRoad();
        initPrimaryCar();
        initEventHandlers();
        initOtherCars();
        mState = GameState.INITIALIZED;
    }

    private void initSounds() {
        Media media = new Media(Util.getResourceURL(getApplication(), "game_theme.ogg"));
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    private void initRoad() {
        Road road1 = new Road(Util.getResourceURL(getApplication(), "road.png"), new Vector2D(0, 0));
        Road road2 = new Road(Util.getResourceURL(getApplication(), "road.png"), new Vector2D(0, Constants.SCREEN_HEIGHT));

        mSprites.add(road1);
        mSprites.add(road2);
    }

    private void initPrimaryCar() {
        mRedCar = new RedCar(Util.getResourceURL(getApplication(), "car.png"), new Vector2D(350, 650));
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

    }

    private void initOtherCars() {
        ArrayList<Rectangle2D> carRects = new ArrayList<>();
        carRects.add(new Rectangle2D(mRedCar.getPosition().getX(), mRedCar.getPosition().getY(), mRedCar.getWidth(), mRedCar.getHeight()));

        int limit = 100;
        while (carRects.size() <= 3 && limit-- > 0) {

            int posX = Util.getRandomInt(150, Constants.SCREEN_WIDTH - 78 - 300);
            int posY = Util.getRandomInt(-Constants.SCREEN_HEIGHT, Constants.SCREEN_HEIGHT - 128);

            Vector2D position = new Vector2D(posX, posY);
            Car car = new Car(Util.getResourceURL(getApplication(), "car_yellow.png"), position);
            Rectangle2D carRect = new Rectangle2D(posX, posY, car.getWidth(), car.getHeight());

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
                mNPCCars.add(car);
            }
        }
    }


    public void render() {
        clearScr();
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
            case INITIALIZED:
                update();
                break;
        }
    }

    public void stopGame() {
        setPlaying(false);
        setGameInitialized(false);
        //mAnimationTimer.stop();
    }

    private void renderScoreBoard() {
        Util.fillStrokeText(getApplication(), getGraphicsContext(), String.format("Score: %d", getScore()), 10, 32, 0, 32);
    }

    private void update() {
        for (Sprite sprite : mSprites) {
            if (sprite instanceof Car && !(sprite instanceof RedCar)) {
                Car car = (Car) sprite;
                if (car.isOutOfScreen()) {
                    setNewPositionForCar(car);
                }
                if (car.isBehind(mRedCar)) {
                    car.setImage(Util.getImage(getApplication(), "car_green.png"));
                    incrementScore();
                }
                if (mRedCar.intersects(car)) {
                    mRedCar.setImage(Util.getImage(getApplication(), "car_totaled.png"));
                    car.setImage(Util.getImage(getApplication(), "car_totaled.png"));
                    stopGame();
                }
            }
            sprite.update();
            sprite.render(getGraphicsContext());
        }
    }

    private void setNewPositionForCar(Car car) {
        car.setImage(Util.getImage(getApplication(), "car_yellow.png"));
        boolean intersects = true;
        int limit = 100;

        while (intersects && limit-- > 0) {
            int newPosX = Util.getRandomInt(150, Constants.SCREEN_WIDTH - 78 - 300);
            int newPosY = Util.getRandomInt(-Constants.SCREEN_HEIGHT, Constants.SCREEN_HEIGHT - 128);
            Vector2D newPos = new Vector2D(newPosX, newPosY);
            car.setPosition(newPos);
            intersects = false;
            for (Car otherCar : mNPCCars) {
                if (!otherCar.equals(car))
                    if (otherCar.intersects(car)) {
                        intersects = true;
                        break;
                    }
            }
        }
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        mGraphicsContext = graphicsContext;
    }

    public Application getApplication() {
        return mApplication;
    }

    public boolean isGameInitialized() {
        return mGameInitialized;
    }

    public void setGameInitialized(boolean gameInitialized) {
        mGameInitialized = gameInitialized;
    }

    public boolean isPlaying() {
        return mPlaying;
    }

    public void setPlaying(boolean playing) {
        mPlaying = playing;
    }

    public GraphicsContext getGraphicsContext() {
        return mGraphicsContext;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public void incrementScore() {
        mScore++;
    }

    public void setMenuContext(GraphicsContext context) {
        mMenuContext = context;
    }

    public void clearScr() {
        mMenuContext.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        mGraphicsContext.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    private void showMenu() {
        switch (getState()) {
            case PLAYING:
                Util.fillStrokeText(getApplication(), getMenuContext(), "HUBBM-RACER", 70, 400, 3, 84);
                Util.fillStrokeText(getApplication(), getMenuContext(), "Press ENTER to start", 120, 440, 1, 36);
                break;
            case PAUSED:
                Util.fillStrokeText(getApplication(), getMenuContext(), "PAUSED", 70, 400, 3, 84);
                Util.fillStrokeText(getApplication(), getMenuContext(), "Press ESC to continue", 120, 440, 1, 36);
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

    public void setAnimationTimer(AnimationTimer animationTimer) {
        mAnimationTimer = animationTimer;
        mAnimationTimer.start();
    }

    public GameState getState() {
        return mState;
    }

    public void setState(GameState state) {
        mState = state;
    }
}
