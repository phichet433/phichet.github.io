package org.example;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.text.DecimalFormat;

public class BMI extends JFrame {

    // Create text fields for weight, height, totalBMI
    private JTextField jtfHeight = new JTextField();
    private JTextField jtfWeight = new JTextField();
    private JTextField jtfTotalBMI = new JTextField();
    private JTextField jtfStatusBMI = new JTextField();
    // Create a Compute Calculate BMI button
    private JButton jbtComputeBMI = new JButton("Kira");

    public static void main(String[] args) {
        BMI frame = new BMI();
        frame.pack();
        // frame.setTitle("BMI Calculator");
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public BMI() {
        // Panel p1 to hold labels and text fields
        JPanel p1 = new JPanel(new GridLayout(5, 2));
        p1.add(new JLabel("Masukkan Tinggi (Meter)"));
        p1.add(jtfHeight);
        p1.add(new JLabel("Masukkan Berat (Kilogram)"));
        p1.add(jtfWeight);
        p1.add(new JLabel("BMI Anda:  "));
        p1.add(jtfTotalBMI);
        p1.add(new JLabel(" "));
        p1.add(jtfStatusBMI);
        //  p1.setBorder(new TitledBorder("Masukkan Tinggi (Meter), Berat (Kg)"));

        // Panel p2 to hold the button
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p2.add(jbtComputeBMI);

        // Add the panels to the frame
        add(p1, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);

        // Register listener
        jbtComputeBMI.addActionListener(new ButtonListener());
    }


    /** Handle the Compute BMI results */
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {


            try {
                double meters    = Double.parseDouble(jtfHeight.getText());
                double kilograms = Double.parseDouble(jtfWeight.getText());


                double bmi= kilograms/( meters*meters);
                String a ="" ;
                DecimalFormat deci= new DecimalFormat ("##.##");
                String format= deci.format(bmi);
                jtfTotalBMI.setText(""+format);
                if ((bmi < 18))
                    a = "under weight";
                else
                if ((bmi>= 18)&&(bmi <= 25))

                    a = "ideal weight";
                else
                if ((bmi> 25)&&(bmi <= 30))
                    a = "over weight";
                else
                if ((bmi> 30))
                    a = "obess";

                jtfStatusBMI.setText(""+ a );


            } catch (Exception nfe) {

                //JOptionPane.showMessageDialog(rootPane, "Please enter real number");
                System.out.println("\nYOU HAVE ENTERED WRONG INPUT!!!");

            }
        }
    }
}
