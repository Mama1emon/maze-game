package maze.lib;

import javafx.scene.image.Image;

import java.io.File;

public class Player extends Cell {
    private int numberImages = 4; //количесвто изображений в анимации(фиксированное)

    private Image[] framesForward = new Image[numberImages];//кадры движения ВПЕРЁД
    private Image[] framesBack = new Image[numberImages];   //кадры движения НАЗАД
    private Image[] framesLeft = new Image[numberImages];   //кадры движения ВЛЕВО
    private Image[] framesRight = new Image[numberImages];  //кадры движения ВПРАВО
    private final double duration = 0.1;                    //частота смены


    public Player(int x, int y) {
        super(x, y);
        this.setAnimation("player");     //устанавливаем анимацию
    }

    //вернуть количество кадров
    public int getNumberImages() {
        return numberImages;
    }
    //установить количество кадров
    public void setNumberImages(int numberImages) {
        this.numberImages = numberImages;
    }

    //установить кадры при движении
    @Override
    public void setAnimation(String nameImage) {
        for (int i = 0; i < numberImages; i++) {
            //подгрузка файлов
            File imageForward = new File("src/maze/Pictures/YellowFlower/Forward/" + nameImage + i + ".png");
            File imageBack = new File("src/maze/Pictures/YellowFlower/Back/" + nameImage + i + ".png");
            File imageLeft = new File("src/maze/Pictures/YellowFlower/Left/" + nameImage + i + ".png");
            File imageRight = new File("src/maze/Pictures/YellowFlower/Right/" + nameImage + i + ".png");

            //установка кадров
            this.framesForward[i] = new Image(imageForward.toURI().toString(), 64, 100, false, false);
            this.framesBack[i] = new Image(imageBack.toURI().toString(), 64, 100, false, false);
            this.framesLeft[i] = new Image(imageLeft.toURI().toString(), 92, 100, false, false);
            this.framesRight[i] = new Image(imageRight.toURI().toString(), 92, 100, false, false);
        }
    }

    //возвращает определенный кадр(анимация движения)
    public Image getFramesForward(double time) {
        int index = (int)((time % (framesForward.length * duration)) / duration);
        return framesForward[index];
    }
    public Image getFramesBack(double time) {
        int index = (int)((time % (framesBack.length * duration)) / duration);
        return framesBack[index];
    }
    public Image getFramesLeft(double time) {
        int index = (int)((time % (framesLeft.length * duration)) / duration);
        return framesLeft[index];
    }
    public Image getFramesRight(double time) {
        int index = (int)((time % (framesRight.length * duration)) / duration);
        return framesRight[index];
    }
    public Image getFrame(){
        File image = new File("src/maze/Pictures/YellowFlower/Back/player0.png");
        return new Image(image.toURI().toString(),64, 100, false, false);
    }
    public void Move(int x, int y){
        this.x += x;
        this.y += y;
    }
}
