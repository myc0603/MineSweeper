package minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private final int y;
    private final int x;

    private boolean mine;
    private int aroundMineCount; // -1: not initialized or is mine
    private boolean released;
    private boolean checked;

    private final List<Cell> aroundCells = new ArrayList<>();

    public Cell(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public void setMine() {
        mine = true;
    }

    public void initAroundCells(List<Cell> cells) {
        this.aroundCells.addAll(cells);
        setAroundMineCount();
    }

    private void setAroundMineCount() {
        for (Cell cell : aroundCells) {
            if (cell.mine) {
                aroundMineCount++;
            }
        }
    }

    public String showingValue() {
        if (released) {
            return mine ? "*" : String.valueOf(aroundMineCount);
        }
        return checked ? "m" : "-";
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased() {
        released = true;
    }

    public int getAroundMineCount() {
        return aroundMineCount;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isMine() {
        return mine;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }
}
