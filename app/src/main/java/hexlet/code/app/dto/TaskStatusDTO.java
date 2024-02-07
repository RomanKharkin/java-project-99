package hexlet.code.app.dto;


import hexlet.code.app.model.TaskStatus.Slug;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskStatusDTO {

    private long id;

    private String name;

    private Slug slug;

    private LocalDate createdAt;
}
