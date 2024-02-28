package np.com.csangharsha.chatsapp.utils;

import lombok.RequiredArgsConstructor;
import np.com.csangharsha.chatsapp.config.securities.model.CustomUserDetails;
import np.com.csangharsha.chatsapp.entities.users.User;
import np.com.csangharsha.chatsapp.entities.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    public User getUserDetails(){
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findById(principal.getId());
    }
}
