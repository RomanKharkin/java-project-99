package hexlet.code.app.component;


import hexlet.code.app.dto.LabelCreateDTO;
import hexlet.code.app.dto.TaskStatusCreateDTO;
import hexlet.code.app.dto.UserCreateDTO;
import hexlet.code.app.mapper.LabelMapper;
import hexlet.code.app.mapper.TaskStatusMapper;
import hexlet.code.app.mapper.UserMapper;
import hexlet.code.app.model.Label;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.model.Task;
import hexlet.code.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {


    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TaskStatusRepository taskStatusRepository;
    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final LabelRepository labelRepository;

    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final TaskStatusMapper taskStatusMapper;
    @Autowired
    private final LabelMapper labelMapper;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        var userData = new UserCreateDTO();
        userData.setEmail("hexlet@example.com");
        userData.setPassword("qwerty");
        var user = userMapper.map(userData);
        userRepository.save(user);

        var taskStatusCreateDTO = new TaskStatusCreateDTO();
        taskStatusCreateDTO.setName("Draft");
        taskStatusCreateDTO.setSlug("draft");
        var taskStatus1 = taskStatusMapper.map(taskStatusCreateDTO);
        taskStatusRepository.save(taskStatus1);

        taskStatusCreateDTO.setName("ToReview");
        taskStatusCreateDTO.setSlug("to_review");
        var taskStatus2 = taskStatusMapper.map(taskStatusCreateDTO);
        taskStatusRepository.save(taskStatus2);

        taskStatusCreateDTO.setName("ToBeFixed");
        taskStatusCreateDTO.setSlug("to_be_fixed");
        var taskStatus3 = taskStatusMapper.map(taskStatusCreateDTO);
        taskStatusRepository.save(taskStatus3);

        taskStatusCreateDTO.setName("ToPublish");
        taskStatusCreateDTO.setSlug("to_publish");
        var taskStatus4 = taskStatusMapper.map(taskStatusCreateDTO);
        taskStatusRepository.save(taskStatus4);

        taskStatusCreateDTO.setName("Published");
        taskStatusCreateDTO.setSlug("published");
        var taskStatus5 = taskStatusMapper.map(taskStatusCreateDTO);
        taskStatusRepository.save(taskStatus5);

        var labelCreateDTO = new LabelCreateDTO();
        labelCreateDTO.setName("feature");
        var label1 = labelMapper.map(labelCreateDTO);
        labelRepository.save(label1);

        labelCreateDTO.setName("bug");
        var label2 = labelMapper.map(labelCreateDTO);
        labelRepository.save(label2);


        var faker = new Faker();

        IntStream.range(1, 10).forEach(i -> {
            var label = new Label();
            label.setName(faker.name().title());
            labelRepository.save(label);
        });
        List<Label> listLabels = labelRepository.findAll();

        List<TaskStatus> taskStatuses = taskStatusRepository.findAll();
        IntStream.range(1, 30).forEach(i -> {
            var randomStatusIndex = faker.number().numberBetween(1, taskStatuses.size());

            Set<Label> labels = new HashSet<>();
            var randomLabelIndex1 = faker.number().numberBetween(1, listLabels.size());
            var randomLabelIndex2 = faker.number().numberBetween(1, listLabels.size());
            labels.add(listLabels.get(randomLabelIndex1));
            labels.add(listLabels.get(randomLabelIndex2));

            var task = new Task();
            task.setName(faker.book().title());
            var description = faker.text().text();
            task.setDescription(description);
            task.setTaskStatus(taskStatuses.get(randomStatusIndex));
            task.setLabels(labels);
            task.setAssignee(user);
            taskRepository.save(task);
        });
    }
}
