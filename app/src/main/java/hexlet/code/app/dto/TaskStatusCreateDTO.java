package hexlet.code.app.dto;

import hexlet.code.app.model.TaskStatus.Slug;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusCreateDTO {
    @NotBlank
    private String name;

    @NotNull
    private Slug slug;
}
