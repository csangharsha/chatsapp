package np.com.csangharsha.chatsapp.entities.users;

import lombok.RequiredArgsConstructor;
import np.com.csangharsha.chatsapp.utils.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserFacade userFacade;
    private final SecurityService securityService;

    @GetMapping("/me")
    public ResponseEntity<User> getUserProfile() {
        return new ResponseEntity<>(securityService.getUserDetails(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(@RequestParam(value = "query", required = true) String query) {
        return new ResponseEntity<>(userFacade.searchUsers(query), HttpStatus.OK);
    }
}
