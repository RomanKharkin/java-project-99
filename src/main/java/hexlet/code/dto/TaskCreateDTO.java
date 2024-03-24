package hexlet.code.dto;


import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TaskCreateDTO {
    private Integer index;

//    @NotNull
    @SuppressWarnings("checkstyle:MemberName")
    private Long assignee_id;

    @NotBlank
    private String content;

    private String status;

    private Set<Long> taskLabelIds;

    @NotBlank
    private String title;
}
