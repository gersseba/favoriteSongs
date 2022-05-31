package favoriteSongs.service;

import favoriteSongs.dto.SongDTO;
import favoriteSongs.entity.Song;
import favoriteSongs.entity.Users;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService {

    private static Logger logger = LoggerFactory.getLogger(SongService.class);

    @Autowired
    private UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    public Song addSong(SongDTO songDTO) {
        Users user = fetchLoggedInUserDetails();
        Song song = mapSongFromSongDTO(songDTO);
        List<Song> songList = user.getSongs();
        songList.add(song);
        user.setSongs(songList);
        userService.registerUser(user);
        logger.info("Song with title "+song.getTitle()+" added to username "+user.getUsername()+" in database");
        return song;
    }

    public List<SongDTO> viewAllSongs() {
        Users user = fetchLoggedInUserDetails();
        logger.info("returning  "+user.getSongs().size()+" song stored under username "+user.getUsername()+" in database");
        List<Song> songs = user.getSongs();
        if(songs.size()>0) {
            return songs.stream().map(k -> mapSongDTOFromSong(k)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public SongDTO viewSong(String title) {
        Users user = fetchLoggedInUserDetails();
        logger.info("attempting to find song in database "+title+" for logged in username "+user.getUsername());
        Optional<Song> song = user.getSongs().stream().filter(k->k.getTitle().equalsIgnoreCase(title)).findAny();
        if(song.isPresent()) {
            return mapSongDTOFromSong(song.get());
        } else {
            return null;
        }
    }

    public Song mapSongFromSongDTO(SongDTO songDTO) {
        return modelMapper.map(songDTO, Song.class);
    }

    public SongDTO mapSongDTOFromSong(Song song) {
        return modelMapper.map(song, SongDTO.class);
    }

    private Users fetchLoggedInUserDetails() {
        return userService.getLoggedInUserDetails();
    }

}
