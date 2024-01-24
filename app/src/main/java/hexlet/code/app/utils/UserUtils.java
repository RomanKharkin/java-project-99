package hexlet.code.app.utils;




import hexlet.code.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import hexlet.code.app.model.User;

@Component
public class UserUtils {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private ArticleRepository postRepository;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }

//    public boolean isAuthor(long postId) {
//        var postAuthorEmail = postRepository.findById(postId).get().getAuthor().getEmail();
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        return postAuthorEmail.equals(authentication.getName());
//    }

    public User getTestUser() {
        return  userRepository.findByEmail("hexlet@example.com")
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
