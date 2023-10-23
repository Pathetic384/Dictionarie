package com.example.demo1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;

public class Dictionary {
    private final TrieNode root = new TrieNode();

    public void LoadFile(String path) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(path));

        String en = br.readLine();
        en = en.replace("|", "");
        String vn = "";

        for (String line = br.readLine(); line != null; line = br.readLine()) {

            if ( line.startsWith("|") ) {
                insertWord(en.trim(),vn.trim());
                en = "";
                vn = "";
                en += line;
                en = en.replace("|", "");
            }
            else {
                vn += line + "\n";
            }
        }
        insertWord(en.trim(),vn.trim());
        br.close();
    }

    public void SaveFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/dictest.txt"));
        List<String> allWord;
        allWord = advanceSearch("");
        for (String i : allWord) {
            bw.write("|");
            bw.write(i);
            bw.write("\n");
            bw.write(FindMeaning(i));
            bw.write("\n");
        }
        bw.close();
    }

    public void insertWord(String word, String meaning) {

        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        current.endOfWord = true;
        current.meaning = meaning;
    }

    public String FindMeaning(String s) {
        TrieNode current = root;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            current = current.children.get(ch);
        }
        if(current == null) return "";
        return current.meaning;
    }

    public boolean Exist(String s) {
        TrieNode current = root;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return true;
    }

    public void DeleteWord(String key) {
        DeleteHelper(root, key, 0);
    }

    public TrieNode DeleteHelper(TrieNode node, String key, int i) {
        if(node == null) return null;
        if(i == key.length()) {
            node.endOfWord = false;
            if(node.children != null) {
                node = null;
            }
            return node;
        }
        TrieNode temp = node.children.get(key.charAt(i));
        temp = DeleteHelper(temp, key, i+1);
        if(node.children == null && !node.endOfWord) {
            node = null;
        }
        return node;
    }

    public ObservableList<String> advanceSearch(String prefix){
        ObservableList<String> autoCompWords = FXCollections.observableArrayList();

        TrieNode currentNode=root;
        if (prefix == null) prefix = "";
        for(int i=0;i<prefix.length();i++) {
            currentNode=currentNode.children.get(prefix.charAt(i));
            if(currentNode==null) return autoCompWords;
        }

        searchWords(currentNode,autoCompWords,prefix);
        return autoCompWords;
    }

    private void searchWords(TrieNode currentNode, List<String> autoCompWords, String word) {
        if(currentNode==null) return;

        if(currentNode.endOfWord) {
            autoCompWords.add(word);
        }
        Map<Character,TrieNode> map=currentNode.children;
        for(Character c:map.keySet()) {
            searchWords(map.get(c),autoCompWords, word+String.valueOf(c));
        }

    }
}
