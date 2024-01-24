//package hexlet.code.app;
//
//import hexlet.code.app.model.User;
//import hexlet.code.app.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class InitialDataLoader implements CommandLineRunner {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (userRepository.findByEmail("hexlet@example.com").isEmpty()) {
//            // Создаем нового пользователя
//            User adminUser = new User();
//            adminUser.setEmail("hexlet@example.com");
//            adminUser.setPasswordDigest("qwerty");
//            userRepository.save(adminUser);
//        }
//    }
//}
