package tacoscloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tacoscloud.domain.User;
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
    public String userInfomation(Model model, Authentication authentication)
    {
        model.addAttribute("user", authentication.getPrincipal());
        return "user";
    }
    
    @ResponseBody
    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> readFile(@PathVariable("filename") String filename)
    {
        Resource resource = fileService.load(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + filename)
                .body(resource);
    }
    
    @PostMapping("/upload")
    public String upLoadFile(@RequestParam MultipartFile file,Authentication authentication)
    {
        fileService.save(file, ((User) authentication.getPrincipal()).getUsername());
        return "redirect:/user";
    }
}
