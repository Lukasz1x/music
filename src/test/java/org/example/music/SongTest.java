package org.example.music;

import org.example.site.database.DatabaseConnection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SongTest
{
    static DatabaseConnection db;
    @BeforeAll
    static void openDatabase(){
        db = new DatabaseConnection();
        db.connect("songs.db");
    }
    @Test
    void readSingle() throws SQLException {
        Optional<Song> testSong = Song.Persistence.read(5, db);
        Song expectedSong = new Song("Queen","Bohemian Rhapsody",355);
        assertTrue(testSong.isPresent());
        assertEquals(expectedSong,testSong.get());
    }
    @Test
    void readSingleWrongID() throws SQLException {
        Optional<Song> testSong = Song.Persistence.read(53, db);
        //Song expectedSong = new Song("Queen","Bohemian Rhapsody",355);
        assertFalse(testSong.isPresent());
    }

    @ParameterizedTest
    @MethodSource("provideIndexesAndExpectedSongs")
    public void testReadParameterized(int index, Song expectedSong) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index, db);
        assertEquals(expectedSong, song.orElse(null));
    }

    static Stream<Arguments> provideIndexesAndExpectedSongs() {
        return Stream.of(
                arguments(3, new Song("Led Zeppelin", "Stairway to Heaven", 482)),
                arguments(47, new Song("The Doors", "Riders on the Storm", 434)),
                arguments(-1, null),
                arguments(100, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provideIndexesAndExpectedSongsCSV")
    public void testReadParameterizedCSV(int index, Song expectedSong) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index, db);
        assertEquals(expectedSong, song.orElse(null));
    }

    static Stream<Arguments> provideIndexesAndExpectedSongsCSV() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("songs.csv"));
            String line=bf.readLine();
            List<Arguments> argumentsList = new ArrayList<>();
            while ((line = bf.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");  //w pliku występują przecinki w tytułach więc trzeba se jakoś radzić :)
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].replaceAll("^\"|\"$", ""); // usuwanie cudzysłowiu na początku i końcu pola
                }
                Song song = new Song(parts[1], parts[2], Integer.parseInt(parts[3]));
                System.out.println(Integer.parseInt(parts[0]) + " "+ song);
                argumentsList.add(Arguments.of(Integer.parseInt(parts[0]), song));
            }
            bf.close();
            return argumentsList.stream();
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    @AfterAll
    static void closeDatabase(){
        db.disconnect();
    }
}
