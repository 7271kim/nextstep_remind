package io.impl;

import java.util.Scanner;

import io.Input;

public class InputConsole implements Input {

    private Scanner scanner;

    public InputConsole() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String accept() {
        return scanner.nextLine();
    }

}
