package hexlet.code.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {
    private long id;
    private Long assigneeId;
    private Integer index;
    private String content;
    private TaskStatusDTO status;
    private String title;
    private LocalDate createdAt;
}
