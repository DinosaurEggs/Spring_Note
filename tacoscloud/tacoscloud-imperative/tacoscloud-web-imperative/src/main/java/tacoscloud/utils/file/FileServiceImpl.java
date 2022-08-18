package tacoscloud.utils.file;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService
{
    private final String FILE_DIR = "D:/Cache/";
    private final Path path = Paths.get(FILE_DIR);
    private final Path defaultPath = Paths.get("D:/a.png");
    
    @Override
    public void save(MultipartFile file, String filename)
    {
        try
        {
            if (!delete(filename))
            {
                throw new IOException("Can not delete file: " + filename);
            }
            Files.copy(file.getInputStream(), path.resolve(filename));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Resource load(String filename)
    {
        Path file = path.resolve(filename);
        Resource resource;
        try
        {
            resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable())
            {
                resource = new UrlResource(defaultPath.toUri());
            }
            
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
        return resource;
    }
    
    @Override
    public boolean delete(String filename)
    {
        return FileSystemUtils.deleteRecursively(path.resolve(filename).toFile());
    }
}
