package tacoscloud.data;

import tacoscloud.domain.Taco;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long>//CRUD接口 <持久化类，主键类型> spring自动实现接口
{

}
