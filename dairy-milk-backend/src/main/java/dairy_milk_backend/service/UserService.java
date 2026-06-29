package dairy_milk_backend.service;

import dairy_milk_backend.dto.UserDto;
import dairy_milk_backend.entity.User;
import dairy_milk_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Naya user banao
    public UserDto createUser(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username required");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password required");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("OPERATOR");   // default role
        }

        User saved = userRepository.save(user);
        return toDto(saved);
    }

    // Saare users (password ke bina)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found: " + id));
        return toDto(user);
    }

    // User update (password optional — khaali ho to purana rahega)
    public UserDto updateUser(Long id, User updated) {
        return userRepository.findById(id)
            .map(existing -> {
                existing.setName(updated.getName());
                existing.setRole(updated.getRole());
                existing.setActive(updated.isActive());

                // Password tabhi badlo jab naya diya gaya ho
                if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
                    existing.setPassword(updated.getPassword());
                }

                return toDto(userRepository.save(existing));
            })
            .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Entity ko safe DTO me badlo (password hata ke)
    private UserDto toDto(User user) {
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getRole(),
            user.isActive()
        );
    }
}