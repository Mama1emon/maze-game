package maze.lib;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Prize extends GameObject {
    private static final int numberImage = 6;
    private Image[] frames = new Image[numberImage];
    private boolean isVisiblePrize = false;
    private static final double duration = 0.15;                    //частота смены

    public boolean isVisiblePrize() {
        return this.isVisiblePrize;
    }

    public void setVisiblePrize(boolean visible) {
        isVisiblePrize = visible;
    }

    public Prize(int x, int y, int size) {
        isVisiblePrize = true;
        entity = new Rectangle(size, size, Color.GOLD);
        setTranslateX(x);
        setTranslateY(y);
        getChildren().addAll(entity);
        setAnimation("coin");
    }

    @Override
    protected void setAnimation(String nameImage) {
        for (int i = 0; i < numberImage; i++) {
            File image = new File("src/maze/Pictures/Coin/" + nameImage + i + ".png");
            frames[i] = new Image(image.toURI().toString(), 50, 50, false, false);
        }
    }

    public Image getFrame(double time){
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }
}
