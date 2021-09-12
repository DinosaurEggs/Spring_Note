package tacoscloud.web;

import tacoscloud.domain.Taco;
import tacoscloud.domain.Ingredient;
import tacoscloud.domain.Ingredient.Type;
import tacoscloud.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping(
        name = "",  //配置请求映射名称
        path = "/design",  //路径映射
        method = {},  //限定只响应HTTP 请求类型,如GET,POST,HEAD,OPTIONS,PUT,TRACE等 默认的情况下,可以响应所有的请求类型
        params = {},  //当存在对应的HTTP 参数时才响应请求
        headers = {},  //限定请求头存在对应的参数时才响应
        consumes = {},  //限定HTTP请求体提交类型， 如"application/json" "text/html"
        produces = {}  //／／限定返回的内容类型，仅当HTTP请求头中的（ Accept ）类型中包含该指定类型时才返回
)
@SessionAttributes("order")
@SuppressWarnings({"DefaultAnnotationParam", "unused"})
public class DesignTacoController
{

    private final List<Ingredient> ingredients;

    @Autowired
    public DesignTacoController( List<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    @ModelAttribute(name = "order")
    public Order order()
    {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco design()
    {
        return new Taco();
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model)
    {
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(
                    type.toString().toLowerCase(),
                    filterByType(ingredients, type)
            );
        }
    }

    @GetMapping
    public String showDesignForm(Model model)
    {
        return "design";
    }

    @PostMapping
    public String processDesign(@ModelAttribute("order") Order order, @Valid @ModelAttribute("taco") Taco taco, Errors errors)
    {
        if (errors.hasErrors()) {
            return "design";
        }

        order.addDesign(taco);

        log.info("Processing design:\n" + taco);
        return "redirect:/orders/current";
    }

    //事务管理
    @Transactional(
            transactionManager = "",//和value相同 当配置了多个事务管理器时，可以使用该属性指定选择哪个事务管理器。
            propagation = Propagation.REQUIRED,//事务的传播机制
            isolation = Isolation.DEFAULT,//事务的隔离级别
            readOnly = false,//如果一个事务只对数据库执行读操作，那么该数据库就可能利用那个事务的只读特性，采取某些优化措施
            timeout = -1,//事务超时 如果超过该时间限制但事务还没有完成，则自动回滚事务
            rollbackFor = {RuntimeException.class},//事务在出现特定的异常时回滚 指定单一异常类：rollbackFor=RuntimeException.class
            noRollbackFor = {Exception.class}//事务在出现特定的异常时不回滚
    )
    void saveTacoInformation(Taco design, Order order)
    {
        order.addDesign(design);
    }

    // provided by 'aexiaosong'
    static public List<Ingredient> filterByType(List<Ingredient> ingredients, Type type)
    {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
