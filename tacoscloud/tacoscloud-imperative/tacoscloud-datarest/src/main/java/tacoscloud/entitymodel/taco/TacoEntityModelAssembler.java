package tacoscloud.entitymodel.taco;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tacoscloud.domain.Taco;

//将 EntityModel 转换为 CollectionModel
public class TacoEntityModelAssembler extends RepresentationModelAssemblerSupport<Taco, TacoEntityModel>
{
    public TacoEntityModelAssembler()
    {
        //使用 ControllerClass 来确定它创建的链接中的任何 url 的基本路径
        super(TacoPath.class, TacoEntityModel.class);
    }

    @Override
    protected TacoEntityModel instantiateModel(Taco entity)//仅用于实例化资源对象
    {
        return new TacoEntityModel(entity);
    }

    @Override
    public TacoEntityModel toModel(Taco entity)//不仅用于创建资源对象，还用于用链接填充它。 将调用 instantiateModel()
    {
        return createModelWithId(entity.getId(), entity);
    }
}

@Controller
@RequestMapping("/api/rest/tacos")
class TacoPath
{
}