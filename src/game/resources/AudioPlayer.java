package game.resources;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioPlayer {
    private MediaPlayer mMediaPlayer;
    private boolean mIsPlaying;

    private AudioPlayer(String filename) {
        mMediaPlayer = new MediaPlayer(new Media(ResourceManager.getInstance().getResourceURL(filename)));
        mMediaPlayer.setVolume(0.35);
    }


    public AudioPlayer(String filename, boolean loops) {
        this(filename);

        mMediaPlayer.setOnEndOfMedia(() -> {
            if (loops) mMediaPlayer.seek(Duration.ZERO);
            else mMediaPlayer.stop();
        });
    }

    public AudioPlayer(String filename, Runnable onEndOfMedia) {
        this(filename);
        mMediaPlayer.setOnEndOfMedia(onEndOfMedia);
    }

    public void play() {
        mMediaPlayer.play();
        mIsPlaying = true;
    }

    public void stop() {
        mMediaPlayer.stop();
        mIsPlaying = false;
    }

    public void pause() {
        mMediaPlayer.pause();
        mIsPlaying = false;

    }

    public Media getAudio() {
        return mMediaPlayer.getMedia();
    }

    public boolean isPlaying() {
        return mIsPlaying;
    }
}

