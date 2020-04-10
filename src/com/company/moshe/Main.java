package com.company.moshe;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static java.awt.GridBagConstraints.CENTER;

public class Main {
    int[] rndNUmbers = new int[3];
    int numbers1 = 0;
    int numbers2 = 0;
    int signNum = 0;
    int score=0;


    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.mainRun();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mainRun() throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame();
        JPanel panel = new JPanel( new GridLayout(2, 3));

        JLabel label = new JLabel();
        JTextField textField = new JTextField();
        JButton button = new JButton();


        int setY = frame.getY()/2;
        int setX = frame.getX()/2;

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        textField.setSize( frame.getWidth(),80);
        Border thickBorder = new LineBorder(Color.BLACK, 2);
        textField.setBorder(thickBorder);


        label.setText("this is text");
        label.setSize(50,50);

        button.setText("Enter");
        button.setSize(new Dimension());

        panel.add(label);
        panel.add(button);
        panel.add(textField);

        frame.add(panel);
        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(400, 400);
        frame.setTitle("Math Game");
        frame.setVisible(true);

        String ans = "";
        String question = "";

        String filename = "score.txt";

        String userDirectory = System.getProperty("user.name");

        String absoluteFilePath =  "C:\\Users\\" + userDirectory + "\\Documents\\" + filename;

        //absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
//        System.out.println(absoluteFilePath);
        File file = new File(absoluteFilePath);
        Path path = Paths.get(file.getPath());
        if (!file.exists()) {
            file.createNewFile();
        }


        BufferedReader br = Files.newBufferedReader(path);
        if (br.readLine() != null) {
            score = Integer.parseInt(br.readLine());
        } else {
            score = 0;
        }
        //it will delete the file context every time, but only after it saves the score to the new program run,
        // it will then save it again when you're done.
        BufferedWriter bw = new BufferedWriter(new FileWriter(path.toString(), false));
        newQ(label);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveScore(bw,br,label,textField,score);
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    label.setText(checkANS(question, label, textField, br, bw));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });
    }

    public void sortNum() {
        rndNUmbers = rndNum();
        numbers1 = rndNUmbers[0];
        numbers2 = rndNUmbers[1];
        signNum = rndNUmbers[2];
    }

    public static String saveScore(BufferedWriter bw, BufferedReader br, JLabel label, JTextField textField, int score) {
        String print = "";
        try {
            bw.newLine();
            bw.write(String.valueOf(score));
            label.setText("saved");
            print = "saved";
        } catch (IOException e) {
            label.setText("problem with saving file: " + e.getMessage());
        } finally {
            try {
                bw.flush();
                bw.close();
                br.close();
            } catch (IOException e) {
                label.setText("problem with saving file: " + e.getMessage());
            }
        }
        return print;
    }
    int changeup =0;
    public String newQ(JLabel label) {
        Random numRND = new Random();


        sortNum();
        String[] sign = new String[4];
        sign[0] = "+";
        sign[1] = "-";
        sign[2] = "*";
        sign[3] = "/";
        if (numbers1 < numbers2) { // 1 =1 2 =2
            changeup =numbers1; // c = 1
            numbers1 = numbers2; // 1 =  2
            numbers2 = changeup; // 2 = c(1)

        }
        if (signNum > 3) {
            signNum = numRND.nextInt(4);
        }
        label.setText(" " +  numbers1 + " " + sign[signNum] + " " + numbers2 + " ");
        return " " +  numbers1 + " " + sign[signNum] + " " + numbers2 + " ";

    }

    public String checkANS(String label, JLabel jLabel, JTextField textField,
                           BufferedReader br, BufferedWriter bw) throws IOException {

        int ansAI;
        if (textField.getText().toLowerCase().equals("next")) {
            label = newQ(jLabel);
        } else if (textField.getText().toLowerCase().equals("save")) {
            label = saveScore(bw, br, jLabel, textField, score);
        } else if (textField.getText().toLowerCase().equals("score")) {
            label = " Your score is: " + score;

        } else {
            if (signNum == 0) {
                ansAI = numbers1 + numbers2;
            } else if (signNum == 1) {
                ansAI = numbers1 - numbers2;
            }else if (signNum == 2) {
                ansAI = numbers1 * numbers2;
            } else {
                ansAI = numbers1 / numbers2;
            }
            if (Integer.parseInt(textField.getText()) == ansAI) {
                score++;
                label += " Very Well Done! ";
            } else {
                label += " No, it's: " + ansAI;
            }
            label += newQ(jLabel);
        }
        return label;
    }

    public int[] rndNum() {
        Random numRND = new Random();
        int numbers2 = numRND.nextInt(100);
        int numbers1 = numRND.nextInt(100);
        int signNum = numRND.nextInt(4);
        int[] ints = new int[3];
        if (numbers1 <= numbers2) {
            numbers1 = numRND.nextInt(100);
        }
        ints[0] = numbers1;
        ints[1] = numbers2;
        ints[2] = signNum;


        return ints;
    }
}