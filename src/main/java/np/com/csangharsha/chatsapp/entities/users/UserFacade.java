package np.com.csangharsha.chatsapp.entities.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public List<User> searchUsers(String query) {
        return userService.searchUsers(query);
    }
}
