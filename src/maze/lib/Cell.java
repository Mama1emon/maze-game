package maze.lib;

import javafx.scene.image.Image;
import java.io.File;

public class Cell extends GameObject {
    private boolean isWall = false;     //это Стена?
    private Image frame;                //кадр

    public Cell(int x, int y) {
        //инициализация координат Клетки
        this.x = x;
        this.y = y;
        //условие для граничных клеток - стены
        if((x == 0) || (y == 0) || (x == 810) || (y == 810)) {
            this.isWall = true;
            this.setAnimation("wall");  //установка "стены"
        }
        //условие для определенных клеток - стенок
        else if ((x == 180 && y == 180) || (x == 270 && y == 180) || (x == 360 && y == 180) || //первый столбей
                 (x == 540 && y == 180) || (x == 630 && y == 180) ||
                 (x == 450 && y == 270) ||                                                     //второй
                 (x == 180 && y == 360) || (x == 360 && y == 360) || (x == 540 && y == 360) ||
                 (x == 630 && y == 360) ||                                                     //третий
                 (x == 270 && y == 450) || (x == 630 && y == 450) ||                           //четвертый
                 (x == 90  && y == 540) || (x == 270 && y == 540) || (x == 450 && y == 540) || //пятый
                 (x == 720 && y == 540) ||
                 (x == 270 && y == 630) || (x == 450 && y == 630) || (x == 540 && y == 630) || //шестой
                 (x == 720 && y == 630) ||
                 (x == 90  && y == 720) || (x == 450 && y == 720))                             //седьмой
        {
            this.isWall = true;
            this.setAnimation("wall");  //установка "стены"
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
    @Override
    public Image getFrame() {
        return frame;
    }

    //устанавливаем кадр
    @Override
    public void setAnimation(String nameImage) {
        File image = new File("src/maze/Pictures/" + nameImage + ".jpg");
        this.frame = new Image(image.toURI().toString(),90, 90, false, false);
    }
}
