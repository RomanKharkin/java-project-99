package hexlet.code.app.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class LabelUpdateDTO {

//    @NotBlank
//    @Column(unique = true)
//    @Size(min = 3, max = 1000)
    private JsonNullable<String> name;
    private JsonNullable<String> slug;
}
