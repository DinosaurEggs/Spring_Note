package tacoscloud.utils.file;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class FileServiceImpl implements FileService
{
    @Override
    public void init()
    {
    
    }
    
    @Override
    public void save(MultipartFile files)
    {
    
    }
    
    @Override
    public Stream<Path> loadAll()
    {
        return null;
    }
    
    @Override
    public Path load(String filename)
    {
        return null;
    }
    
    @Override
    public Resource loadAsResource(String filename)
    {
        return null;
    }
    
    @Override
    public void deleteAll()
    {
    
    }
}
