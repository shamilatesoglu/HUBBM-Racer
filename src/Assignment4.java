import game.Game;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.Constants;
import util.Util;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;


public class Assignment4 extends Application {

    private Game mGame;
    private Scene mGameScene;
    private AnimationTimer mAnimationTimer;
    private Application mApplication;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        Group root = new Group();
        mGameScene = new Scene(root);
        mApplication = this;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("HUBBM-Racer");

        mGame = new Game(this, getGameScene());

        initGameCanvas();
        initMenuCanvas();
        initEventHandlers();
        initGameLoop();

        primaryStage.setScene(getGameScene());
        primaryStage.show();
    }

    private void initGameCanvas() {
        Canvas gameCanvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        ((Group) getGameScene().getRoot()).getChildren().add(gameCanvas);
        GraphicsContext graphicsContext = gameCanvas.getGraphicsContext2D();
        getGame().setGraphicsContext(graphicsContext);
    }

    private void initGameLoop() {
        AtomicLong previousFrameTimestamp = new AtomicLong();
        previousFrameTimestamp.set(new Date().getTime() * 1000000);
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                getGame().render();
                Util.fillStrokeText(mApplication, mGame.getGraphicsContext(),
                        String.format("FPS: %.1f", mGame.getFPS(now, previousFrameTimestamp.get())), Constants.SCREEN_WIDTH - 200, 32, 0, 32);
                previousFrameTimestamp.set(now);
            }
        };
        getGame().setAnimationTimer(animationTimer);
    }

    private void initEventHandlers() {
        getGameScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case ENTER:
                    if (getGame().isGameInitialized() || !getGame().isPlaying()) {
                        getGame().startGame();
                    }
                    break;
                case ESCAPE:
                    if (getGame().isPlaying()) {
                        getGame().stopGame();
                    } else getGame().startGame();
            }
        });
    }

    private void initMenuCanvas() {
        Canvas menuCanvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        ((Group) getGameScene().getRoot()).getChildren().add(menuCanvas);
        GraphicsContext menuContext = menuCanvas.getGraphicsContext2D();
        getGame().setMenuContext(menuContext);
    }


    private Scene getGameScene() {
        return mGameScene;
    }

    private Game getGame() {
        return mGame;
    }

    public AnimationTimer getAnimationTimer() {
        return mAnimationTimer;
    }
}
