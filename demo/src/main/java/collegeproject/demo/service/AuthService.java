package collegeproject.demo.service;

import collegeproject.demo.dto.AuthResponse;
import collegeproject.demo.dto.LoginRequest;
import collegeproject.demo.dto.RegisterRequest;
import collegeproject.demo.entity.User;
import collegeproject.demo.repository.UserRepository;
import collegeproject.demo.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already registered");
        }
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail().toLowerCase())
                .password(encoder.encode(req.getPassword()))
                .role(req.getRole() == null ? "student" : req.getRole())
                .build();

        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        return new AuthResponse(token, user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    public AuthResponse login(LoginRequest req) {
        User user;
        if (req.getUsername() != null && !req.getUsername().isBlank()) {
            user = userRepo.findByUsername(req.getUsername())
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        } else if (req.getEmail() != null && !req.getEmail().isBlank()) {
            user = userRepo.findByEmail(req.getEmail().toLowerCase())
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        } else {
            throw new RuntimeException("Username or email required");
        }

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        return new AuthResponse(token, user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}