package np.com.csangharsha.chatsapp.entities.users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findById(Long id);
    User findByEmail(String email);
    User save(User user);

    List<User> searchUsers(String query);
}
