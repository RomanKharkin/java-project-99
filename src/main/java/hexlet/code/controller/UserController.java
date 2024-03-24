package hexlet.code.controller;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Data
public class UserController {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<UserDTO>> index() {
        List<User> users = userRepository.findAll();
        var result = users.stream()
                .map(userMapper::map)
                .toList();
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(result.stream()
                .count())).body(result);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO newUser) {
        var user = userMapper.map(newUser);
        userRepository.save(user);
        var userDto = userMapper.map(user);
        return userDto;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO show(@PathVariable Long id) {
        var product = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        var productDTO = userMapper.map(product);
        return productDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO update(@RequestBody @Valid UserUpdateDTO userData, @PathVariable Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        userMapper.update(userData, user);
        userRepository.save(user);
        var userDTO = userMapper.map(user);
        return userDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@userUtils.isSelf(#id)")
    void delete(@PathVariable Long id) {
        var task = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        userRepository.deleteById(id);
    }
}
