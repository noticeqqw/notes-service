package noticeqqw.project.main.service;

import lombok.RequiredArgsConstructor;
import noticeqqw.project.main.dto.auth.JwtResponse;
import noticeqqw.project.main.dto.auth.LoginRequest;
import noticeqqw.project.main.dto.auth.RegisterRequest;
import noticeqqw.project.main.entity.User;
import noticeqqw.project.main.repository.UserRepository;
import noticeqqw.project.main.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public JwtResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already taken");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        return new JwtResponse(jwtUtils.generateToken(user.getUsername()));
    }

    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        return new JwtResponse(jwtUtils.generateToken(request.username()));
    }
}
