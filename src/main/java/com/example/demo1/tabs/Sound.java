package com.example.demo1.tabs;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

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

    @Override
    public void run() {
        File soundFile = new File(filename);
        while(true) {
            if (!soundFile.exists()) {
                System.err.println("Khong tim thay file");
                return;
            }


            AudioInputStream audioInputStream = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            } catch (UnsupportedAudioFileException e1) {
                e1.printStackTrace();
                return;
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }

            AudioFormat format = audioInputStream.getFormat();
            SourceDataLine auline = null;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            try {
                auline = (SourceDataLine) AudioSystem.getLine(info);
                auline.open(format);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            if (auline.isControlSupported(FloatControl.Type.PAN)) {
                FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);

                if (curPosition == Position.RIGHT) {
                    pan.setValue(1.0f);
                } else if (curPosition == Position.LEFT) {
                    pan.setValue(-1.0f);
                }
            }

            auline.start();

            int nBytesRead = 0;

            byte[] abData = new byte[size];

            try {
                while (nBytesRead != -1) {
                    nBytesRead = audioInputStream.read(abData, 0, abData.length);
                    if (nBytesRead >= 0) {
                        auline.write(abData, 0, nBytesRead);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } finally {
                auline.stop();
                auline.drain();
                auline.close();
            }
        }

    }
}
