package hexlet.code.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.lang.Nullable;

import java.util.Set;

@Getter
@Setter
public class TaskCreateDTO {
    private Integer index;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    private String content;

    private String status;

    private Set<Long> taskLabelIds;

    @NotBlank
    private String title;
}
