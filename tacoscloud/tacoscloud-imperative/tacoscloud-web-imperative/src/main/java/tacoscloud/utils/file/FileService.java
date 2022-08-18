package tacoscloud.utils.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService
{
    void save(MultipartFile file, String filename);
    
    Resource load(String filename);
    
    boolean delete(String filename);
}
