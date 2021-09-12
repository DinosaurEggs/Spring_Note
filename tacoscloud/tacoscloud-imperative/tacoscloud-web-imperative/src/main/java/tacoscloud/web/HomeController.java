package tacoscloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@Slf4j
@Controller
@SessionAttributes("order")
public class HomeController
{
    @GetMapping("/")
    public String home(SessionStatus sessionStatus, Authentication authentication, Model model)
    {
        if (authentication != null && authentication.isAuthenticated()) {
            log.info("user\n" + authentication.getPrincipal());
            sessionStatus.setComplete();
            model.addAttribute("user", authentication.getPrincipal());
            return "home";
        } else {
            return "home_without_login";
        }
    }
}
