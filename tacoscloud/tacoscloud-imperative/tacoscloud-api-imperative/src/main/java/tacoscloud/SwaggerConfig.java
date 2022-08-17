package tacoscloud;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * <p><strong>Rest API 文档 - Swagger</strong></p>
 * <a href="http://localhost/swagger-ui/index.html">访问路径:http://localhost/swagger-ui/index.html</a>
 * <p>通过swagger给一些比较难理解的接口或属性增加注释信息;  接口文档实时更新;  可以在线测试;</p>
 * <p>安全问题:正式上线的时候  记得关闭swagge</p>
 */
@SuppressWarnings("unused")
@EnableSwagger2
@Configuration
public class SwaggerConfig
{
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()) // 配置swagger2信息
                .enable(true) // 是否启用swagger
                .select()
                // 配置扫描接口的方式
                // basePackage 配置要扫描的包
                // any 扫描全部
                // none 不扫描
                // withClassAnnotation 扫描类上的注解
                // withMethodAnnotation 扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("tacoscloud.web.api"))
                // 扫描过滤方式
                // any   过滤全部
                // none  不过滤
                // regex 正则过滤
                // ant   过滤指定路径
                .paths(PathSelectors.any())
                .build();
    }
    
    public ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title("Tacos Cloud")
                .description("Market Demo")
                .termsOfServiceUrl("0.0.0.0")
                .version("0.0.1 Alpha")
                .license("None")
                .build();
    }
}
