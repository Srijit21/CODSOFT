import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    private static final char EMPTY = ' ';
    private static final char HUMAN = 'X';
    private static final char AI = 'O';

    private char[][] board;

    public TicTacToe() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isGameOver() {
        return hasWon(HUMAN) || hasWon(AI) || getAvailableMoves().isEmpty();
    }

    public boolean hasWon(char player) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }
        return false;
    }

    public List<int[]> getAvailableMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    public void makeMove(int row, int col, char player) {
        board[row][col] = player;
    }

    public int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        
        for (int[] move : getAvailableMoves()) {
            makeMove(move[0], move[1], AI);
            int score = minimax(0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            makeMove(move[0], move[1], EMPTY);
            
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int minimax(int depth, int alpha, int beta, boolean isMaximizing) {
        if (hasWon(AI)) return 10 - depth;
        if (hasWon(HUMAN)) return depth - 10;
        if (getAvailableMoves().isEmpty()) return 0;

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] move : getAvailableMoves()) {
                makeMove(move[0], move[1], AI);
                int eval = minimax(depth + 1, alpha, beta, false);
                makeMove(move[0], move[1], EMPTY);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] move : getAvailableMoves()) {
                makeMove(move[0], move[1], HUMAN);
                int eval = minimax(depth + 1, alpha, beta, true);
                makeMove(move[0], move[1], EMPTY);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe game = new TicTacToe();

        while (!game.isGameOver()) {
            game.printBoard();
            System.out.println("Enter your move (row and column): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (game.board[row][col] == EMPTY) {
                game.makeMove(row, col, HUMAN);
                if (!game.isGameOver()) {
                    int[] aiMove = game.findBestMove();
                    game.makeMove(aiMove[0], aiMove[1], AI);
                }
            } else {
                System.out.println("This move is not valid");
            }
        }
        
        game.printBoard();
        if (game.hasWon(HUMAN)) {
            System.out.println("You win!");
        } else if (game.hasWon(AI)) {
            System.out.println("AI wins!");
        } else {
            System.out.println("It's a draw!");
        }

        scanner.close();
    }
}
