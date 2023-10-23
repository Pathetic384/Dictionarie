package com.example.demo1;

import java.util.TreeMap;

class TrieNode {
    TreeMap<Character, TrieNode> children;
    boolean endOfWord;
    String meaning;

    TrieNode() {
        children = new TreeMap<Character, TrieNode>();
        endOfWord = false;
        meaning = "";
    }
}
