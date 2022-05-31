package favoriteSongs.service;

import favoriteSongs.dto.UserDTO;
import favoriteSongs.entity.Authorities;
import favoriteSongs.entity.Users;
import favoriteSongs.repository.AuthorityRepository;
import favoriteSongs.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    ModelMapper modelMapper = new ModelMapper();

    public Users registerUser(Users users) {
        authorityRepository.save(new Authorities(users.getUsername()));
        return userRepository.save(users);
    }

    public Users findUserByUserName(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    public Users getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByUsername(authentication.getName());
    }

    public Users mapUserFromUserDTO(UserDTO userDTO) {
        return modelMapper.map(userDTO, Users.class);
    }

    public UserDTO mapUserDTOFromUsers(Users users) {
        return modelMapper.map(users, UserDTO.class);
    }

    public void deleteUser(Users user){
        userRepository.delete(user);
    }

}
