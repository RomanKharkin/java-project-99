
package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    public void setUp() {

        testUser = Instancio.of(modelGenerator.getUserModel()).create();

    }

    @Test
    public void testIndex() throws Exception {
        userRepository.save(testUser);
        var result = mockMvc.perform(get("/api/users").with(user(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testShow() throws Exception {

        userRepository.save(testUser);

        var request = get("/api/users/{id}", testUser.getId())
                .with(user(testUser));
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                v -> v.node("firstName").isEqualTo(testUser.getFirstName()),
                v -> v.node("lastName").isEqualTo(testUser.getLastName()),
                v -> v.node("email").isEqualTo(testUser.getEmail())
        );
    }

    @Test
    public void testCreate() throws Exception {
        var testUserCreateDTO = Instancio.of(modelGenerator.getUserCreateDTOModel()).create();

        var request = post("/api/users").with(user(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testUserCreateDTO));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var user = userRepository.findByEmail(testUserCreateDTO.getEmail())
                .orElseThrow(() -> new AssertionError("User not found"));

        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo(testUserCreateDTO.getFirstName());
        assertThat(user.getLastName()).isEqualTo(testUserCreateDTO.getLastName());

    }

    @Test
    public void testCreateWithWrongEmail() throws Exception {
        var dto = userMapper.map(testUser);
        dto.setEmail("123gmail.ru");

        var request = post("/api/users").with(user(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate() throws Exception {
        userRepository.save(testUser);

        var userUpdateDto = Instancio.of(modelGenerator.getUserUpdateDTOModel()).create();

        var request = put("/api/users/{id}", testUser.getId()).with(user(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userUpdateDto));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var user = userRepository.findById(testUser.getId())
                .orElseThrow(() -> new AssertionError("User not found"));

        assertThat(user.getFirstName()).isEqualTo(userUpdateDto.getFirstName().get());
        assertThat(user.getLastName()).isEqualTo(userUpdateDto.getLastName().get());
        assertThat(user.getEmail()).isEqualTo(userUpdateDto.getEmail().get());
        assertThat(passwordEncoder.matches(userUpdateDto.getPassword().get(), user.getPasswordDigest())).isTrue();
    }

    @Test
    public void testPartialUpdate() throws Exception {
        userRepository.save(testUser);

        var userUpdateDto = Instancio.of(modelGenerator.getUserUpdateDTOModel()).create();
        userUpdateDto.setPassword(null);

        var request = put("/api/users/{id}", testUser.getId()).with(user(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userUpdateDto));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var user = userRepository.findById(testUser.getId())
                .orElseThrow(() -> new AssertionError("User not found"));

        assertThat(user.getFirstName()).isEqualTo(userUpdateDto.getFirstName().get());
        assertThat(user.getLastName()).isEqualTo(userUpdateDto.getLastName().get());
        assertThat(user.getEmail()).isEqualTo(userUpdateDto.getEmail().get());
    }

    @Test
    public void testDestroy() throws Exception {
        userRepository.save(testUser);
        var request = delete("/api/users/{id}", testUser.getId()).with(user(testUser));
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        assertThat(userRepository.existsById(testUser.getId())).isEqualTo(false);
    }
}
