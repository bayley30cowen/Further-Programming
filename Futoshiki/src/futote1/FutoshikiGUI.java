
package futote1;

/**
 *
 * @author 184514
 * @version 08/05/19
 */
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/* I have chosen to use swing, since i could find more content online and in books to help with this */
/* I have a small amount of experience with swing, and i got help from PAL with increasing my knowledge of swing */
public class FutoshikiGUI extends JFrame {

    public static void main(String[] args) {
        FutoshikiGUI puzzleWindow = new FutoshikiGUI();
        puzzleWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JFrame frame;
    int sizeOfGrid;
    //saved as a serialised object
    String filename = "Puzzle.ser";
    FutoshikiGrid puzzle;

    public FutoshikiGUI() {
        super("Futoshiki Game");
        start();
    }

    private void start() {
        JFrame start = new JFrame();
        JPanel scp = (JPanel) start.getContentPane();
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        // the GUI will use resizing methods to make it always the same size, to make it look nice
        start.setResizable(false);
        start.setPreferredSize(new Dimension(700, 500));
        start.setLayout(new BorderLayout());
        JTextArea instructions = new JTextArea();
        instructions.setText("         Please enter the size of grid you wish to start with in the box below\n\n");
        scp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        scp.setBackground(new Color(254, 197, 255));
        instructions.setBorder(new EmptyBorder(60, 30, 50, 50));
        instructions.setEditable(false);
        instructions.setBackground(new Color(254, 197, 255));
        instructions.setFont(new Font("Serif", Font.PLAIN, 20));

        start.add(instructions, BorderLayout.CENTER);

        JPanel size = new JPanel();
        size.setLayout(new BorderLayout());
        size.setPreferredSize(new Dimension(0, 150));

        JPanel question = new JPanel();
        question.setLayout(new GridLayout(2, 1));

        JLabel sizeQuestion = new JLabel();
        sizeQuestion.setText("What size would you like the grid?");
        sizeQuestion.setHorizontalAlignment(JLabel.CENTER);
        sizeQuestion.setFont(new Font("Serif", Font.PLAIN, 20));
        sizeQuestion.setBorder(new EmptyBorder(20, 20, 30, 20));
        sizeQuestion.setOpaque(true);
        sizeQuestion.setBackground(new Color(254, 197, 255));
        question.add(sizeQuestion);

        JLabel sizeIns = new JLabel();
        sizeIns.setText("press enter to continue!!!");
        sizeIns.setHorizontalAlignment(JLabel.CENTER);
        sizeIns.setFont(new Font("Serif", Font.PLAIN, 12));
        sizeIns.setPreferredSize(new Dimension(100, 0));
        sizeIns.setBorder(new EmptyBorder(20, 20, 30, 20));
        sizeIns.setOpaque(true);
        sizeIns.setBackground(new Color(254, 197, 255));
        sizeIns.setForeground(Color.gray);
        question.add(sizeIns);

        JTextField entry = new JTextField();
        entry.setHorizontalAlignment(JLabel.CENTER);
        entry.setFont(new Font("Serif", Font.PLAIN, 30));
        entry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int input = Integer.parseInt(entry.getText());
                    if (input <= 0) {
                        JOptionPane.showMessageDialog(frame, "Please enter a number greater than 0");
                    }
                    puzzle = new FutoshikiGrid(input);
                    fill(2);
                    start.dispose();
                    startGame();
                } catch (NumberFormatException ee) {
                    JOptionPane.showMessageDialog(frame, "Only numbers can be entered");
                }
            }
        });

        size.add(question, BorderLayout.CENTER);
        size.add(entry, BorderLayout.SOUTH);
        start.add(size, BorderLayout.SOUTH);
        start.pack();
        start.setVisible(true);
    }
    
    private void setupNewSize() {
        try {
            int input = Integer.parseInt(JOptionPane.showInputDialog("What size would you like the grid?\n"));
            if (input <= 1 || input > 13) {
                JOptionPane.showMessageDialog(frame, "Please enter a number greater than 1, but less than 13");
                setupNewSize();
            }
            sizeOfGrid = input;
            puzzle = new FutoshikiGrid(sizeOfGrid);
            fill(1);
            startGame();
        } catch (NumberFormatException ee) {
            JOptionPane.showMessageDialog(frame, "Please enter a number!!!");
            setupNewSize();
        }
    }
    private void setupNewDiff() {
        try {
            int diff = Integer.parseInt(JOptionPane.showInputDialog("What difficulty do you wish to play on \nEasy: 1\nNormal: 2\nHard: 3\nPlease enter the corrosponding number!!\n"));
            if (diff <= 0 || diff > 3) {
                JOptionPane.showMessageDialog(frame, "Please enter 1, 2 or 3!");
                setupNewDiff();
            }
            puzzle = new FutoshikiGrid(sizeOfGrid);
            if (sizeOfGrid > 4) {
                fill(diff);
            }
            else {
                fill(1);   
            }
            
            startGame();
        } catch (NumberFormatException ee) {
            JOptionPane.showMessageDialog(frame, "Please enter a number!!!");
            setupNewDiff();
        }
    }
    private void fill(int x) {
        //easy difficulty
        if (x == 1) {
            puzzle.fillPuzzle(1,1);
            while (!puzzle.isLegal() || !puzzle.checkIfSolvable()) {
                puzzle.fillPuzzle(1,1);
            }
        }
        if (x == 2) {
            puzzle.fillPuzzle(2,2);
            while (!puzzle.isLegal() || !puzzle.checkIfSolvable()) {
                puzzle.fillPuzzle(2,1);
            }
        }
        if (x == 3) {
            puzzle.fillPuzzle(1,3);
            while (!puzzle.isLegal() || !puzzle.checkIfSolvable()) {
                puzzle.fillPuzzle(1,3);
            }
        }
    }

    private void startGame() {

        JPanel futoGrid = new JPanel();
        JPanel cp = (JPanel) getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        JPanel west = new JPanel();
        west.setLayout(new GridLayout(2, 1));
        west.setBackground(new Color(219, 197, 255));
        //tt
        west.setBorder(new EmptyBorder(25, 25, 25, 25));
        cp.add(west, BorderLayout.WEST);
        cp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));


        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Features");

        JMenuItem menuNewSize = new JMenuItem("New Size");
        menuNewSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewSize();
            }
        });
        JMenuItem menuNewDiff = new JMenuItem("New Difficulty");
        menuNewDiff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewDiff();
            }
        });
        JMenuItem menuSave = new JMenuItem("Save Grid");
        menuSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // save the object to file
                FileOutputStream fos = null;
                ObjectOutputStream out = null;
                try {
                    fos = new FileOutputStream(filename);
                    out = new ObjectOutputStream(fos);
                    out.writeObject(puzzle);

                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JMenuItem menuOpen = new JMenuItem("Open Grid");
        menuOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileInputStream f = null;
                ObjectInputStream in = null;
                try {
                    f = new FileInputStream(filename);
                    in = new ObjectInputStream(f);
                    puzzle = (FutoshikiGrid) in.readObject();
                    in.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                startGame();
            }
        });

        menuFile.add(menuNewSize);
        menuFile.add(menuNewDiff);
        menuFile.add(menuSave);
        menuFile.add(menuOpen);

        menuBar.add(menuFile);

        cp.add(menuBar);
        setJMenuBar(menuBar);

        JLabel probLabel = new JLabel();
        probLabel.setText("Buttons");
        probLabel.setHorizontalAlignment(JLabel.CENTER);
        probLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        probLabel.setBorder(new EmptyBorder(20, 20, 30, 20));
        probLabel.setOpaque(true);
        probLabel.setBackground(new Color(219, 197, 255));
        JLabel probLabel2 = new JLabel();
        
        JTextArea results = new JTextArea();
        results.setLineWrap(true);
        results.setPreferredSize(new Dimension(300, 100));
        results.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        results.setBackground(Color.white);
        results.setOpaque(true);
        results.setText("Problems will be printed here...");
        results.setForeground(Color.gray);
        results.setEditable(false);


        JPanel buttons = new JPanel();
        buttons.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JButton solve = new JButton("   Solve for me   ");
        solve.setMargin(new Insets(10, 10, 10, 10));
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to solve it?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    if (puzzle.solve()) {
                        puzzle.solve();
                        startGame();
                        JOptionPane.showMessageDialog(frame, "The puzzle has been solved solved for you, very lazy!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "The puzzle can't be solved at this moment in time, start to make changes!");
                    }
                }
                }
            });

        JButton check = new JButton("Check Puzzle");
        check.setMargin(new Insets(10, 10, 10, 10));

        check.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                if (!puzzle.isLegal()) {
                    results.setForeground(Color.black);
                    results.setText(puzzle.getAllProblems());
                } else if (puzzle.checkIfFull() && puzzle.isLegal()) {
                    results.setForeground(Color.black);
                    results.setText("");
                    JOptionPane.showMessageDialog(frame, "Complete!");
                    //test
                    fill(1);
                    startGame();
                } else {
                    results.setText("Results will show here...");
                    results.setForeground(Color.gray);
                }
            }
        }
        );

        JButton newPuzzle = new JButton("  Generate new puzzle ");
        newPuzzle.setMargin(new Insets(10, 10, 10, 10));
        newPuzzle.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                fill(2);
                startGame();
            }
        }
        );
        JButton giveUp = new JButton("   GiveUp   ");
        giveUp.setMargin(new Insets(10, 10, 10, 10));

        giveUp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to give up?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(frame, "Unlucky, play again soon!");
                     setVisible(false); //you can't see me!
                        dispose(); //Destroy the JFrame object  
                }
            }
        }
        );
       
        c.gridx = 0;
        c.gridy = 2;
        buttons.add(solve, c);
        c.gridx = 0;
        c.gridy = 4;
        buttons.add(check, c);
        c.gridx = 0;
        c.gridy = 6;
        buttons.add(newPuzzle, c);
        c.gridx = 0;
        c.gridy = 8;
        buttons.add(giveUp, c);
        c.gridx = 0;
        c.gridy = 0;
        buttons.add(probLabel, c);
        buttons.setBackground(new Color(219, 197, 255));
        west.add(buttons);
        west.add(new JScrollPane(results));


        futoGrid = new JPanel();
        futoGrid.setBorder(new EmptyBorder(10, 10, 10, 10));
        futoGrid.setBackground(new Color(254, 197, 255));
