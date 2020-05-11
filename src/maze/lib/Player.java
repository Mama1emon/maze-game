/*
 * Игровой объект - игрок
 */
package maze.lib;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import maze.Main;
import java.io.File;

public class Player extends GameObject {
    private static final int NUMBER_IMAGES = 4; //количесвто изображений в анимации(фиксированное)
    private static final double DURATION = 0.1;              //частота смены
    private Image[] framesForward = new Image[NUMBER_IMAGES];//кадры движения ВПЕРЁД
    private Image[] framesBack = new Image[NUMBER_IMAGES];   //кадры движения НАЗАД
    private Image[] framesLeft = new Image[NUMBER_IMAGES];   //кадры движения ВЛЕВО
    private Image[] framesRight = new Image[NUMBER_IMAGES];  //кадры движения ВПРАВО

    public Player(int x, int y, int size) {
        this.entity = new Rectangle(size*0.64, size);
        this.setAnimation("player");     //устанавливаем анимацию
        //устанавливаем координаты
        setTranslateX(x);
        setTranslateY(y);
        getChildren().addAll(entity);    //представляем игрока в виде прямоугольника
    }

    //вернуть количество кадров
    public int getNumberImages() {
        return NUMBER_IMAGES;
    }

    //установить кадры при движении
    @Override
    public void setAnimation(String nameImage) {
        for (int i = 0; i < NUMBER_IMAGES; i++) {
            //подгрузка файлов
            File imageForward = new File("src/maze/Resources/Pictures/YellowFlower/Forward/" + nameImage + i + ".png");
            File imageBack = new File("src/maze/Resources/Pictures/YellowFlower/Back/" + nameImage + i + ".png");
            File imageLeft = new File("src/maze/Resources/Pictures/YellowFlower/Left/" + nameImage + i + ".png");
            File imageRight = new File("src/maze/Resources/Pictures/YellowFlower/Right/" + nameImage + i + ".png");

            //установка кадров
            this.framesForward[i] = new Image(imageForward.toURI().toString(), 51, 80, false, false);
            this.framesBack[i] = new Image(imageBack.toURI().toString(), 51, 80, false, false);
            this.framesLeft[i] = new Image(imageLeft.toURI().toString(), 73, 80, false, false);
            this.framesRight[i] = new Image(imageRight.toURI().toString(), 73, 80, false, false);
        }
    }

    //возвращает определенный кадр(анимация движения)
    public Image getFramesForward(double time) {
        int index = (int)((time % (framesForward.length * DURATION)) / DURATION);
        return framesForward[index];
    }
    public Image getFramesBack(double time) {
        int index = (int)((time % (framesBack.length * DURATION)) / DURATION);
        return framesBack[index];
    }
    public Image getFramesLeft(double time) {
        int index = (int)((time % (framesLeft.length * DURATION)) / DURATION);
        return framesLeft[index];
    }
    public Image getFramesRight(double time) {
        int index = (int)((time % (framesRight.length * DURATION)) / DURATION);
        return framesRight[index];
    }
    public Image getFrame(){
        File image = new File("src/maze/Resources/Pictures/YellowFlower/Back/player0.png");
        return new Image(image.toURI().toString(),51, 80, false, false);
    }

    //передвижение игрока
    public void Move(int newX, int newY)  {
        //Создаем пробный прямоугольник с новыми коорднатами
        Rectangle newEntity = new Rectangle(Main.PLAYER_SIZE*0.64, Main.PLAYER_SIZE);
        newEntity.setTranslateX(this.getTranslateX() + newX);
        newEntity.setTranslateY(this.getTranslateY() + newY);

        //перебираем всю карту
        for(int i = 0; i < Main.MAP_SIZE; i++){
            for(int j = 0; j < Main.MAP_SIZE; j++){
                if (Main.map[i][j].isWall()) {
                    //Если оригинальный прямоугольник и пробный не пересекаются со стенками
                    if (this.getBoundsInParent().intersects(Main.map[i][j].getBoundsInParent())
                    || newEntity.getBoundsInParent().intersects(Main.map[i][j].getBoundsInParent())) {
                        if (newY < 0) {     //двигаемся вверх
                            System.out.println("Ahead wall");
                            this.setTranslateY(this.getTranslateY() + 2); //оттолкнуть вниз
                            return;
                        }
                        if (newY > 0) {     //двигаемся вниз
                            System.out.println("Back wall");
                            this.setTranslateY(this.getTranslateY() - 2); //оттолкнуть вверх
                            return;
                        }
                        if (newX < 0) {     //двигаемся влево
                            System.out.println("Left wall");
                            this.setTranslateX(this.getTranslateX() + 2); //оттолкнуть вправо
                            return;
                        }
                        if (newX > 0) {     //двигаемся вправо
                            System.out.println("Right wall");
                            this.setTranslateX(this.getTranslateX() - 2); //оттолкнуть влево
                            return;
                        }
                    }
                }
                //Если оригинальный прямоугольник пересекается с призом
                else if(this.getBoundsInParent().intersects(Main.coin.getBoundsInParent())){
                    Main.coin.setVisiblePrize(false);               //скрываем приз
                    Main.score++;                                   //увеличиваем счет
                    if(Main.score == 10){
                        Main.isGame = false;
                        Main.isFinish = true;
                    }
                    //находим новые координаты для приза
                    int prizeNewY;
                    int prizeNewX;
                    do{
                        prizeNewX = (int) (Math.random() * 10);
                        prizeNewY = (int) (Math.random() * 10);
                    }while(Main.map[prizeNewX][prizeNewY].isWall());
                    //устанавливаем координаты приза
                    Main.coin.setTranslateX(prizeNewX * Main.CELL_SIZE + 15);
                    Main.coin.setTranslateY(prizeNewY * Main.CELL_SIZE + 15);
                    Main.coin.setVisiblePrize(true);                //отображаем
                }
            }
        }
        //Если ни с чем не пересекается - устанавливаем новые координаты
        setTranslateX(getTranslateX() + newX);
        setTranslateY(getTranslateY() + newY);
    }
}
