package hexlet.code.app.service;

import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomTaskStatusDetailsService {
    @Autowired
    private TaskStatusRepository repository;

    public void taskStatusFind(String slug) throws UsernameNotFoundException {
        var yourEntity = repository.findBySlug(slug)
                .orElseThrow(() -> new UsernameNotFoundException("TaskStatus not found"));
    }
}
