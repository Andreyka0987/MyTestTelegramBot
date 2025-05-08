package org.example;

import java.io.*;

public class DB {

            public static void SetDb(int a){
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("smt.txt"));
                    bufferedWriter.write(a+"");
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public static int getDb(){
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader("smt.txt"));
                    try {
                      String a = bufferedReader.readLine();
                      return Integer.parseInt(a);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
}
