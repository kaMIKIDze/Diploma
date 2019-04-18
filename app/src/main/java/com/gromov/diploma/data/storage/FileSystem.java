package com.gromov.diploma.data.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileSystem {
    public static StringBuilder readText(String fileName) {

        StringBuilder stringBuilder = null;

        File file = new File(fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            stringBuilder = new StringBuilder();
            String line;

            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }
}
