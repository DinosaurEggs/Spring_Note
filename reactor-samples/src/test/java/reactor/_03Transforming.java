package reactor;

import lombok.Data;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class _03Transforming
{
    /*skip take基于计数或持续时间的筛选条件的操作*/
    @Test
    public void skipAFew()
    {
        Flux<String> skipFlux = Flux.just("one", "two", "skip a few", "ninety nine", "one hundred")
                .skip(3);//该流跳过前三个项，并且只发布最后两个项

        StepVerifier.create(skipFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    public void skipAFewSeconds()
    {
        Flux<String> skipFlux = Flux.just("one", "two", "skip a few", "ninety nine", "one hundred")
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(4));//创建一个在发出任何值之前等待 4 秒的 Flux

        //由于该 Flux 是从项之间具有 1 秒延迟（使用 delayElements()）的 Flux 创建的，因此只会发出最后两个项
        StepVerifier.create(skipFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    public void take()
    {
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                .take(3);//发出前几个项

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    @Test
    public void takeAFewSeconds()
    {
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofMillis(3500));//在订阅后的前 3.5 秒内发出

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    @Test
    public void filter()
    {
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                .filter(s -> !s.contains(" "));//根据需要的任何条件有选择地发布

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Zion")
                .verifyComplete();
    }

    @Test
    public void distinct()
    {
        Flux<String> animalFlux = Flux.just("dog", "cat", "bird", "dog", "bird", "anteater")
                .distinct();//去重

        StepVerifier.create(animalFlux)
                .expectNext("dog", "cat", "bird", "anteater")
                .verifyComplete();
    }

    /*按照给定函数对其接收的每个对象执行指定的转换*/
    @Test
    public void map()//同步执行
    {
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(s -> {
                    String[] split = s.split("\\s");
                    return new Player(split[0], split[1]);
                });

        StepVerifier.create(playerFlux)
                .expectNext(new Player("Michael", "Jordan"))
                .expectNext(new Player("Scottie", "Pippen"))
                .expectNext(new Player("Steve", "Kerr"))
                .verifyComplete();
    }

    @Test
    public void flatMap()//异步执行
    {
        Flux<Player> playerFlux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(n -> Mono.just(n)
                                //将每个对象映射到一个新的 Mono 或 Flux。Mono 或 Flux 的结果被压成一个新的 Flux。
                                //被赋予一个 lambda 函数，该函数将传入 String 转换为 String 类型的 Mono。
                                // 然后对 Mono 应用 map() 操作，将 String 转换为 Player
                                .map(p -> {
                                    String[] split = p.split("\\s");
                                    return new Player(split[0], split[1]);
                                }).subscribeOn(Schedulers.parallel()).log()
                        //.immediate() 在当前线程中执行订阅
                        //.single() 在单个可重用线程中执行订阅，对所有调用方重复使用同一线程
                        //.newSingle() 在每个调用专用线程中执行订阅
                        //.elastic() 在从无限弹性池中提取的工作进程中执行订阅，根据需要创建新的工作线程，并释放空闲的工作线程（默认情况下 60 秒）
                        //.parallel() 在从固定大小的池中提取的工作进程中执行订阅，该池的大小取决于 CPU 核心的数量。
                );

        List<Player> playerList = Arrays.asList(
                new Player("Michael", "Jordan"),
                new Player("Scottie", "Pippen"),
                new Player("Steve", "Kerr"));

        StepVerifier.create(playerFlux)
                .expectNextMatches(playerList::contains)
                .expectNextMatches(playerList::contains)
                .expectNextMatches(playerList::contains)
                .verifyComplete();
    }

    @Data
    private static class Player
    {
        private final String firstName;
        private final String lastName;
    }
}
