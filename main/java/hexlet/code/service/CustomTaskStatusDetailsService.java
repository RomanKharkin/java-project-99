package hexlet.code.service;

import hexlet.code.repository.TaskStatusRepository;
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
