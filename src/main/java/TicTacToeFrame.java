import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeFrame extends JFrame {
    private TicTacToeTile[][] tiles;
    private String currentPlayer = "X";
    private JButton quitButton;
    private int count = 0;
    public TicTacToeFrame() {
        super("Tic Tac Toe");

        //layout
        setLayout(new BorderLayout());

        //panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        tiles = new TicTacToeTile[3][3];
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                tiles[row][col] = new TicTacToeTile(row, col);
                tiles[row][col].setFont(new Font("Times New Roman", Font.BOLD, 60));
                tiles[row][col].addActionListener(new TileListener());
                boardPanel.add(tiles[row][col]);
            }
        }

        //quit
        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        });

        add(boardPanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);
        TicTacToe.resetGame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setVisible(true);
    }

    private class TileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            TicTacToeTile tile = (TicTacToeTile) e.getSource();
            int row = tile.getRow();
            int col = tile.getCol();

            //empty checks
            if (!TicTacToe.getCell(row, col).equals(" "))
            {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "Please choose an empty cell!");
                return;
            }

            //updates
            TicTacToe.makeMove(row, col, currentPlayer);
            tile.setText(currentPlayer);
            count++;

            //checks
            if (count >= 5 && TicTacToe.checkWin(currentPlayer))
            {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, currentPlayer + " wins!");
                askPlayAgain();
                return;
            }
            if (count >= 7 && TicTacToe.checkTie() && count < 9)
            {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "It's a tie! (Board not full)");
                askPlayAgain();
                return;
            }
            if (count == 9 && TicTacToe.checkTie()) {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "It's a tie! (Board full)");
                askPlayAgain();
                return;
            }

            if (currentPlayer.equals("X"))
            {
                currentPlayer = "O";
            }
            else
            {
                currentPlayer = "X";
            }
        }
    }

    private void askPlayAgain() {
        int choice = JOptionPane.showConfirmDialog(this, "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION)
        {
            TicTacToe.resetGame();
            currentPlayer = "X";
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    tiles[r][c].setText("");
                    count = 0;
                }
            }
        }
        else
        {
            System.exit(0);
        }
    }
}