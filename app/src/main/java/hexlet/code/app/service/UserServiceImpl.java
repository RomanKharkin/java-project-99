package hexlet.code.app.service;

import hexlet.code.app.mapper.UserMapper;
import hexlet.code.app.dto.UserCreateDto;
import hexlet.code.app.dto.UserDto;
import hexlet.code.app.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;
    private UserMapper userMapper;

    @Override
    public UserDto createUser(UserCreateDto newUser) {
        return userMapper.map(usersRepository.save(userMapper.map(newUser)));
    }
}
