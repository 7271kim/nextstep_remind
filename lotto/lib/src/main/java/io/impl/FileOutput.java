package io.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.Output;

public class FileOutput implements Output {

    private BufferedWriter writer;

    public FileOutput(String path) {
        try {
            fileRemove(path);
            this.writer = new BufferedWriter(new FileWriter(path, StandardCharsets.UTF_8, true));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void fileRemove(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public void show(String text) {
        try {
            writer.write(text);
            writer.write("\r\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
