package np.com.csangharsha.chatsapp.entities.users;

import lombok.RequiredArgsConstructor;
import np.com.csangharsha.chatsapp.exceptions.CustomBackendException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomBackendException(String.format("User with id %d not found.", id)));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomBackendException(String.format("User with email '%s' not found.", email)));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUsers(String query) {
        return userRepository.searchUsers(query);
    }
}
