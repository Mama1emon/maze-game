package maze.lib;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Prize extends GameObject {
    private static final int numberImage = 6;              //количество кадров анимации
    private Image[] frames = new Image[numberImage];       //кадры
    private boolean isVisiblePrize = false;                //светимость приза
    private static final double duration = 0.15;           //частота смены

    public Prize(int x, int y, int size) {
        isVisiblePrize = true;  //видимость
        entity = new Rectangle(size, size, Color.GOLD);
        //установка координат приза
        setTranslateX(x);
        setTranslateY(y);
        //представляем приз в окне в виде прямоугольника
        getChildren().addAll(entity);
        //устанавливаем анимацию
        setAnimation("coin");
    }

    //Объект видимый
    public boolean isVisiblePrize() {
        return this.isVisiblePrize;
    }

    //установить видимость
    public void setVisiblePrize(boolean visible) {
        isVisiblePrize = visible;
    }

    //устанавливаем анимацию
    @Override
    protected void setAnimation(String nameImage) {
        for (int i = 0; i < numberImage; i++) {
            File image = new File("src/maze/Resources/Pictures/Coin/" + nameImage + i + ".png");
            frames[i] = new Image(image.toURI().toString(), 50, 50, false, false);
        }
    }

    //возвращаем кадр
    public Image getFrame(double time){
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }
}
