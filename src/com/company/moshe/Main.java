package com.company.moshe;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Main {
    int[] rndNUmbers = new int[3];
    int numbers1 = 0;
    int numbers2 = 0;
    int signNum = 0;
    int score = 0;
    int settingNum = 0;
    int settingSign = 0;

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
        JPanel panel = new JPanel();

        JLabel questionLabel = new JLabel();
        JLabel answerHereLabel = new JLabel("Answer Here");
        JLabel draftLabel = new JLabel("Draft");
        JTextField answerText = new JTextField("", 10);
        JTextArea thinkText = new JTextArea(10, 10);
        JButton button = new JButton();
        JButton seting = new JButton("Set");

        seting.setBounds(0, 210, 50, 50);
        seting.setVisible(false);

        questionLabel.setText("");
        questionLabel.setBounds(25, 10, 200, 40);

        answerHereLabel.setBounds(10, 60, 100, 20);

        Border thickBorder = new LineBorder(Color.BLACK, 2);
        answerText.setBorder(thickBorder);
        answerText.setBounds(10, 80, 140, 40);

        button.setText("Enter");
        button.setBounds(10, 120, 100, 40);

        draftLabel.setBounds(200, 10, 50, 30);

        thinkText.setBounds(155, 40, 120, 200);
        thinkText.setBorder(thickBorder);
        thinkText.setLineWrap(true);

        frame.add(seting);
        frame.add(questionLabel);
        frame.add(answerHereLabel);
        frame.add(answerText);
        frame.add(button);
        frame.add(draftLabel);
        frame.add(thinkText);
        frame.add(panel);

        frame.setTitle("Math Game");
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String ans = "";
        String question = "";

        String scoreFileName = "score.txt";
        String settingFileName = "setting.txt";

        String userDirectory = System.getProperty("user.name");

        String absoluteFilePath = "C:\\Users\\" + userDirectory + "\\Documents\\" + scoreFileName;
        String settingFilePath = "C:\\Users\\" + userDirectory + "\\Documents\\" + settingFileName;


        //absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
//        System.out.println(absoluteFilePath);
        File scoreFile = new File(absoluteFilePath);
        Path scorePath = Paths.get(scoreFile.getPath());

        File settingFiles = new File(settingFilePath);
        Path settingPath = Paths.get(settingFiles.getPath());
        if (!settingFiles.exists()) {
            settingFiles.createNewFile();
        }
        if (!scoreFile.exists()) {
            scoreFile.createNewFile();
        }

        BufferedReader br = Files.newBufferedReader(scorePath);
        BufferedReader brSet = Files.newBufferedReader(settingPath);

        if (br.readLine() != null) {
            score = Integer.parseInt(br.readLine());
        } else {
            score = 0;
        }
        if (brSet.readLine() != null) {
            settingNum = Integer.parseInt(brSet.readLine());
            if (Integer.parseInt(brSet.readLine()) < 5 && brSet.readLine() != null) {
                settingSign = Integer.parseInt(brSet.readLine());
            } else {
                settingSign = 3;
            }
        } else {
            score = 11;
            settingSign = 3;
        }

        //it will delete the file context every time, but only after it saves the score to the new program run,
        // it will then save it again when you're done.
        BufferedWriter bw = new BufferedWriter(new FileWriter(scorePath.toString(), false));
        BufferedWriter bwSet = new BufferedWriter(new FileWriter(settingPath.toString(), false));
        newQ(questionLabel);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveScore(bw, br, questionLabel, answerText, score, settingNum, bwSet, brSet, settingSign);
            }
        });
        seting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame settingJ = new JFrame("settings");
                JPanel setPan = new JPanel();
                JButton setBTN = new JButton("Submit");
                JTextField setTXT = new JTextField(10);

                setTXT.setBounds(10, 25, 100, 50);

                setBTN.setBounds(120, 25, 100, 50);

                settingJ.add(setTXT);
                settingJ.add(setBTN);
                settingJ.add(setPan);

                settingJ.setSize(250, 150);
                settingJ.setVisible(true);

                settingJ.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        seting.setVisible(false);
                    }
                });
                setBTN.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String[] settingsStr = setTXT.getText().split(",");
                            settingNum = Integer.parseInt(settingsStr[0]);
                            settingSign = Integer.parseInt(settingsStr[1]);
