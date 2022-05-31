package favoriteSongs.repository;

import favoriteSongs.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    Users findUserByUsername(String username);
    Users findUserByFirstNameAndLastName(String firstName, String lastName);

}
