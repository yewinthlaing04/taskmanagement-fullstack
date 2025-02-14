package com.ye.task.service.auth;

import com.ye.task.dto.SignUpRequest;
import com.ye.task.dto.UserDto;
import com.ye.task.entity.User;
import com.ye.task.enums.UserRoles;
import com.ye.task.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void createAnAdminAccount(){

        // Check if admin account already exists
        // If not, create one with default credentials: admin@test.com and password: admin
        // Note: The password is hashed using BCryptPasswordEncoder for security purposes
       Optional<User> optionalUser =  userRepository.findByUserrole(UserRoles.ADMIN);

        if ( optionalUser.isEmpty()){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserrole(UserRoles.ADMIN);
            userRepository.save(user);
            System.out.println("Admin account create successfully");
        }else {
            System.out.println("Admin account already exists ");
        }
    }


    @Override
    public UserDto signUpUser(SignUpRequest signUpRequest) {

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        user.setUserrole(UserRoles.EMPLOYEE);
        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
