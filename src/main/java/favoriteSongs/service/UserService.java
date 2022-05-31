package favoriteSongs.service;

import favoriteSongs.dto.UserDTO;
import favoriteSongs.entity.Authorities;
import favoriteSongs.entity.Users;
import favoriteSongs.repository.AuthorityRepository;
import favoriteSongs.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public Users registerUser(Users users) {
        authorityRepository.save(new Authorities(users.getUsername()));
        logger.info("User with username "+users.getUsername()+" saved to database");
        return userRepository.save(users);
    }

    public Users getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findUserByUsername(authentication.getName());
        logger.info("Current user is logged in with username "+user.getUsername());
        return user;
    }

    public Users mapUserFromUserDTO(UserDTO userDTO) {
        return modelMapper.map(userDTO, Users.class);
    }

    public UserDTO mapUserDTOFromUsers(Users users) {
        return modelMapper.map(users, UserDTO.class);
    }

    public void deleteUser(Users user){
        logger.info("deleting user with username "+user.getUsername());
        userRepository.delete(user);
    }

}
