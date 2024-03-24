package hexlet.code.dto;


import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskUpdateDTO {
//        @NotNull
        private JsonNullable<Long> assignee_id;

        @NotBlank
        private JsonNullable<String> content;

        @JoinColumn(unique = true)
        private JsonNullable<String> slug;

        private JsonNullable<LabelDTO> label;

        @NotBlank
        private JsonNullable<String> title;
}
