package hexlet.code.app.service;

import hexlet.code.app.dto.UserCreateDto;
import hexlet.code.app.dto.UserDto;

public interface UserService {

    UserDto createUser(UserCreateDto newUser);
}
