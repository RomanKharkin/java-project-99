package hexlet.code.service;

import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@AllArgsConstructor
public class CustomTaskStatusDetailsService {

    private final TaskStatusRepository repository;

    public void taskStatusFind(String slug) throws UsernameNotFoundException {
        var yourEntity = repository.findBySlug(slug)
                .orElseThrow(() -> new UsernameNotFoundException("TaskStatus not found"));
    }
}
