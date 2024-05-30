package org.example.music;

import org.example.site.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class Song {
    public String artist;
    public String title;
    public int duration;

    public Song(String artist, String title, int duration) {
        this.artist = artist;
        this.title = title;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return duration == song.duration && Objects.equals(artist, song.artist) && Objects.equals(title, song.title);
    }

    @Override
    public String toString() {
        return "Song{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, title, duration);
    }

    public static class Persistence {
        public static Optional<Song> read(int id, DatabaseConnection databaseConnection) throws SQLException {
            String sql = "SELECT artist, title, length FROM song WHERE id = ?";
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return Optional.of(new Song(
                        resultSet.getString("artist"),
                        resultSet.getString("title"),
                        resultSet.getInt("length")
                ));
            }
            return Optional.empty();
        }
    }
}
