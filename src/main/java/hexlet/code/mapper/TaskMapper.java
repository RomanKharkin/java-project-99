package hexlet.code.mapper;

import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    @Autowired
    private LabelRepository labelRepository;
    @Mapping(target = "assignee", expression = "java(mapAssignee(dto.getAssigneeId()))")
    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "labels", source = "taskLabelIds")
    public abstract Task map(TaskCreateDTO dto);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    public abstract Task update(TaskUpdateDTO taskDto, @MappingTarget Task task);

    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "title", source = "name")
    @Mapping(target = "content", source = "description")
    @Mapping(target = "status", source = "taskStatus.slug")
    public abstract TaskDTO map(Task model);

    Set<Long> map(Set<Label> value) {
        return value.stream().map(Label::getId).collect(Collectors.toSet());
    }

    Set<Label> mapTaskLabelIdsToLabels(Set<Long> taskLabelIds) {
        if (taskLabelIds == null) {
            return Collections.emptySet();
        }
        return taskLabelIds.stream()
                .map(labelRepository::findById)
                .map(optional -> optional.orElseThrow(() -> new RuntimeException("Label not found")))
                .collect(Collectors.toSet());
    }

    protected User mapAssignee(Long assigneeId) {
        if(assigneeId == null) {
            return null;
        } else {
            return new User(assigneeId);
        }
    }
}
