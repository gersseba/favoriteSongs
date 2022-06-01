package favoriteSongs.controller;

import favoriteSongs.dto.SongDTO;
import favoriteSongs.entity.Song;
import favoriteSongs.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class SongController extends BaseController{

    @Autowired
    private SongService songService;

    @PostMapping("/addSong")
    public ResponseEntity<String> addSong(@Valid @RequestBody SongDTO songDTO) {
        Song song = songService.addSong(songDTO);
        return ResponseEntity.status(201).body("Song added with name "+song.getTitle());
    }

    @GetMapping("/viewAllSongs")
    public ResponseEntity<List<SongDTO>> viewAllSongs() {
        return ResponseEntity.status(200).body(songService.viewAllSongs());
    }

    @GetMapping("/viewSong/{title}")
    public ResponseEntity<SongDTO> viewSong(@PathVariable String title) {
        Song responseBodyAsSong = null;
        SongDTO songRecord = songService.viewSong(title);
        if(songRecord!=null) {
            return ResponseEntity.status(200).body(songRecord);
        } else {
            return ResponseEntity.status(404).body(songRecord);
        }
    }

}
