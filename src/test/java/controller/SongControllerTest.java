package controller;

import favoriteSongs.controller.SongController;
import favoriteSongs.dto.SongDTO;
import favoriteSongs.entity.Song;
import favoriteSongs.service.SongService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SongControllerTest {

    @InjectMocks
    private SongController songController;

    @Mock
    private SongService songService;

    @Test
    public void testAddSongReturnsTitleOfSongAndCreatedHttpStatusCode() {
        Song song = new Song("testTitle","testGenre", new Date(),"testArtist");
        SongDTO songDTO = new SongDTO("testTitle","testGenre", new Date(),"testArtist");
        when(songService.addSong(songDTO)).thenReturn(song);
        ResponseEntity<String> responseEntity = songController.addSong(songDTO);
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
        Assert.assertEquals("Song added with name testTitle", responseEntity.getBody());
    }

    @Test
    public void testViewAllSongsReturnsOKStatusWithAllSongs() {
        SongDTO song1 = new SongDTO("testTitle1","testGenre1", new Date(),"testArtist1");
        SongDTO song2 = new SongDTO("testTitle2","testGenre2", new Date(),"testArtist2");
        List<SongDTO> songList = new ArrayList<>();
        songList.add(song1);
        songList.add(song2);
        when(songService.viewAllSongs()).thenReturn(songList);
        ResponseEntity<List<SongDTO>> responseEntity = songController.viewAllSongs();
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    public void testViewSongShouldReturnNotFoundStatusCodeAndNullResponse() {
        when(songService.viewSong("randomTitle")).thenReturn(null);
        ResponseEntity<SongDTO> responseEntity = songController.viewSong("randomTitle");
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
        Assert.assertNull(responseEntity.getBody());
    }

    @Test
    public void testViewSongShouldReturnOKStatusCodeAndSongResponse() {
        SongDTO songDTO = new SongDTO("testTitle","testGenre", new Date(),"testArtist");
        when(songService.viewSong("testTitle")).thenReturn(songDTO);
        ResponseEntity<SongDTO> responseEntity = songController.viewSong("testTitle");
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals("testTitle", responseEntity.getBody().getTitle());
    }

}
