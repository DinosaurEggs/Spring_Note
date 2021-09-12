import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tacoscloudreactive.TacosCloudReactiveApplication;
import tacoscloudreactive.cache.TacoTemporary;
import tacoscloudreactive.domain.Taco;

@Slf4j
@SpringBootTest(classes = TacosCloudReactiveApplication.class)
public class test
{
    private final TacoTemporary tacoTemporary;

    @Autowired
    public test(TacoTemporary tacoTemporary)
    {
        this.tacoTemporary = tacoTemporary;
    }


    @Test
    public void test1()
    {
        Taco taco = new Taco();
        taco.setName("taoc_name");
        Boolean aBoolean = tacoTemporary.save(taco, "233").block();
        log.info(aBoolean + "xxxxxxxxx");
        tacoTemporary.findByName("taoc_name", "233").subscribe(
                (x) -> System.out.println("llllllllllllllllllllllllllllllllllllllll\n" + x)
        );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
