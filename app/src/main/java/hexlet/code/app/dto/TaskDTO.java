package hexlet.code.app.dto;

import hexlet.code.app.model.Label;
import hexlet.code.app.model.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {
    private long id;
    private Long assignee_id;
    private Integer index;
    private String content;
    private TaskStatus status;
    private String title;
    private Label label;
    private LocalDate createdAt;
}
