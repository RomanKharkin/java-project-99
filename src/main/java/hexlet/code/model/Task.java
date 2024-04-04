package hexlet.code.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Task implements BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private Integer index;

    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    private TaskStatus taskStatus;

    @ManyToOne
    private User assignee;

    //private long assigneeId;

    @NotBlank
    private String name;

    @ManyToMany
    private Set<Label> labels = new HashSet();

    @CreatedDate
    private LocalDate createdAt;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
