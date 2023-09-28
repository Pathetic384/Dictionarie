package com.example.demo1;
import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;

public class Dictionary {
    public TreeMap<String, Word> map = new TreeMap<String, Word>();

    public void LoadFile(String path) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(path));

        String en = br.readLine();
        en = en.replace("|", "");
        String vn = "";

        for (String line = br.readLine(); line != null; line = br.readLine()) {

            if ( line.startsWith("|") ) {
                Word word = new Word(en.trim(), vn.trim());
                map.put(en, word);

                en = "";
                vn = "";
                en += line;
                en = en.replace("|", "");
            }
            else {
                vn += line + "\n";
            }
        }
        Word word = new Word(en.trim(), vn.trim());
        map.put(en, word);
        br.close();
    }

    public String FindWord(String word) {
        for(String i : map.keySet()) {
            if(i.equals(word)) {
                Word ans = map.get(i);
                return ans.getMeaning();
            }
        }
        return null;
    }

    public void SaveFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src\\main\\resources\\dictest.txt"));
        for (String i : map.keySet()) {
            Word w = map.get(i);
            bw.write("|");
            bw.write(w.getWord());
            bw.write("\n");
            bw.write(w.getMeaning());
            bw.write("\n");
        }
        bw.close();
    }
}
