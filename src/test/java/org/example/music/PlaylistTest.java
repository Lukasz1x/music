package org.example.music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest
{
    @Test
    public void isEmpty()
    {
        Playlist playlist=new Playlist();
        assertTrue(playlist.isEmpty());
    }

    @Test
    public void hasOne()
    {
        Playlist playlist =new Playlist();
        Song song = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        playlist.add(song);
        assertEquals(1, playlist.size());
    }

    @Test
    public void correctlyAdded()
    {
        Playlist playlist =new Playlist();
        Song song = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        playlist.add(song);
        assertEquals(song, playlist.getFirst());
    }

    @Test
    public void correctlyAdded2()
    {
        Playlist playlist =new Playlist();
        Song song = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        playlist.add(new Song("Heavy Young Heathens", "Being Evil Has a Price", 194));
        assertEquals(song, playlist.getFirst());
    }
}

