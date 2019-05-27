package util;

import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

public final class Util {
    private static Random sRandom = new Random();

    public static String getResourceURL(Application application, String filename) {
        return application.getClass().getResource(filename).toExternalForm();
    }

    public static int getRandomInt(int min, int max) {
        return sRandom.nextInt(max) + min;
    }

    public static void fillStrokeText(Application application, GraphicsContext context, String text, double x, double y, double lineWidth, double size) {
        context.setFill(Color.YELLOW);
        context.setStroke(Color.BLACK);
        context.setLineWidth(lineWidth);
        if (context.getFont().getSize() != size)
            context.setFont(Font.loadFont(Util.getResourceURL(application, "FSEX300.ttf"), size));
        context.fillText(text, x, y);
        context.strokeText(text, x, y);
    }
}
