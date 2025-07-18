package com.example.user_management_service.service;

import com.example.user_management_service.aspect.Monitored;
import com.example.user_management_service.mapper.UserMapper;
import com.example.user_management_service.model.User;
import com.example.user_management_service.dto.UserDTO;
import com.example.user_management_service.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Monitored
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper mapper;
    private final JavaMailSender mailSender;
    private final Map<String, String> refreshTokenStorage = new ConcurrentHashMap<>();

    @Autowired
    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, UserMapper mapper, JavaMailSender mailSender) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mapper = mapper;
        this.mailSender = mailSender;
    }

    public String saveUser(UserDTO userDto){
        User user = mapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsActive(false);
        user.setIsEnabled(false);
        user.setIsDeleted(false);
        User registeredUser = repository.save(user);
        sendVerificationEmail(registeredUser);
        return "user added";
    }

    public User getUserByUsername(String username){
        return repository.getUserByUsername(username);
    }

    public String generateToken(String username){
        User user = getUserByUsername(username);
        return jwtService.generateToken(user.getId(), user.getUsername(), user.getUserRole());
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

    public void sendVerificationEmail(User registeredUser) {
        String subject = "Please verify your registration";
        String verificationUrl = "http://localhost:5173/verify/" + registeredUser.getId();

        String message = "Thank you for registering. Please click the link to verify your account:\n" + verificationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(registeredUser.getEmail());
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("synechronpraksa@gmail.com");

        mailSender.send(email);
    }

    public boolean verifyUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
         if(user != null){
             user.setIsEnabled(true);
             repository.save(user);
             return true;
         }
         return false;
    }

    public Map<String, String> generateTokens(String username) {
        User user = getUserByUsername(username);
        String accessToken = jwtService.generateToken(user.getId(), user.getUsername(), user.getUserRole());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getUsername(), user.getUserRole());
        jwtService.saveRefreshToken(username, refreshToken);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    public Map<String, String> refreshTokens(String refreshToken) {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String username = jwtService.getUsernameFromToken(refreshToken);
        return generateTokens(username);
    }
}
