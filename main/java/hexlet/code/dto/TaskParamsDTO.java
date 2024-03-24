package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TaskParamsDTO {
    private Long assigneeId;
    private String status;
    private String titleCont;
    private Set<Long> labelId;
}
