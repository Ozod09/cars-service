package uz.taxi.user_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.taxi.user_service.enums.RoleName;
import uz.taxi.user_service.enums.UserStatusEnum;
import uz.taxi.user_service.repository.UserRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) {

        if (!ddl.equals("create"))
            return;

        User admin = new User();
        admin.setId(UUID.fromString("7c4401ac-6272-49c7-bb05-2679bd7e165f"));
        admin.setFirstName("Administrator");
        admin.setLastName("Administrator");
        admin.setPhone("+998123456789");
        admin.setStatus(UserStatusEnum.ACTIVE);
        admin.setRoleName(RoleName.ROLE_SUPER_ADMIN);
        admin.setPassword(passwordEncoder.encode("admin123"));

        userRepository.save(admin);
    }
}
