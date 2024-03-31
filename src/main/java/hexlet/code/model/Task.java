package hexlet.code.model;


import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;
import java.util.Collection;
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
    @Nullable
    private User assignee;

    @NotBlank
    private String name;

    @ManyToMany
    private Set<Label> labels = new HashSet();

    @CreatedDate
    private LocalDate createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
