package hexlet.code.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskUpdateDTO {
        @NotNull
        private JsonNullable<Long> assigneeId;

        @NotBlank
        private JsonNullable<String> content;

        private JsonNullable<TaskStatusDTO> status;

        @NotBlank
        private JsonNullable<String> title;
}
