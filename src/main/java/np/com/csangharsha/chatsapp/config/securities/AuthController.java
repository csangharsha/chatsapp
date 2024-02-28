package np.com.csangharsha.chatsapp.config.securities;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import np.com.csangharsha.chatsapp.config.securities.model.AuthRequest;
import np.com.csangharsha.chatsapp.config.securities.model.AuthResponse;
import np.com.csangharsha.chatsapp.config.securities.model.CustomUserDetails;
import np.com.csangharsha.chatsapp.entities.users.User;
import np.com.csangharsha.chatsapp.entities.users.UserService;
import np.com.csangharsha.chatsapp.exceptions.CustomBackendException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = "Bearer " + tokenProvider.generateToken(authentication);

        String username = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByEmail(username);
        return new ResponseEntity<>(new AuthResponse(token, user), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody User user) {
        try {
            userService.findByEmail(user.getEmail());
        } catch(CustomBackendException ex) {
            String plainPassword = user.getPassword();
            user.setPassword(encoder.encode(user.getPassword()));
            user = userService.save(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(new CustomUserDetails(user, user.getEmail()), plainPassword);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = "Bearer " + tokenProvider.generateToken(authentication);
            return new ResponseEntity<>(new AuthResponse(token, user), HttpStatus.ACCEPTED);
        }
        throw new CustomBackendException(String.format("User with email %s already exists."));
    }
}
