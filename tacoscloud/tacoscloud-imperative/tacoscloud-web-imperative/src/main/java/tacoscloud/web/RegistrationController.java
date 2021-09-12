package tacoscloud.web;

import lombok.extern.slf4j.Slf4j;
import tacoscloud.data.UserRepository;
import tacoscloud.domain.User;
import tacoscloud.vo.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/register")
@SuppressWarnings("unused")
public class RegistrationController
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(Model model)
    {
        return "registeration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm registrationForm)
    {
        User user = userRepository.save(registrationForm.toUser(passwordEncoder));
        log.info("User registred\n" + user);
        return "redirect:/login";
    }
}