//display grid
        futoGrid.setLayout(new GridLayout((2 * (puzzle.getSize()) - 1), 2 * (puzzle.getSize()) - 1));
        for (int i = 0; i < (puzzle.getSize()); i++) {
            for (int j = 0; j < (puzzle.getSize()); j++) {
                int i1 = i;
                int j1 = j;

                JPanel jPan = new JPanel();
                jPan.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
                //Uneditable
                if (puzzle.getSquare(i, j).getInitial() == true) {
                    jPan.setBackground(Color.gray);
                }
                else {
                   jPan.setBackground(Color.white); 
                }
                
                jPan.setPreferredSize(new Dimension(300 / puzzle.getSize(), 300 / puzzle.getSize()));
                if (puzzle.getSquare(i, j).getInitial() == true) {
                    JLabel num = new JLabel();
                    num.setFont(new Font("Arial", Font.BOLD, 200 / (puzzle.getSize())));
                    num.setBackground(Color.white);
                    num.setForeground(Color.white);
                    num.setText(puzzle.getSquare(i, j).getNumber() + "");
                    jPan.add(num);
                } else {
                    JTextField num = new JTextField(1);
                    num.setFont(new Font("Arial", Font.BOLD, 200 / (puzzle.getSize())));
                    num.setHorizontalAlignment(JTextField.CENTER);
                    num.setBorder(javax.swing.BorderFactory.createEmptyBorder());

                    if (puzzle.getSquare(i, j).getNumber() != 0) {
                        num.setText(puzzle.getSquare(i, j).getNumber() + "");
                    }
                    num.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            try {
                                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    num.setText("0");
                                    startGame();
                                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                                    num.setText("0");
                                    startGame();
                                }
                                int current = Integer.parseInt(num.getText());
                                puzzle.setSquare(i1, j1, current);
                                if (num.getText().equals("0")) {
                                    startGame();
                                }
                            } catch (NumberFormatException ee) {
                                JOptionPane.showMessageDialog(frame, "You can only enter numbers");
                                num.setText("0");
                                startGame();
                            } catch (IllegalArgumentException ee) {
                                JOptionPane.showMessageDialog(frame, "You are not in the range of the grid");
                                num.setText("0");
                                startGame();
                            }
                        }

                    });
                    jPan.add(num);
                }
                futoGrid.add(jPan);

                if (j < (puzzle.getSize()) - 1) {
                    JPanel jP1 = new JPanel();
                    jP1.setBackground(new Color(254, 197, 255));
                    JLabel rCon = new JLabel();
                    rCon.setFont(new Font("Arial", Font.BOLD, 200 / (puzzle.getSize())));
                    rCon.setText(puzzle.getRowConstraint(i, j).constraintType() + "");
                    rCon.setBackground(new Color(254, 197, 255));
                    jP1.add(rCon);
                    futoGrid.add(jP1);
                }
            }


            if (i < (puzzle.getSize()) - 1) {
                for (int k = 0; k < (puzzle.getSize()); k++) {
                    JPanel jP2 = new JPanel();
                    jP2.setBackground(new Color(254, 197, 255));
                    JLabel cCon = new JLabel();
                    cCon.setFont(new Font("Arial", Font.BOLD, 200 / (puzzle.getSize())));
                    cCon.setBackground(new Color(254, 197, 255));
                    cCon.setText(puzzle.getColConstraint(i, k).constraintType() + "");
                    jP2.add(cCon);
                    futoGrid.add(jP2);
                    if (k < (puzzle.getSize()) - 1) {
                        JPanel empty = new JPanel();
                        empty.setBackground(new Color(254, 197, 255));
                        futoGrid.add(empty);
                    }
                }
            }
        }

        cp.add(futoGrid, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

}
