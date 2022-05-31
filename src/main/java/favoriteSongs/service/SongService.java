package favoriteSongs.service;

import favoriteSongs.dto.SongDTO;
import favoriteSongs.entity.Song;
import favoriteSongs.entity.Users;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private UserService userService;

    ModelMapper modelMapper = new ModelMapper();

    public Song addSong(SongDTO songDTO) {
        Users user = fetchLoggedInUserDetails();
        Song song = mapSongFromSongDTO(songDTO);
        List<Song> songList = user.getSongs();
        songList.add(song);
        user.setSongs(songList);
        userService.registerUser(user);
        return song;
    }

    public List<Song> viewAllSongs() {
        Users user = fetchLoggedInUserDetails();
        return user.getSongs();
    }

    public Optional<Song> viewSong(String title) {
        Users user = fetchLoggedInUserDetails();
        return user.getSongs().stream().filter(k->k.getTitle().equalsIgnoreCase(title)).findAny();
    }

    public Song mapSongFromSongDTO(SongDTO songDTO) {
        return modelMapper.map(songDTO, Song.class);
    }

    private Users fetchLoggedInUserDetails() {
        String loggedInUsername = userService.getLoggedInUserDetails().getUsername();
        return userService.findUserByUserName(loggedInUsername);
    }

}
