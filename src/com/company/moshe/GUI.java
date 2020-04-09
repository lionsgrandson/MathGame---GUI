package com.company.moshe;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

public class GUI {
    public static void main(String[] args) {
//        try {
        MathWork mathWork = new MathWork();

        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        JTextArea textArea = new JTextArea();

        frame.add(textArea);
        frame.add(label);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(2,1));

        textArea.setSize(10, 10);
        Border thickBorder = new LineBorder(Color.BLACK, 5);
        textArea.setBorder(thickBorder);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setText("this is text");

        frame.setSize(500, 500);
        frame.setTitle("Math Game");
        frame.setVisible(true);

//            label.setText(mathWork.work());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
