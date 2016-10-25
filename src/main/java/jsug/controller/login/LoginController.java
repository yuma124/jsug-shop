package jsug.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yuma on 2016/10/19.
 */
@Controller
public class LoginController {

    @RequestMapping("loginForm")
    String loginForm() {
        return "login/loginForm";
    }

}
