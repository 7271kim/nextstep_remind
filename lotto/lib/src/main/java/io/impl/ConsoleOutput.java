package io.impl;

import io.Output;

public class ConsoleOutput implements Output {

    @Override
    public void show(String text) {
        System.out.println(text);
    }

    @Override
    public void close() {}

}
