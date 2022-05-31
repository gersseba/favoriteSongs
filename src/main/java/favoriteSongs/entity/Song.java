package favoriteSongs.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import static javax.persistence.TemporalType.DATE;

@Data
@Entity
@Table(name = "Song")
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songID;

    private String title;

    private String genre;

    @Temporal(DATE)
    private Date releaseDate;

    private String artist;

    public Song(String title, String genre, Date releaseDate, String artist) {
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.artist = artist;
    }
}
