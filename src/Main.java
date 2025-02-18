import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String mainMenuText = """
                Головне Меню (для переходу між пунктами вводьте їх у строку)
                1. Нова гра
                2. Налаштування
                0. Вихід
                Ваш вибір:
                """;

        String settingsText = """
                Налаштування:
                Введіть бажаний розмір поля (3, 5, 7 або 9):
                """;
        int height = 7;
        while (true) {
            System.out.print(mainMenuText);
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    game(sc, height);
                    break;
                case "2":
                    height = settings(sc, settingsText);
                    break;
                case "0":
                    System.out.println("До зустрічі!");
                    return;
                default:
                    System.out.println("Некоректний формат вводу");
                    break;
            }
        }
    }
    public static void game(Scanner sc, int height) {
        int width = height;
        int size = (height - 1) / 2;
        boolean start = true;

        while (start) {
            char[][] board = new char[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (i == 0 && j == 0) {
                        board[i][j] = ' ';
                    } else if (i == 0 && j % 2 == 1) {
                        board[i][j] = (char) ('0' + (j / 2) + 1);
                    } else if (j == 0 && i % 2 == 1) {
                        board[i][j] = (char) ('0' + (i / 2) + 1);
                    } else if (i == 0 || j == 0) {
                        board[i][j] = ' ';
                    } else if (i % 2 == 1 && j % 2 == 1) {
                        board[i][j] = ' ';
                    } else if (i % 2 == 1 && j % 2 == 0) {
                        board[i][j] = '|';
                    } else if (i % 2 == 0 && j % 2 == 1) {
                        board[i][j] = '-';
                    } else {
                        board[i][j] = '+';
                    }
                }
            }

            System.out.print("Введіть знак першого гравця: ");
            char turn1 = sc.next().charAt(0);
            System.out.print("Введіть знак другого гравця: ");
            char turn2 = sc.next().charAt(0);
            sc.nextLine();
            char turn = turn1;
            for (int count = 0; count < size * size; count++) {
                System.out.println();
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        System.out.print(board[i][j]);
                    }
                    System.out.println();
                }
                int row, col;
                while (true) {
                    System.out.print("Гравець " + turn + ", введіть стовпець (1-" + size + "): ");
                    if (sc.hasNextInt()) {
                        col = sc.nextInt();
                    } else {
                        System.out.println("Введіть число!");
                        sc.next();
                        continue;
                    }
                    System.out.print("Гравець " + turn + ", введіть рядок (1-" + size + "): ");
                    if (sc.hasNextInt()) {
                        row = sc.nextInt();
                    } else {
                        System.out.println("Введіть число!");
                        sc.next();
                        continue;
                    }
                    if (row >= 1 && row <= size && col >= 1 && col <= size) {
                        int gridRow = row * 2 - 1;
                        int gridCol = col * 2 - 1;
                        if (board[gridRow][gridCol] == ' ') {
                            board[gridRow][gridCol] = turn;
                            sc.nextLine();
                            break;
                        } else {
                            System.out.println("Клітинка вже зайнята.");
                        }
                    } else {
                        System.out.println("Введіть значення від 1 до " + size + ": ");
                    }
                }
                boolean win = false;
                if (count >= 4) {
                    win = checker(board, turn, size);
                }
                if (win) {
                    System.out.println();
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            System.out.print(board[i][j]);
                        }
                        System.out.println();
                    }
                    System.out.println("Виграв гравець " + turn + "!!!");
                    break;
                }
                if (count == size * size - 1 && !win) {
                    System.out.println();
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            System.out.print(board[i][j]);
                        }
                        System.out.println();
                    }
                    System.out.println("НІЧИЯ!!!");
                    break;
                }
                turn = (turn == turn1) ? turn2 : turn1;
            }
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Натисніть Enter для виходу в меню або введіть 1 для рестарту:");
                String choice = sc.nextLine();
                if (choice.length() == 1 && choice.charAt(0) == '1') {
                    start = true;
                    validInput = true;
                } else if (choice.isEmpty()) {
                    start = false;
                    validInput = true;
                } else {
                    System.out.println("Некоректний ввід! Введіть 1 або натисніть Enter.");
                }
            }
        }
    }
    public static boolean checker(char[][] game, char turn, int size) {
        for (int i = 1; i < size * 2; i += 2) {
            for (int j = 1; j <= size * 2 - 4; j += 2) {
                if (game[i][j] == turn && game[i][j + 2] == turn && game[i][j + 4] == turn) {
                    return true;
                }
            }
        }
        for (int j = 1; j < size * 2; j += 2) {
            for (int i = 1; i <= size * 2 - 4; i += 2) {
                if (game[i][j] == turn && game[i + 2][j] == turn && game[i + 4][j] == turn) {
                    return true;
                }
            }
        }
        for (int i = 1; i <= size * 2 - 4; i += 2) {
            for (int j = 1; j <= size * 2 - 4; j += 2) {
                if (game[i][j] == turn && game[i + 2][j + 2] == turn && game[i + 4][j + 4] == turn) {
                    return true;
                }
            }
        }
        for (int i = 1; i <= size * 2 - 4; i += 2) {
            for (int j = size * 2 - 1; j >= 5; j -= 2) {
                if (game[i][j] == turn && game[i + 2][j - 2] == turn && game[i + 4][j - 4] == turn) {
                    return true;
                }
            }
        }
        return false;
    }
    public static int settings(Scanner sc, String settingsText) {
        System.out.print(settingsText);
        while (true) {
            if (sc.hasNextInt()) {
                int newSize = sc.nextInt();
                sc.nextLine();
                int height;
                if (newSize >= 3 && newSize % 2 == 1 && newSize <= 9) {
                    height = newSize * 2 + 1;
                    System.out.println("Розмір змінено!");
                    return height;
                } else {
                    System.out.println("Невірний розмір!");
                }
            } else {
                System.out.println("Введіть коректний розмір");
                sc.nextLine();
            }
        }
    }
}
