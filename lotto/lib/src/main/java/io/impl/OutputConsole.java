package io.impl;

import io.Output;

public class OutputConsole implements Output {

    @Override
    public void show(String text) {
        System.out.println(text);
    }

}
