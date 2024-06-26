package hexlet.code.controller;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskParamsDTO;
import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.exception.ApiException.*;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.specification.TaskSpecification;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final TaskStatusRepository taskStatusRepository;

    private final TaskMapper taskMapper;

    private final TaskSpecification specBuilder;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<TaskDTO>> index(TaskParamsDTO params, @RequestParam(defaultValue = "1") int page) {
        var spec = specBuilder.build(params);
        var tasks = taskRepository.findAll(spec);

        var result = tasks.stream()
                .map(taskMapper::map)
                .toList();
        return ResponseEntity
                .ok()
                .header("X-Total-Count", String.valueOf(result.stream().count())).body(result);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    TaskDTO create(@Valid @RequestBody TaskCreateDTO taskData) {
        var taskStatus = taskStatusRepository.findBySlug(taskData.getStatus())
                .orElseThrow(() -> new BadRequestException("Task status not found"));
        Set<Long> labelIds = taskData.getTaskLabelIds();
        var task = taskMapper.map(taskData);

        task.setTaskStatus(taskStatus);

        taskRepository.save(task);
        var taskDTO = taskMapper.map(task);

        taskDTO.setTaskLabelIds(labelIds);

        return taskDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskDTO show(@PathVariable Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        var taskDTO = taskMapper.map(task);
        return taskDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskDTO update(@RequestBody @Valid TaskUpdateDTO taskData, @PathVariable Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        taskMapper.update(taskData, task);
        taskRepository.save(task);
        var taskDTO = taskMapper.map(task);
        return taskDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
