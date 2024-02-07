package hexlet.code.app.dto;

import hexlet.code.app.model.TaskStatus.Slug;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskStatusUpdateDTO {
    private JsonNullable<String> name;

    private JsonNullable<Slug> slug;
}
