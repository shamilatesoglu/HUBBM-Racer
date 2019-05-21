package game.resources;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import util.Util;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static ResourceManager INSTANCE;

    private Application mApplication;

    private Map<String, Image> mImageMap;
    private Map<String, AudioPlayer> mAudioPlayerMap;

    private ResourceManager() {
        mImageMap = new HashMap<>();
        mAudioPlayerMap = new HashMap<>();
    }

    public static ResourceManager getInstance() {
        if (INSTANCE == null)
            return INSTANCE = new ResourceManager();
        return INSTANCE;
    }

    public void init(Application application) {
        setApplication(application);
        initGraphics();
        initAudioPlayers();
    }

    private void setApplication(Application application) {
        mApplication = application;
    }

    private void initGraphics() {
        Image imageRedCar = new Image(Util.getResourceURL(mApplication, "car.png"));
        Image imageYellowCar = new Image(Util.getResourceURL(mApplication, "car_yellow.png"));
        Image imageGreenCar = new Image(Util.getResourceURL(mApplication, "car_green.png"));
        Image imageTotaledCar = new Image(Util.getResourceURL(mApplication, "car_totaled.png"));
        Image roadImage = new Image(Util.getResourceURL(mApplication, "road.png"));
        mImageMap.put("car-red", imageRedCar);
        mImageMap.put("car-yellow", imageYellowCar);
        mImageMap.put("car-green", imageGreenCar);
        mImageMap.put("car-totaled", imageTotaledCar);
        mImageMap.put("road", roadImage);
    }

    public Image getImage(String ID) {
        return mImageMap.get(ID);
    }

    private void initAudioPlayers() {
        mAudioPlayerMap.put("game-theme", new AudioPlayer("game_theme.mp3", () -> {
            getAudio("game-theme").stop();
            getAudio("game-theme-2").play();
        }));
        mAudioPlayerMap.put("game-theme-2", new AudioPlayer("game_theme2.mp3", () -> {
            getAudio("game-theme-2").stop();
            getAudio("game-theme").play();
        }));
        mAudioPlayerMap.put("menu-theme", new AudioPlayer("menu_theme.mp3", true));
        mAudioPlayerMap.put("game-over", new AudioPlayer("game_over.mp3", false));
        mAudioPlayerMap.put("car-crash", new AudioPlayer("car_crash.mp3", () -> {
            getAudio("car-crash").stop();
            getAudio("game-over").play();
        }));
        mAudioPlayerMap.put("car-pass", new AudioPlayer("car_pass.mp3", false));
    }

    public AudioPlayer getAudio(String name) {
        return mAudioPlayerMap.get(name);
    }

    public String getResourceURL(String filename) {
        return mApplication.getClass().getResource(filename).toExternalForm();
    }

    public void playAudioIndependently(String ID) {
        new MediaPlayer(mAudioPlayerMap.get(ID).getAudio()).play();
    }
}
