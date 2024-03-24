package hexlet.code.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "task_statuses")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TaskStatus implements BaseEntity {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String slug;

    @CreatedDate
    private LocalDate createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
