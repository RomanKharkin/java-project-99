package hexlet.code.controller;


import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.dto.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task_statuses")
@AllArgsConstructor
public class TaskStatusController {

    private final TaskStatusRepository taskStatusRepository;

    private final TaskStatusMapper taskStatusMapper;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<TaskStatusDTO>> index() {
        var taskStatus = taskStatusRepository.findAll();
        var result = taskStatus.stream()
                .map(taskStatusMapper::map)
                .toList();
        return ResponseEntity
                .ok()
                .header("X-Total-Count", String.valueOf(result.stream().count())).body(result);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    TaskStatusDTO create(@Valid @RequestBody TaskStatusCreateDTO taskStatusData) {
            var taskStatus = taskStatusMapper.map(taskStatusData);
            taskStatusRepository.save(taskStatus);
            var taskStatusDTO = taskStatusMapper.map(taskStatus);
            return taskStatusDTO;
    }
    public class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskStatusDTO show(@PathVariable Long id) {
        var taskStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        var taskStatusDTO = taskStatusMapper.map(taskStatus);
        return taskStatusDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskStatusDTO update(@RequestBody @Valid TaskStatusUpdateDTO taskStatusData, @PathVariable Long id) {
        var taskStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        taskStatusMapper.update(taskStatusData, taskStatus);
        taskStatusRepository.save(taskStatus);
        var taskStatusDTO = taskStatusMapper.map(taskStatus);
        return taskStatusDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        var task = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        taskStatusRepository.deleteById(id);
    }
}

