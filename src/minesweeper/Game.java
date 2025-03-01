package minesweeper;

import java.util.Scanner;

// 지면 모든 지뢰 위치 보여주기
public class Game {
    private final Board board;
    private final Scanner scanner;
    private boolean end = false;
    private boolean win = false;

    public Game() {
        this.board = new Board(this); // 이게 되네?? 순환 참조 아닌가?
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        board.init();
        board.printBoard();

        while (!end) {
            String[] command = getCommand();
            String position = command[0];
            String option = command[1];

            int y = position.charAt(1) - '1';
            int x = position.charAt(0) - 'a';

            switch (option) {
                case "1" -> {
                    boolean[][] visited = new boolean[Board.SIZE_Y][Board.SIZE_X];
                    board.setCellReleased(y, x, visited);
                }
                case "2" -> board.checkCellMine(y, x);
            }
            board.printBoard();
        }
        end();
    }

    private String[] getCommand() {
        System.out.println("선택할 위치를 입력 해주세요. (예: a1, b3, ...)");
        String position = scanner.nextLine();
        while (!isValid(position)) {
            System.out.println("올바른 위치를 다시 입력 해주세요.");
            position = scanner.nextLine();
        }
        System.out.println("옵션을 정해주세요. 1 혹은 2를 입력하세요.");
        System.out.println("1. 확인");
        System.out.println("2. 지뢰 표시 / 해제");
        String option = scanner.nextLine();
        while (!option.equals("1") && !option.equals("2")) {
            System.out.println("옵션은 1 혹은 2만 입력 가능합니다. 다시 입력해주세요.");
            option = scanner.nextLine();
        }
        return new String[]{position, option};
    }

    private boolean isValid(String position) {
        char alp = position.charAt(0);
        char num = position.charAt(1);
        return 'a' <= alp && alp <= 'i' && '1' <= num && num <= '9';
    }

    public void end() {
        if (win) {
            System.out.println("Win!! You found all of the mines");
        } else {
            System.out.println("Bomb!! You fail!!");
        }
    }

    public void setWin() {
        win = true;
    }

    public void setEnd() {
        end = true;
    }
}
