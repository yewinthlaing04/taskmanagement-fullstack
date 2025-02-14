package com.ye.task.controller;


import com.ye.task.dto.AuthenticationRequest;
import com.ye.task.dto.AuthenticationResponse;
import com.ye.task.dto.SignUpRequest;
import com.ye.task.dto.UserDto;
import com.ye.task.entity.User;
import com.ye.task.repo.UserRepository;
import com.ye.task.service.auth.AuthService;
import com.ye.task.service.jwt.UserService;
import com.ye.task.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser (@RequestBody SignUpRequest signUpRequest ){
        if ( authService.hasUserWithEmail(signUpRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with this email");
        }else {
            UserDto createUserDto = authService.signUpUser(signUpRequest);
            if ( createUserDto == null ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not created successfully");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createUserDto);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));
        }catch(BadCredentialsException e ) {
            throw new BadCredentialsException(("Incorrect username or password"));
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getEmail());

        Optional<User> optionalUser = userRepository.findFirstByEmail(request.getEmail());

        Map<String , Object> claims = new HashMap<>();
        claims.put("userRole" , optionalUser.get().getUserrole().name());
        final String jwtToken = jwtUtil.generateToken(claims , userDetails);

        AuthenticationResponse response = new AuthenticationResponse();
        if ( optionalUser.isPresent()){
            response.setJwt(jwtToken);
            response.setUserId(optionalUser.get().getId());
            response.setUserRoles(optionalUser.get().getUserrole());
        }

        return response ;
    }
}
