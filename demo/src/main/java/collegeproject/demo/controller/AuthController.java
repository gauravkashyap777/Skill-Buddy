package collegeproject.demo.controller;

import collegeproject.demo.dto.LoginRequest;
import collegeproject.demo.dto.RegisterRequest;
import collegeproject.demo.security.UserPrincipal;
import collegeproject.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Not authenticated"));
        }

        Map<String, Object> info = new HashMap<>();
        info.put("id", principal.getId());
        info.put("username", principal.getUsername());
        info.put("email", principal.getEmail());
        info.put("role", principal.getRole());

        return ResponseEntity.ok(info);
    }
}