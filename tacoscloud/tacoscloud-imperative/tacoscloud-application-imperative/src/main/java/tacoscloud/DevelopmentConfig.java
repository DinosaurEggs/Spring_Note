package tacoscloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Configuration
//取代XML来配置Spring 作为bean定义的源 允许通过调用同一类中的其他@Bean method方法来定义bean之间的依赖关系 URL:https://zhuanlan.zhihu.com/p/157679528
@Profile({"prop01", "!test"})//在指定配置文件激活时加载
public class DevelopmentConfig
{
    @Bean
    @Order(value = 1)
    //SpringBoot在项目启动后会遍历所有实现CommandLineRunner的实体类并执行run方法，如果需要按照一定的顺序去执行，那么就需要在实体类上使用一个@Order注解（或者实现Order接口）来表明顺序
    public CommandLineRunner loader()//CommandLineRunner 实现方法二
    {
        return args -> System.out.println("------ Loading Resource ------");
    }
}

@Component
@Order(value = 2)
class ComponentRunner implements CommandLineRunner
{
    //CommandLineRunner 实现方法一
    @Override
    public void run(String... args)
    {
        System.out.println("------ Starting Container ------");
    }
}
