package favoriteSongs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Pattern(regexp = "^[A-Za-z]\\w{2,29}$", message = "input should be between 2 and 29 letters")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]\\w{2,29}$", message = "input should be between 2 and 29 letters")
    private String lastName;

    @Email
    @JsonProperty("email")
    private String username;

    private String password;

    private List<SongDTO> songs = new ArrayList<>();

    public UserDTO(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}
