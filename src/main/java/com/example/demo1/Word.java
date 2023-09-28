package com.example.demo1;

public class Word {
    private String word;
    private String meaning;

    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }
    public String getMeaning() {
        return meaning;
    }

    public String getWord() {
        return word;
    }
}
