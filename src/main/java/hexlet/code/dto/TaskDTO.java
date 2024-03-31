package hexlet.code.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class TaskDTO {
    private long id;
    @JsonProperty("assignee_id")
    private Long assigneeId;
    private Integer index;
    private String content;
    private String status;
    private String title;
    private Set<Long> taskLabelIds;
    private LocalDate createdAt;
}
