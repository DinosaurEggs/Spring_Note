package tacoscloud.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tacoscloud.domain.Taco;

import java.util.List;
import java.util.Optional;

public interface TacoRepository
{
    List<Taco> findAll();
    Page<Taco> findAll(Pageable pageable);

    Optional<Taco> findById(Long id);

    Taco save(Taco taco);

    void deleteById(Long id);
}
