package tacoscloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tacoscloud.utils.file.FileService;

@Controller
@RequestMapping("/user")
public class UserInfoController
{
    private final FileService fileService;
    
    @Autowired
    public UserInfoController(FileService fileService)
    {
        this.fileService = fileService;
    }
    
    @GetMapping
    public String userInfomation(Model model)
    {
        model.addAttribute("file");
        return "user";
    }
    
    @PostMapping("/upload")
    public String upLoadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes)
    {
        fileService.save(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/user";
    }
}
