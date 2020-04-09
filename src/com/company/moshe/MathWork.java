package com.company.moshe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class MathWork {
    public String work() throws IOException {

        Scanner scanner = new Scanner(System.in);
        String ans = "";
        String qu = "";
        File file = new File("score.txt");
        Path path = Paths.get(file.getPath());
        if (!file.exists()) {
            file.createNewFile();
        }

        int score;
        BufferedReader br = Files.newBufferedReader(path);
        if (br.readLine() != null) {
            score = Integer.parseInt(br.readLine());
        } else {
            score = 0;
        }

        //it will delete the file context every time, but only after it saves the score to the new program run,
        // it will then save it again when you're done.
        BufferedWriter bw = new BufferedWriter(new FileWriter(path.toString(), false));
        try {
            while (!ans.toLowerCase().equals("q")) {
                Random numRND = new Random();
                int numbers1 = numRND.nextInt(11);
                int numbers2 = numRND.nextInt(11);
                int signNum = numRND.nextInt(3);
                String[] sign = new String[3];
                sign[0] = "+";
                sign[1] = "-";
                sign[2] = "*";
                if (numbers1 > numbers2) {
//                    System.out.print(numbers1 + " ");
//                    System.out.print(sign[signNum] + " ");
//                    System.out.print(numbers2 + " ");
                    qu = numbers1 + " " + sign[signNum] + " " + numbers2;
                    System.out.println("What does it equal? ");
                    ans = scanner.nextLine();
                    if (ans.toLowerCase().equals("q")) {
                        bw.newLine();
                        bw.write(String.valueOf(score));
                        break;
                    } else if (ans.toLowerCase().equals("score")) {
//                        System.out.println(score);
                        System.out.println("your score is: " + score);
                    } else {
                        if (signNum == 0) {
                            int ansAI = numbers1 + numbers2;
                            if (Integer.parseInt(ans) == ansAI) {
                                System.out.println("Very Well Done!");
                                score++;
                            } else {
                                System.out.println("No, it equals: " + ansAI);
                            }
                        } else if (signNum == 1) {
                            int ansAI = numbers1 - numbers2;
                            if (Integer.parseInt(ans) == ansAI) {
                                System.out.println("Very Well Done!");
                                score++;
                            } else {
                                System.out.println("No, it equals: " + ansAI);
                            }
                        } else {
                            int ansAI = numbers1 * numbers2;
                            if (Integer.parseInt(ans) == ansAI) {
                                score++;
                                System.out.println("Very Well Done!");
                            } else {
                                System.out.println("No, it equals: " + ansAI);
                            }
                        }
                    }
                } else {
                    numbers1 = numRND.nextInt(11);
                }
                return qu;
            }

        } catch (
                IOException e) {
            System.out.println("problem with saving file: " + e.getMessage());
            bw.write(String.valueOf(e.getStackTrace()));
        } finally {
//            bw.append(Character.highSurrogate(score));
            bw.flush();
            bw.close();
            br.close();
        }
     return qu;
    }
}
