package hexlet.code.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.app.mapper.TaskMapper;
import hexlet.code.app.model.Label;
import hexlet.code.app.model.Task;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.util.ModelGenerator;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskMapper mapper;

    @Autowired
    private ModelGenerator modelGenerator;

    private Task testTask;
    private User testUser;
    private TaskStatus testTaskStatus;
    private Set<Label> testLabels;
    private Label testLabel;

    Faker faker = new Faker();


    @BeforeEach
    public void setUp() {
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);

        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        taskStatusRepository.save(testTaskStatus);

        testLabel = Instancio.of(modelGenerator.getLabelModel()).create();
        labelRepository.save(testLabel);
        testLabels = new HashSet<>();
        testLabels.add(testLabel);

        testTask = Instancio.of(modelGenerator.getTaskModel()).create();
        testTask.setAssignee(testUser);
        testTask.setTaskStatus(testTaskStatus);
        testTask.setLabels(testLabels);
        taskRepository.save(testTask);


//        IntStream.range(1, 5).forEach(i -> {
//            var label = new Label();
//            label.setName(faker.name().title());
//            labelRepository.save(label);
//        });
        List<Label> listLabels = labelRepository.findAll();

        List<TaskStatus> taskStatuses = taskStatusRepository.findAll();

        IntStream.range(1, 20).forEach(i -> {
            var randomStatusIndex = faker.number().numberBetween(0, taskStatuses.size());

            Set<Label> labels = new HashSet<>();
            var randomLabelIndex1 = faker.number().numberBetween(0, listLabels.size());
            var randomLabelIndex2 = faker.number().numberBetween(0, listLabels.size());
            labels.add(listLabels.get(randomLabelIndex1));
            labels.add(listLabels.get(randomLabelIndex2));

            var task = new Task();
            task.setName(faker.book().title());
            var description = faker.text().text();
            task.setDescription(description);
            task.setTaskStatus(taskStatuses.get(randomStatusIndex));
            task.setLabels(labels);
            task.setAssignee(testUser);
            taskRepository.save(task);
        });
    }


    @Test
    public void testIndex() throws Exception {
        taskRepository.save(testTask);
        var result = mockMvc.perform(get("/api/tasks").with(user(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testIndexWithStatus() throws Exception {
        var result = mockMvc.perform(get("/api/tasks?Status=draft").with(user(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray().allSatisfy(element ->
                assertThatJson(element)
                        .and(v -> v.node("status").asString().containsIgnoringCase("draft"))
        );
    }

    @Test
    public void testIndexWithLabelId() throws Exception {
        var result = mockMvc.perform(get("/api/tasks?LabelId=1").with(user(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray().allSatisfy(element ->
                assertThatJson(element)
                        .and(v -> v.node("taskLabelIds").isArray().contains(1))
        );
    }

    @Test
    public void testIndexWithAssigneeId() throws Exception {
        var result = mockMvc.perform(get("/api/tasks?AssigneeId=2").with(user(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray().allSatisfy(element ->
                assertThatJson(element)
                        .and(v -> v.node("assignee_id").isEqualTo(2))
        );
    }

    @Test
    public void testIndexWithTitleCont() throws Exception {
        var task = taskRepository.findById(1L).get();
        task.setName("TestTitle");
        taskRepository.save(task);

        var result = mockMvc.perform(get("/api/tasks?TitleCont=TestTitle").with(user(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray().allSatisfy(element ->
                assertThatJson(element)
                        .and(v -> v.node("title").asString().containsIgnoringCase("TestTitle"))
        );
    }


    @Test
    public void testShow() throws Exception {

        taskRepository.save(testTask);

        var request = get("/api/tasks/{id}", testTask.getId()).with(user(testUser));
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                v -> v.node("title").isEqualTo(testTask.getName()),
                v -> v.node("content").isEqualTo(testTask.getDescription())
        );
    }

    @Test
    public void testCreate() throws Exception {
        var dto = mapper.map(testTask);
        var request = post("/api/tasks").with(user(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        // Получить все задачи из репозитория
        Iterable<Task> tasks = taskRepository.findAll();
        // Проверить, что среди всех задач есть созданная задача
        boolean isTestTaskCreated = StreamSupport.stream(tasks.spliterator(), false)
                .anyMatch(task -> task.getName().equals(testTask.getName()));
        assertThat(isTestTaskCreated).isTrue();

//        var task = taskRepository.findByName(testTask.getName()).get();
//
//        assertThat(task).isNotNull();
//        assertThat(task.getName()).isEqualTo(testTask.getName());
//        assertThat(task.getDescription()).isEqualTo(testTask.getDescription());
    }

    @Test
    public void testCreateWithWrongStatus() throws Exception {
        var dto = mapper.map(testTask);
        dto.setTitle("");

        var request = post("/api/tasks").with(user(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate() throws Exception {
        taskRepository.save(testTask);

        var dto = mapper.map(testTask);

        var request = put("/api/tasks/{id}", testTask.getId()).with(user(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var task = taskRepository.findById(testTask.getId()).get();

        assertThat(task.getName()).isEqualTo(dto.getTitle());
        assertThat(task.getDescription()).isEqualTo(dto.getContent());
    }

    @Test
    public void testDestroy() throws Exception {
        taskRepository.save(testTask);
        var request = delete("/api/tasks/{id}", testTask.getId()).with(user(testUser));
        mockMvc.perform(request)
                .andExpect(status().isOk());

        assertThat(taskRepository.existsById(testTask.getId())).isEqualTo(false);
    }
}
