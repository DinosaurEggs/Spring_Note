package tacoscloud.utils.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService
{
    void init();
    
    void save(MultipartFile files);
    
    Stream<Path> loadAll();
    
    Path load(String filename);
    
    Resource loadAsResource(String filename);
    
    void deleteAll();
}
