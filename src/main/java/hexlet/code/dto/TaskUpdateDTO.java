package hexlet.code.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskUpdateDTO {

        @JsonProperty("assignee_id")
        private JsonNullable<Long> assigneeId;

        private JsonNullable<String> content;

        private JsonNullable<String> slug;

        private JsonNullable<LabelDTO> label;

        @NotBlank
        private JsonNullable<String> title;
}
