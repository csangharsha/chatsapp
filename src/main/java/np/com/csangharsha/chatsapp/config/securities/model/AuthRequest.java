package np.com.csangharsha.chatsapp.config.securities.model;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AuthRequest {

    @Email
    private String email;
    private String password;

}
