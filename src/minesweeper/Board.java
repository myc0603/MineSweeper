package minesweeper;

import java.util.*;

public class Board {

    // 입력받는 방법 때문에 보드 사이즈와 지뢰 수를 커스텀할 수가 없음
    public static final int SIZE_Y = 9;
    public static final int SIZE_X = 9;
    public static final int CELL_COUNT = SIZE_Y * SIZE_X;
    public static final int MINE_COUNT = 10;

    private final Game game;
    private final Cell[][] cells = new Cell[SIZE_Y][SIZE_X];
    private int releasedCnt;
    private int checkMineCnt;

    public Board(Game game) {
        this.game = game;
        // init cells
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void init() {
        // determine the positions of mines
        landMines();

        // init aroundCells[] of each cell, set cellNumber
        initAroundCells();
    }

    public void setCellReleased(int y, int x, boolean[][] visited) {
        if (cells[y][x].isMine()) {
            // 모든 지뢰 cell released
            for (int i = 0; i < SIZE_Y; i++) {
                for (int j = 0; j < SIZE_X; j++) {
                    if (cells[i][j].isMine()) {
                        cells[i][j].setReleased();
                    }
                }
            }
            game.setEnd();
        }
        visited[y][x] = true;
        cells[y][x].setReleased();
        releasedCnt++;
        checkWin();

        if (cells[y][x].getAroundMineCount() == 0) { // 주변에 지뢰 없음
            for (Cell cell : around(y, x)) {
                int ny = cell.getY();
                int nx = cell.getX();
                if (!cells[ny][nx].isChecked() && !cells[ny][nx].isReleased() && !visited[ny][nx]) {
                    setCellReleased(ny, nx, visited);
                }
            }
        }
    }

    public void checkCellMine(int i, int j) {
        cells[i][j].setChecked(!cells[i][j].isChecked());
        checkMineCnt += cells[i][j].isChecked() ? 1 : -1;
    }

    public void printBoard() {
        System.out.println("  a b c d e f g h i");
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(cells[i][j].showingValue() + " ");
            }
            System.out.println();
        }
        System.out.println("Mine Remaining: " + (MINE_COUNT - checkMineCnt));
    }

    private void landMines() {
        Random random = new Random();
        Set<Integer> mineNumbers = new HashSet<>();
        while (mineNumbers.size() < MINE_COUNT) {
            mineNumbers.add(random.nextInt(CELL_COUNT));
        }
        for (Integer mineNumber : mineNumbers) {
            int i = mineNumber / SIZE_X;
            int j = mineNumber % SIZE_X;
            cells[i][j].setMine();
        }
    }

    private void initAroundCells() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                cells[i][j].initAroundCells(around(i, j));
            }
        }
    }

    private void checkWin() {
        if (releasedCnt == CELL_COUNT - MINE_COUNT) {
            game.setWin();
            //모든 cell released
            for (int i = 0; i < SIZE_Y; i++) {
                for (int j = 0; j < SIZE_X; j++) {
                    cells[i][j].setReleased();
                }
            }
            game.setEnd();
        }
    }

    private List<Cell> around(int y, int x) {
        List<Cell> result = new ArrayList<>();
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int i = 0; i < 8; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            if (inBoard(ny, nx)) {
                result.add(cells[ny][nx]);
            }
        }
        return result;
    }

    private boolean inBoard(int i, int j) {
        return 0 <= i && i < SIZE_Y && 0 <= j && j < SIZE_X;
    }
}
