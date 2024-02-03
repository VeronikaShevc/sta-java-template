import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Game {
    private int playerX, playerY, wumpusX, wumpusY, batsX, batsY, SIZE;
    private Random random = new Random();
    private JLabel positionLabel;

    public Game() {
        while (true) {
            String sizeStr = JOptionPane.showInputDialog("Enter the size of the game:");
            this.SIZE = Integer.parseInt(sizeStr);
            if(this.SIZE <= 2 || this.SIZE >=30 ) {
                JOptionPane.showMessageDialog(null, "Size must be more than 2. Please enter again.");
            } else {
                initializePositions();
                break;
            }
        }
    }

    public void start() {
        JFrame frame = new JFrame("Hunt the Wumpus");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        positionLabel = new JLabel();
        frame.add(positionLabel, BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(SIZE, SIZE));
        String[] directions = {"Up", "Down", "Left", "Right"};
        for (String direction : directions) {
            addButton(panel, direction);
        }
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
        updatePosition();
    }

    private void addButton(JPanel panel, String direction) {
        JButton button = new JButton(direction);
        button.addActionListener(e -> {
            movePlayer(direction.toLowerCase());
            updatePosition();
        });
        panel.add(button);
    }

    private void updatePosition() {
        positionLabel.setText("Position: " + (playerX + 1) + ", " + (playerY + 1));
        if (playerX == wumpusX && playerY == wumpusY) {
            JOptionPane.showMessageDialog(null, "Found the wumpus!");
            System.exit(0);
        }
        if (playerX == batsX && playerY == batsY) {
            JOptionPane.showMessageDialog(null, "Bats!");
            batsMove();
            updatePosition();
        }
    }

    private void initializePositions() {
        playerX = random.nextInt(SIZE);
        playerY = random.nextInt(SIZE);
    
        do {
            wumpusX = random.nextInt(SIZE);
        } while (wumpusX == playerX);
        
        do {
            wumpusY = random.nextInt(SIZE);
        } while (wumpusY == playerY);
    
        do {
            batsX = random.nextInt(SIZE);
        } while (batsX == playerX || batsX == wumpusX);
        
        do {
            batsY = random.nextInt(SIZE);
        } while (batsY == playerY || batsY == wumpusY);
    }

    private void batsMove() {
        playerX = random.nextInt(SIZE);
        playerY = random.nextInt(SIZE);
    }

    private void movePlayer(String direction) {
        switch (direction) {
            case "up":
                if (playerY > 0) playerY--;
                break;
            case "down":
                if (playerY < SIZE - 1) playerY++;
                break;
            case "left":
                if (playerX > 0) playerX--;
                break;
            case "right":
                if (playerX < SIZE - 1) playerX++;
                break;
        }
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
