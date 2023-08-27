package my.hikandgo.jsonObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestCreate {
    private String login;
    private String password;
    private String firstName;

}
