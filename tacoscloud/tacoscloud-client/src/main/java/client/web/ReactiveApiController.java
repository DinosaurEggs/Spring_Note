package client.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import client.po.Ingredient;

@Slf4j
@Controller
@RequestMapping("/")
public class ReactiveApiController
{
    private final Flux<Ingredient> ingredientFlux;


    public ReactiveApiController()
    {
        this.ingredientFlux = WebClient.create()
                .get()
                .uri("http://localhost:8080/api/ingredient/")
                .exchangeToFlux(
                        clientResponse -> clientResponse.bodyToFlux(Ingredient.class)
                )
//                .delayElements(Duration.ofSeconds(1))
                .map((order) -> {
                    log.info("received\n" + order);
                    return order;
                });
    }


    @GetMapping
    public String api1(Model model)
    {
        IReactiveDataDriverContextVariable reactiveDataDriverContextVariable =
                new ReactiveDataDriverContextVariable(
                        ingredientFlux,
                        1
                );
        model.addAttribute("ingredients", reactiveDataDriverContextVariable);
        return "view";
    }
}
