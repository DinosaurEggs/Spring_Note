package tacoscloudreactive.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tacosapi")
@SuppressWarnings("unused")
public class APIController
{
    @GetMapping
    public String allAPI(Model model)
    {
        return "api";
    }

    @PostMapping
    public String tacoById(@ModelAttribute("tacosid") String id)
    {
        return "redirect:/api/design/" + id;
    }
}
