package hexlet.code.app.mapper;

import hexlet.code.app.dto.TaskUpdateDTO;
import hexlet.code.app.dto.TaskCreateDTO;
import hexlet.code.app.dto.TaskDTO;
import hexlet.code.app.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public  abstract class TaskMapper {
        @Mapping(target = "assignee.id", source = "assignee_id")
        @Mapping(target = "name", source = "title")
        @Mapping(target = "description", source = "content")
//        @Mapping(target = "status.slug", source = "status")
        public abstract Task map(TaskCreateDTO dto);
        @Mapping(target = "name", source = "title")
        @Mapping(target = "description", source = "content")
        public abstract Task update(TaskUpdateDTO taskDto, @MappingTarget Task task);
        @Mapping(target = "assignee_id", source = "assignee.id")
        @Mapping(target = "title", source = "name")
        @Mapping(target = "content", source = "description")
        public abstract TaskDTO map(Task model);
}
