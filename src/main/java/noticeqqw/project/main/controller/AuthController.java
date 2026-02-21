package noticeqqw.project.main.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import noticeqqw.project.main.dto.auth.JwtResponse;
import noticeqqw.project.main.dto.auth.LoginRequest;
import noticeqqw.project.main.dto.auth.RegisterRequest;
import noticeqqw.project.main.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
