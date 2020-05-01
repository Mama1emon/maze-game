/*
 * Игровое поле
 */
package maze.lib;

public class Map {
    private final Cell[][] fields;
    private final Cell entrance;
    private final Cell exit;
    private final int mapSize;

    public Map(int size) {
        int destination = 80;
        this.fields = new Cell[size][size];
        this.mapSize = size;
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                fields[i][j] = new Cell(i * destination, j * destination);
            }
        }
        this.entrance = fields[1][0];
        this.exit = fields[size-2][size-1];
    }

    public Cell[][] getFields() {
        return fields;
    }

    public Cell getEntrance() {
        return entrance;
    }

    public Cell getExit() {
        return exit;
    }

    public int getMapSize() {
        return mapSize;
    }
}
