package favoriteSongs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {

    @Pattern(regexp = "^\\b(?!.*?\\s{2})[A-Za-z ]{2,50}\\b$", message = "input should be between 2 and 50 letters with valid spacing")
    private String title;

    @Pattern(regexp = "^\\b(?!.*?\\s{2})[A-Za-z ]{2,50}\\b$", message = "input should be between 2 and 50 letters with valid spacing")
    private String genre;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date releaseDate;

    @Pattern(regexp = "^\\b(?!.*?\\s{2})[A-Za-z ]{2,50}\\b$", message = "input should be between 2 and 50 letters with valid spacing")
    private String artist;
}
