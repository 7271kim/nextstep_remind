package io.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.Input;

public class FileInput implements Input {

    private BufferedReader reader;

    public FileInput(String path) {
        try {
            this.reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String accept() {
        try {
            return reader.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
