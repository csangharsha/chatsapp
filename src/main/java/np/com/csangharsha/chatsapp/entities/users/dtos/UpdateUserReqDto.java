package np.com.csangharsha.chatsapp.entities.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserReqDto {
    private String fullName;
    private String profilePicture;
}
