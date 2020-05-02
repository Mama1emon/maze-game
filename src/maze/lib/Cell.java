package maze.lib;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Cell extends GameObject {
    private boolean isWall = false;     //это Стена?
    private Image frame;                //кадр

    public Cell(int x, int y, int size) {
        //инициализация
        this.entity = new Rectangle(size, size, Color.GREEN);
        this.size = size;


        //условие для граничных клеток - стены
        if((x == 0) || (y == 0) || (x == 720) || (y == 720)) {
            this.isWall = true;
            this.setAnimation("wall");  //установка "стены"
            getChildren().add(entity);
        }
        //условие для определенных клеток - стенок
        else if ((x == 160 && y == 160) || (x == 240 && y == 160) || (x == 320 && y == 160) || //первый столбей
                 (x == 480 && y == 160) || (x == 560 && y == 160) ||
                 (x == 400 && y == 240) ||                                                     //второй
                 (x == 160 && y == 320) || (x == 320 && y == 320) || (x == 480 && y == 320) ||
                 (x == 560 && y == 320) ||                                                     //третий
                 (x == 240 && y == 400) || (x == 560 && y == 400) ||                           //четвертый
                 (x == 80  && y == 480) || (x == 240 && y == 480) || (x == 400 && y == 480) || //пятый
                 (x == 640 && y == 480) ||
                 (x == 240 && y == 560) || (x == 400 && y == 560) || (x == 480 && y == 560) || //шестой
                 (x == 640 && y == 560) ||
                 (x == 80  && y == 640) || (x == 400 && y == 640))                             //седьмой
        {
            this.isWall = true;
            this.setAnimation("wall");  //установка "стены"
            getChildren().add(entity);
        }
        //остальные клетки
        else {
            this.setAnimation("cell");  //установка "поля"
        }

    }

    //Это стена?
    public boolean isWall() {
        return isWall;
    }

    //вернуть кадр
    public Image getFrame() {
        return frame;
    }

    //устанавливаем кадр
    @Override
    public void setAnimation(String nameImage) {
        File image = new File("src/maze/Pictures/" + nameImage + ".jpg");
        this.frame = new Image(image.toURI().toString(),80, 80, false, false);
    }
}
