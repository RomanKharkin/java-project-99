package hexlet.code.mapper;

import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    @Mapping(target = "assignee.id", source = "assignee_id")
    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "labels", source = "taskLabelIds")
//        @Mapping(target = "taskStatus", source = "slug")
    public abstract Task map(TaskCreateDTO dto);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    public abstract Task update(TaskUpdateDTO taskDto, @MappingTarget Task task);

    @Mapping(target = "assignee_id", source = "assignee.id")
    @Mapping(target = "title", source = "name")
    @Mapping(target = "content", source = "description")
    @Mapping(target = "status", source = "taskStatus.slug")
    @Mapping(target = "taskLabelIds", source = "labels")
    public abstract TaskDTO map(Task model);

    Set<Long> map(Set<Label> value) {
        return value.stream().map(Label::getId).collect(Collectors.toSet());
    }
}
