package np.com.csangharsha.chatsapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GlobalExceptionResponse {
    private Instant time;
    private int status;
    private String error;
    private String exception;
    private String message;
}
