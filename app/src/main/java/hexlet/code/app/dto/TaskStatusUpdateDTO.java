package hexlet.code.app.dto;

import hexlet.code.app.model.TaskStatus.Slug;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskStatusUpdateDTO {
    @NotBlank
    private JsonNullable<String> name;

    @NotNull
    private JsonNullable<Slug> slug;
}
