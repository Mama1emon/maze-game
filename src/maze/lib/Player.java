package maze.lib;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import maze.Main;

import java.io.File;

public class Player extends GameObject {
    private int numberImages = 4; //количесвто изображений в анимации(фиксированное)

    private Image[] framesForward = new Image[numberImages];//кадры движения ВПЕРЁД
    private Image[] framesBack = new Image[numberImages];   //кадры движения НАЗАД
    private Image[] framesLeft = new Image[numberImages];   //кадры движения ВЛЕВО
    private Image[] framesRight = new Image[numberImages];  //кадры движения ВПРАВО
    private final double duration = 0.1;                    //частота смены

    public Player(int x, int y, int size) {
        //super(x, y, size);
        this.entity = new Rectangle(size*0.64, size, Color.RED);
        this.setAnimation("player");     //устанавливаем анимацию
        setTranslateX(x);
        setTranslateY(y);
        getChildren().addAll(entity);
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
            this.framesForward[i] = new Image(imageForward.toURI().toString(), 51, 80, false, false);
            this.framesBack[i] = new Image(imageBack.toURI().toString(), 51, 80, false, false);
            this.framesLeft[i] = new Image(imageLeft.toURI().toString(), 73, 80, false, false);
            this.framesRight[i] = new Image(imageRight.toURI().toString(), 73, 80, false, false);
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
        return new Image(image.toURI().toString(),51, 80, false, false);
    }

    public void Move(int x, int y){
        Rectangle rect = new Rectangle(Main.PLAYER_SIZE*0.64, Main.PLAYER_SIZE);
        rect.setTranslateX(this.getTranslateX()+x);
        rect.setTranslateY(this.getTranslateY()+y);

        for(int i = 0; i < Main.MAP_SIZE; i++){
            for(int j = 0; j < Main.MAP_SIZE; j++){
                if (Main.map[i][j].isWall()) {
                    if (this.getBoundsInParent().intersects(Main.map[i][j].getBoundsInParent())
                    || rect.getBoundsInParent().intersects(Main.map[i][j].getBoundsInParent())) {
                        if (y < 0) {
                            System.out.println("Ahead wall");
                            this.setTranslateY(this.getTranslateY() + 2);
                            return;
                        }
                        if (y > 0) {
                            System.out.println("Back wall");
                            this.setTranslateY(this.getTranslateY() - 2);
                            return;
                        }
                        if (x < 0) {
                            System.out.println("Left wall");
                            this.setTranslateX(this.getTranslateX() + 2);
                            return;
                        }
                        if (x > 0) {
                            System.out.println("Right wall");
                            this.setTranslateX(this.getTranslateX() - 2);
                            return;
                        }
                    }
                }
                else if(this.getBoundsInParent().intersects(Main.coin.getBoundsInParent())){
                    Main.coin.setVisiblePrize(false);
                }
            }
        }
        setTranslateX(getTranslateX() + x);
        setTranslateY(getTranslateY() + y);
    }
}
