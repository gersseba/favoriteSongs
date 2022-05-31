package favoriteSongs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Authorities")
@NoArgsConstructor
@AllArgsConstructor
public class Authorities {

    @Id
    private String username;

    @Value("Üser")
    private String authority;

    public Authorities(String username) {
        this.username = username;
    }
}
