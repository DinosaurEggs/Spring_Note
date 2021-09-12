package tacoscloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorPageController
{
    @GetMapping("/access")
    public String unknowError()
    {
        return "AccessError";
    }
}
