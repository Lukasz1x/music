package org.example;

import org.example.music.Song;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            BufferedReader bf = new BufferedReader(new FileReader("songs.csv"));
            String line=bf.readLine();
            while ((line = bf.readLine()) != null) {
                String[] parts = line.split(",\\S");
                Song song = new Song(parts[1], parts[2], Integer.parseInt(parts[3]));
                System.out.println(Integer.parseInt(parts[0]) + " "+ song);
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}