package ppTracker;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Coordinator implements ActionListener {

    private final JFrame frame;
    private final JPanel panel;
    private final JLabel[] attackLabels;
    private final JButton[] incrementButtons;
    private final JButton[] decrementButtons;
    private final JCheckBox pressure;
    private final GridBagConstraints order = new GridBagConstraints();

    private final Attack[] attacks;

    // Creates GUI
    public Coordinator(Attack[] attacks) {

        this.attacks = attacks;

        frame = new JFrame("Pokemon Power Point Tracker");
        frame.setBounds(0, 0, 600, 400); // Sets size of frame

        panel = new JPanel(new GridBagLayout()); // Using GridBagLayout

        attackLabels = new JLabel[attacks.length]; // Button to increase power points
        incrementButtons = new JButton[attacks.length]; // Label that has the attack's name
        decrementButtons = new JButton[attacks.length]; // Button to decrease power points
        pressure = new JCheckBox("Pressure"); // CheckBox to see if the pressure ability is active

        order.insets = new Insets(5, 5, 5, 5); // Sets margins

        for(int i = 0; i < attacks.length; i++) {
            decrementButtons[i] = new JButton("-");
            order.gridx = 2;
            order.gridy = i + 1;
            panel.add(decrementButtons[i], order);

            attackLabels[i] = new JLabel(attacks[i].getAttackName() + " (" +
                    attacks[i].getTotalPowerPoints() + "/" + attacks[i].getTotalPowerPoints() + ")");
            order.gridx = 3;
            order.gridy = i + 1;
            panel.add(attackLabels[i], order);

            incrementButtons[i] = new JButton("+");
            order.gridx = 4;
            order.gridy = i + 1;
            panel.add(incrementButtons[i], order);

            incrementButtons[i].addActionListener(this);
            decrementButtons[i].addActionListener(this);
        }

        order.gridx = 1;
        order.gridy = 1;
        panel.add(pressure, order);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Attack[] attacks = promptUser();
        new Coordinator(attacks); // Calls constructor to create GUI
    }

    private static Attack[] promptUser() {
        int numOfAttacks;

        String attackNames;
        int powerPoints;

        String[] invalidInputs = new String[1];
        invalidInputs[0] = ""; // The only invalid expression in our program

        String[] alreadyUsedInputs;

        Scanner scanner = new Scanner(System.in);
        Utility utility = new Utility();

        numOfAttacks = utility.askUser(scanner,"How many moves does the pokemon have? ",
                1, 4); // Asks user for number of attacks
        Attack[] attacks = new Attack[numOfAttacks];

        for(int i = 0; i < attacks.length; i++) { // Asks users for attack information base on numOfAttacks
            scanner.nextLine();
            System.out.println();

            alreadyUsedInputs = new String[i];

            for(int j = 0; j < i; j++) {
                alreadyUsedInputs[j] = attacks[j].getAttackName(); // Stores names of previous attacks into this array
            }

            attackNames = utility.askUser(scanner, "What is the name of Attack " + (i + 1) + "? ", invalidInputs,
                    alreadyUsedInputs); // Asks user for name of attack, passes invalid inputs and previous inputs
            powerPoints = utility.askUser(scanner, "How many power points does \"" + attackNames + "\" have? ",
                    5, 64); // Asks user for power points of said attack
            attacks[i] = new Attack(attackNames, powerPoints);
        }

        scanner.close();

        return attacks;
    }

    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < attacks.length; i++) {

            if(e.getSource() == incrementButtons[i]) {

                // Increase power points
                if(attacks[i].getPowerPoints() < attacks[i].getTotalPowerPoints()) {
                    attacks[i].raisePowerPoint();
                    attackLabels[i].setText(attacks[i].getAttackName() + " (" +
                            attacks[i].getPowerPoints() + "/" + attacks[i].getTotalPowerPoints() + ")");

                    attackLabels[i].setForeground(Color.BLACK); // Sets text black

                    // Adds ToolTip
                    if(attacks[i].getPowerPoints() == attacks[i].getTotalPowerPoints()) {
                        incrementButtons[i].setToolTipText("PP for this attack is full");
                    }

                    // Removes ToolTip
                    else {
                        incrementButtons[i].setToolTipText(null);
                        decrementButtons[i].setToolTipText(null);
                    }
                }

                // Adds ToolTip
                else {
                    incrementButtons[i].setToolTipText("PP for this attack is full");
                }
            }

            if(e.getSource() == decrementButtons[i]) {
                // Decreases power points
                if(attacks[i].getPowerPoints() > 0) {

                    attacks[i].lowerPowerPoint();

                    if(pressure.isSelected() && attacks[i].getPowerPoints() != 0) {
                        attacks[i].lowerPowerPoint(); // Reduces power points by two if pressure is active
                    }

                    attackLabels[i].setText(attacks[i].getAttackName() + " (" +
                            attacks[i].getPowerPoints() + "/" + attacks[i].getTotalPowerPoints() + ")");

                    // Adds tool tip (and makes text red)
                    if(attacks[i].getPowerPoints() == 0) {
                        attackLabels[i].setForeground(Color.RED);
                        decrementButtons[i].setToolTipText("PP for this attack has run out");
                    }

                    // Removes ToolTip
                    else {
                        incrementButtons[i].setToolTipText(null);
                        decrementButtons[i].setToolTipText(null);
                    }
                }
            }
        }
    }
}