//                            System.out.println(settingsStr[0] + " " + settingsStr[1]);
                        } catch (NumberFormatException e1) {
                            setTXT.setText("only numbers");
                        }
                    }
                });
            }
        });

        answerText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println(settingNum);
                super.keyPressed(e);
                if (e.getKeyCode() == 10) {
                    try {
                        questionLabel.setText(checkANS(question, questionLabel, answerText, br, bw, settingNum, seting));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    questionLabel.setText(checkANS(question, questionLabel, answerText, br, bw, settingNum, seting));
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

    public static String saveScore(BufferedWriter bw, BufferedReader br, JLabel label, JTextField textField,
                                   int score, int settingNum, BufferedWriter bwSet, BufferedReader brSet, int settingSign) {
        String print = "";
        try {
            bw.newLine();
            bw.write(String.valueOf(score));
            bwSet.newLine();
            bwSet.write(String.valueOf(settingNum));
            bwSet.newLine();
            bwSet.write(String.valueOf(settingSign));
            label.setText("saved");
            print = "saved";
        } catch (IOException e) {
            label.setText("problem with saving file: " + e.getMessage());
        } finally {
            try {
                bw.flush();
                bwSet.flush();
                bw.close();
                bw.close();
                br.close();
                br.close();
            } catch (IOException e) {
                label.setText("problem with saving file: " + e.getMessage());
            }
        }
        return print;
    }

    int changeup = 0;

    public String newQ(JLabel label) {
        Random numRND = new Random();


        sortNum();
        String[] sign = new String[4];
        sign[0] = "+";
        sign[1] = "-";
        sign[2] = "*";
        sign[3] = "/";
        if (numbers1 < numbers2) { // 1 =1 2 =2
            changeup = numbers1; // c = 1
            numbers1 = numbers2; // 1 =  2
            numbers2 = changeup; // 2 = c(1)

        }
        if (signNum > 3) {
            signNum = numRND.nextInt(settingSign);
        }
        label.setText(" " + numbers1 + " " + sign[signNum] + " " + numbers2 + " ");
        return " " + numbers1 + " " + sign[signNum] + " " + numbers2 + " ";

    }

    public String checkANS(String label, JLabel jLabel, JTextField textField,
                           BufferedReader br, BufferedWriter bw, int settingNum, JButton setting) throws IOException {

        int ansAI;
        if (textField.getText().toLowerCase().equals("next")) {
            label = newQ(jLabel);
        } else if (textField.getText().toLowerCase().equals("score")) {
            label = " Your score is: " + score;
        } else if (textField.getText().toLowerCase().equals("settings")) {
            setting.setVisible(true);
        } else {
            if (signNum == 0) {
                ansAI = numbers1 + numbers2;
            } else if (signNum == 1) {
                ansAI = numbers1 - numbers2;
            } else if (signNum == 2) {
                ansAI = numbers1 * numbers2;
            } else {
                ansAI = numbers1 / numbers2;
            }
            try {
                if (Integer.parseInt(textField.getText()) == ansAI) {
                    score++;
                    label += " Very Well Done! ";
                } else {
                    label += " No, it's: " + ansAI;
                }
            } catch (Exception e) {
                //do nothing, it'll just re-ask a question.
            }
            label += newQ(jLabel);
        }
        return label;
    }

    public int[] rndNum() {
        Random numRND = new Random();
        int numbers2 = numRND.nextInt(settingNum);
        int numbers1 = numRND.nextInt(settingNum);
        int signNum = numRND.nextInt(settingSign);
        int[] ints = new int[3];
        if (numbers1 <= numbers2) {
            numbers1 = numRND.nextInt(settingNum);
        }
        ints[0] = numbers1;
        ints[1] = numbers2;
        ints[2] = signNum;


        return ints;
    }
}