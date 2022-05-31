package favoriteSongs.service;

import favoriteSongs.dto.UserDTO;
import favoriteSongs.entity.Users;
import favoriteSongs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidatorService {

    @Autowired
    private UserRepository userRepository;

    public boolean validateIfUserIsNew(UserDTO userDTO) {
        Users usersFetchedByEmail = userRepository.findUserByUsername(userDTO.getUsername());
        Users usersFetchedByFirstAndLastName = userRepository.findUserByFirstNameAndLastName(userDTO.getFirstName(), userDTO.getLastName());
        if(usersFetchedByEmail == null && usersFetchedByFirstAndLastName == null) {
            return true;
        } else {
            return false;
        }
    }

}
