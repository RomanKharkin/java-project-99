
package hexlet.code.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.app.mapper.UserMapper;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
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

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = Instancio.of(modelGenerator.getUserModel()).create();

    }

    @Test
    public void testIndex() throws Exception {
        userRepository.save(testUser);
        var result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

//    @Test
//    public void testShow() throws Exception {
//
//        userRepository.save(testUser);
//
//        var request = get("/api/users/{id}", testUser.getId());
//        var result = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn();
//        var body = result.getResponse().getContentAsString();
//
//        assertThatJson(body).and(
//                v -> v.node("firstName").isEqualTo(testUser.getFirstName()),
//                v -> v.node("lastName").isEqualTo(testUser.getLastName()),
//                v -> v.node("email").isEqualTo(testUser.getEmail())
//        );
//    }

//    @Test
//    public void testCreate() throws Exception {
//        var dto = userMapper.map(testUser);
//
//        var request = post("/api/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto));
//
//        mockMvc.perform(request)
//                .andExpect(status().isCreated());
//
//        var user = userRepository.findByEmail(testUser.getEmail()).get();
//
//        assertThat(user).isNotNull();
//        assertThat(user.getFirstName()).isEqualTo(testUser.getFirstName());
//        assertThat(user.getLastName()).isEqualTo(testUser.getLastName());
//        assertThat(user.getPassword()).isEqualTo(testUser.getPassword());
//    }

//    @Test
//    public void testCreateWithWrongEmail() throws Exception {
//        var dto = userMapper.map(testUser);
//        dto.setEmail("123@gmail.ru");
//
//        var request = post("/api/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto));
//
//        mockMvc.perform(request)
//                .andExpect(status().isBadRequest());
//    }

//    @Test
//    public void testUpdate() throws Exception {
//        userRepository.save(testUser);
//
//        var dto = userMapper.map(testUser);
//
//        dto.setFirstName("Roman");
//        dto.setLastName("Kharkin");
//        dto.setEmail("123@gmail.de");
//        dto.setPassword("derParol");
//
//        var request = put("/api/users/{id}", testUser.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto));
//
//        mockMvc.perform(request)
//                .andExpect(status().isOk());
//
//        var task = userRepository.findById(testUser.getId()).get();
//
//        assertThat(task.getFirstName()).isEqualTo(dto.getFirstName());
//        assertThat(task.getLastName()).isEqualTo(dto.getLastName());
//        assertThat(task.getEmail()).isEqualTo(dto.getEmail());
//        assertThat(task.getPassword()).isEqualTo(dto.getPassword());
//    }


//    @Test
//    public void testDestroy() throws Exception {
//        userRepository.save(testUser);
//        var request = delete("/api/users/{id}", testUser.getId());
//        mockMvc.perform(request)
//                .andExpect(status().isOk());
//
//        assertThat(userRepository.existsById(testUser.getId())).isEqualTo(false);
//    }
}
