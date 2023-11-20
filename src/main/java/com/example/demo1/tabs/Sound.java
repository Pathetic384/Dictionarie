package com.example.demo1.tabs;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.text.Position;

public class Sound extends Thread {
    private String filename;

    private Position curPosition;

    private final int size = 524288;

    enum Position {
        LEFT, RIGHT, NORMAL
    };

    public Sound(String wavFile) {
        this.filename = wavFile;
        curPosition = Position.NORMAL;
    }

    public Sound(String wavFile, Position p) {
        this.filename = wavFile;
        this.curPosition = p;
    }

    public void run() {
        while(true) {
            File soundFile = new File(filename);
            if(!soundFile.exists()) {
                System.err.println("Khong tim thay file");
                return;
            }
        }
    }
}
