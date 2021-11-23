package io.impl;

import java.util.Scanner;

import io.Input;

public class ConsoleInput implements Input {

    private Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String accept() {
        return scanner.nextLine();
    }

    @Override
    public void close() {}

}
