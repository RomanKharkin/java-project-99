package hexlet.code.app.dto;

import java.util.Set;
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
    private String status;
    private String title;
    private Set<Long> taskLabelIds;
    private LocalDate createdAt;
}
