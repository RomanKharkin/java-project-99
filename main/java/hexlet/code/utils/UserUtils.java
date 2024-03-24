package hexlet.code.utils;


import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean isSelf(long userId) {
        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        var userEmail = user.getEmail();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userEmail.equals(authentication.getName());
    }

    public User getTestUser() {
        return  userRepository.findByEmail("hexlet@example.com")
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
