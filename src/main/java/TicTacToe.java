import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] board = new JButton[3][3]; //틱택토 버튼
    private char currentPlayer = 'X'; //현재 턴 플레이어 (X시작)
    private JLabel statusLabel; //진행 출력 라벨 (턴 또는 승패 등)
    private JButton resetButton;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Player X Turn", SwingConstants.CENTER); //시작 (X)턴 출력
        statusLabel.setFont(new Font("", Font.BOLD, 18));
        add(statusLabel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3)); //틱택토 보드 패널

        for (int row = 0; row < 3; row++) { //버튼 생성
            for (int col = 0; col < 3; col++) {
                board[row][col] = new JButton("");
                board[row][col].setFont(new Font("", Font.BOLD, 60)); //OX가 너무 안보여서 폰트 사이즈 및 BOLD 설정
                board[row][col].addActionListener(this);
                gamePanel.add(board[row][col]);
            }
        }
        add(gamePanel, BorderLayout.CENTER);
        resetButton = new JButton("Reset Game"); //리셋버튼
        resetButton.setFont(new Font("", Font.BOLD, 18));
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });
        add(resetButton, BorderLayout.SOUTH);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource(); //버튼이 클릭 되면
        if (!clickedButton.getText().equals("")) { //눌린 칸 무시
            return;
        }

        clickedButton.setText(String.valueOf(currentPlayer)); //현재 플레이어 표시
        if (isWinner()) { //승자 나오면
            statusLabel.setText("Player " + currentPlayer + " Wins!"); //승자 표시
            JOptionPane.showMessageDialog(this,
                    "Player " + currentPlayer + " Wins!");
            disableBoard();
            return;
        }
        if (isDraw()) { //무승부 뜨면
            statusLabel.setText("Draw Game!"); //무승부 표시
            JOptionPane.showMessageDialog(this,
                    "Draw Game!");
            return;
        }
        switchPlayer(); //게임 진행중이면 다음턴
    }

    private void switchPlayer() { //플레이어 변경
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
        statusLabel.setText("Player " + currentPlayer + " Turn");
    }

    private boolean isWinner() { //승리 판단
        for (int i = 0; i < 3; i++) { //가로
            if (!board[i][0].getText().equals("") &&
                    board[i][0].getText().equals(board[i][1].getText()) &&
                    board[i][1].getText().equals(board[i][2].getText())) {
                return true; //승리 반환
            }
        }
        for (int i = 0; i < 3; i++) { //세로
            if (!board[0][i].getText().equals("") &&
                    board[0][i].getText().equals(board[1][i].getText()) &&
                    board[1][i].getText().equals(board[2][i].getText())) {
                return true;
            }
        }
        if (!board[0][0].getText().equals("") && //좌상-우하 대각
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {
            return true;
        }
        if (!board[0][2].getText().equals("") && //우상-좌하 대각
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {
            return true;
        }
        return false; //게임 계속
    }
    private boolean isDraw() { //무승부
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].getText().equals("")) { //빈칸이 있으면
                    return false; //게임 계속
                }
            }
        }
        return true; //빈칸 없으면 true(무승부) 반환
    }

    private void disableBoard() { //게임 끝나고 버튼 못누르게
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col].setEnabled(false);
            }
        }
    }

    private void resetBoard() { //초기화
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col].setText("");
                board[row][col].setEnabled(true);
            }
        }
        currentPlayer = 'X';
        statusLabel.setText("Player X Turn");
    }

    public static void main(String[] args) {

        new TicTacToe();
    }
}