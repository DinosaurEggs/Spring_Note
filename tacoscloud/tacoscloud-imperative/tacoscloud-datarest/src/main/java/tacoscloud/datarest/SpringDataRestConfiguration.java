package tacoscloud.datarest;

import tacoscloud.domain.Taco;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;

@Configuration
// Spring Data REST 为 Spring Data 创建的存储库自动创建REST API
// 生成地址： xx.xx.xx/配置文件中基本路径/持久层类名+s
//          xx.xx.xx/配置文件中基本路径/用户端点定义路径名
// 查询API：xx.xx.xx/配置文件中基本路径 包含所有公开API的链接
// 分页：localhost:8080/api/tacos?size=5
// 排序：localhost:8080/api/tacos?sort=createAt,desc?page=0&size=12
public class SpringDataRestConfiguration
{
    @Bean
    // 将链接添加到 Spring Data REST 自动包含的链接列表 "_links" 中
    // Spring HATEOAS 将自动发现这个 bean（以及 RepresentationModelProcessor 类型的任何其他 bean），并将它们应用于适当的资源
    public RepresentationModelProcessor<PagedModel<EntityModel<Taco>>> tacoProcessor(EntityLinks links)
    {
//      匿名类
//      noinspection Convert2Lambda,Convert2Diamond
        return new RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>()
        {
            @Override
            public PagedModel<EntityModel<Taco>> process(PagedModel<EntityModel<Taco>> resource)
            {
                // 将 /api/rest/tacos/recent 添加至/api/rest/tacos的"_links"中
                resource.add(
                        links.linkFor(Taco.class)
                                .slash("recent")
                                .withRel("recents")
                );
                return resource;
            }
        };

//      lambda
//        return resource ->
//        {
//            resource.add(
//                    links.linkFor(Taco.class)
//                            .slash("recent")
//                            .withRel("recents")
//            );
//            return resource;
//        };
    }
}