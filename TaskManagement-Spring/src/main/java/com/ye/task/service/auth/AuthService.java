package com.ye.task.service.auth;

import com.ye.task.dto.SignUpRequest;
import com.ye.task.dto.UserDto;

public interface AuthService {

    UserDto signUpUser(SignUpRequest signUpRequest);

    boolean hasUserWithEmail(String email);
}
