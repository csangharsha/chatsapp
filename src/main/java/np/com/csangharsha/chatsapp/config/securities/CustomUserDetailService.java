package np.com.csangharsha.chatsapp.config.securities;

import np.com.csangharsha.chatsapp.config.securities.model.CustomUserDetails;
import np.com.csangharsha.chatsapp.entities.users.User;
import np.com.csangharsha.chatsapp.entities.users.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        return new CustomUserDetails(user, username);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userService.findById(id);
        return new CustomUserDetails(user, user.getEmail());
    }
}
