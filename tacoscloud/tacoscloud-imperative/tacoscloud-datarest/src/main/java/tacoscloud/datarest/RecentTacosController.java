package tacoscloud.datarest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import tacoscloud.data.TacoRepository;
import tacoscloud.domain.Taco;
import tacoscloud.entitymodel.taco.TacoEntityModel;
import tacoscloud.entitymodel.taco.TacoEntityModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RepositoryRestController// 定义用户端点 采用Spring Data REST的基本路径进行其请求映射
public class RecentTacosController
{
    private final TacoRepository tacoRepository;

    @Autowired
    public RecentTacosController(TacoRepository tacoRepository)
    {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TacoEntityModel>> recentTacos()
    {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepository.findAll(page).getContent();

        CollectionModel<TacoEntityModel> recentResources = new TacoEntityModelAssembler().toCollectionModel(tacos);

        recentResources.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RecentTacosController.class).recentTacos())
                        .withRel("recents")
        );
        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }
}
