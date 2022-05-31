package favoriteSongs.controller;

import favoriteSongs.dto.SongDTO;
import favoriteSongs.entity.Song;
import favoriteSongs.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SongController {

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
