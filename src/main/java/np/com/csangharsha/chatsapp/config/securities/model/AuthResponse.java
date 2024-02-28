package np.com.csangharsha.chatsapp.config.securities.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import np.com.csangharsha.chatsapp.entities.users.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private User user;
}
