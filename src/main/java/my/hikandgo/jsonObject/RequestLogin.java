package my.hikandgo.jsonObject;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestLogin {
    private String login;
    private String password;
}
