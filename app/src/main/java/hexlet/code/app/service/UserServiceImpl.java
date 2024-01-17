//package hexlet.code.app.service;

//import hexlet.code.app.mapper.UserMapper;
//import hexlet.code.app.dto.UserCreateDTO;
//import hexlet.code.app.dto.UserDTO;
//import hexlet.code.app.repository.UsersRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;

//@Service
//@AllArgsConstructor
//public class UserServiceImpl implements UserService {
//    private UsersRepository usersRepository;
//    private UserMapper userMapper;
//
//    @Override
//    public UserDTO createUser(UserCreateDTO newUser) {
//        return userMapper.map(usersRepository.save(userMapper.map(newUser)));
//    }
//}
