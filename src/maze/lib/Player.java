package maze.lib;

import javafx.scene.image.Image;

import java.io.File;

public class Player extends Cell {
    private Image[] framesForward;      //кадры движения ВПЕРЁД
    private Image[] framesBack;         //кадры движения НАЗАД
    private Image[] framesLeft;         //кадры движения ВЛЕВО
    private Image[] framesRight;        //кадры движения ВПРАВО
    private final double duration = 0.1;//частота смены

    public Player(int x, int y) {
        super(x, y);
        setAnimation("frames");
    }

    //установить кадры при движении
    @Override
    public void setAnimation(String fileName) {
        this.setFramesForward(fileName + "Forward", 4);
        this.setFramesBack(fileName+ "Back", 4);
        this.setFramesLeft(fileName + "Left", 4);
        this.setFramesRight(fileName + "Left", 4);
    }

    //возвращает кадры при движении
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

    //установка нужных параметров анимации
    public void setFramesForward(String nameImage, int numberImages) {
        Image[] framesForward = new Image[numberImages];
        File image = null;
        for (int i = 0; i < numberImages; i++) {
            image = new File("src/maze/Pictures/" + nameImage + ".png");
            framesForward[i] = new Image(image.toURI().toString());
        }
        this.framesForward = framesForward;
    }
    public void setFramesBack(String nameImage, int numberImages) {
        Image[] framesBack = new Image[numberImages];
        File image = null;
        for (int i = 0; i < numberImages; i++) {
            image = new File("src/maze/Pictures/" + nameImage + ".png");
            framesBack[i] = new Image(image.toURI().toString());
        }
        this.framesBack = framesBack;
    }
    public void setFramesLeft(String nameImage, int numberImages) {
        Image[] framesLeft = new Image[numberImages];
        File image = null;
        for (int i = 0; i < numberImages; i++) {
            image = new File("src/maze/Pictures/" + nameImage + ".png");
            framesLeft[i] = new Image(image.toURI().toString());
        }
        this.framesLeft = framesLeft;
    }
    public void setFramesRight(String nameImage, int numberImages) {
        Image[] framesRight = new Image[numberImages];
        File image = null;
        for (int i = 0; i < numberImages; i++) {
            image = new File("src/maze/Pictures/" + nameImage + ".png");
            framesRight[i] = new Image(image.toURI().toString());
        }
        this.framesRight = framesRight;
    }

    //получить путь файла
//    @Override
//    public String getResource(String fileName) {
//        return Player.class.getResource(fileName).toString();
//    }
}
