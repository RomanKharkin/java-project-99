package hexlet.code.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateDTO {
    private Integer index;

    @NotNull
    private Long assigneeId;

    @NotBlank
    private String content;

    private TaskStatusDTO status;

    @NotBlank
    private String title;
}
