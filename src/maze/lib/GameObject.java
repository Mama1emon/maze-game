package maze.lib;

public abstract class GameObject {
    //координаты объекта
    protected int x;
    protected int y;

    //getters and setters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x){
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //abstract methods
    protected abstract void setAnimation(String nameImage);//установить анимацию
}
