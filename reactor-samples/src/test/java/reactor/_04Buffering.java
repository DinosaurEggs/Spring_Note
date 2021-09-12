package reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class _04Buffering
{
    @Test
    public void buffer()
    {
        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");
        //缓冲数据
        //将数据流分解成固定大小的块
        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);

        StepVerifier
                .create(bufferedFlux)
                .expectNext(Arrays.asList("apple", "orange", "banana"))
                .expectNext(Arrays.asList("kiwi", "strawberry"))
                .verifyComplete();
    }

    @Test
    public void bufferAndFlatMap()
    {
        Flux.just("apple", "orange", "banana", "kiwi", "strawberry")
                .buffer(3)
                .flatMap(x -> Flux.fromIterable(x)
                        .map(String::toUpperCase)
                        .subscribeOn(Schedulers.parallel())
                        //每个缓冲 List 在单独的线程中进一步并行处理
                        .log()//记录所有的 Reactor Streams 事件
                ).subscribe();
    }

    @Test
    public void collectList()
    {
        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");
        //生成一个发布 List 的 Mono
        Mono<List<String>> fruitListMono = fruitFlux.collectList();

        StepVerifier.create(fruitListMono)
                .expectNext(Arrays.asList("apple", "orange", "banana", "kiwi", "strawberry"))
                .verifyComplete();
    }

    @Test
    public void collectMap()
    {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
        //生成一个发布 Map 的 Mono
        //key由传入消息的生成
        Mono<Map<Character, String>> animalMapMono = animalFlux.collectMap(a -> a.charAt(0));

        //则流经流的最后一个条目将覆盖所有先前的条目
        StepVerifier.create(animalMapMono)
                .expectNextMatches(map -> map.size() == 3 &&
                        map.get('a').equals("aardvark") &&
                        map.get('e').equals("eagle") &&
                        map.get('k').equals("kangaroo"))
                .verifyComplete();
    }
}
