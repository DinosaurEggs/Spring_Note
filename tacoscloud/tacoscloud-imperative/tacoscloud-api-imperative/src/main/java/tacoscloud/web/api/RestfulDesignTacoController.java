package tacoscloud.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacoscloud.data.TacoRepository;
import tacoscloud.domain.Taco;

/*
|    **作用范围**    |      **API**       |                       **API常用参数**                        |         **作用位置**         |
| :----------------: | :----------------: | :----------------------------------------------------------: | :--------------------------: |
|     协议集描述     |        @Api        |              @Api(tags = {"tag1","tag2","..."})              |         controller类         |
|      协议描述      |   @ApiOperation    |       @ApiOperation(value = "功能描述",notes = "备注")       |      controller类的方法      |
| 描述返回对象的意义 |     @ApiModel      |         @ApiModel(value="类名",description="类描述")         |          返回对象类          |
|      对象属性      | @ApiModelProperty  | @ApiModelProperty(value = "类属性描述",required = *true*,example = "属性举例",notes = "备注") |      出入参数对象的字段      |
|    非对象参数集    | @ApiImplicitParams | @ApiImplicitParams({@ApiImplicitParam(),@ApiImplicitParam(),...}) |       controller的方法       |
|   非对象参数描述   | @ApiImplicitParam  | @ApiImplicitParam(name = "参数名",value = "参数描述",required = *true*,paramType = "接口传参类型",dataType = "参数数据类型") | @ApiImplicitParams的方法里用 |
|     Response集     |   @ApiResponses    |    @ApiResponses({     @ApiResponse(),@ApiResponse(),..})    |       controller的方法       |
|      Response      |    @ApiResponse    |       @ApiResponse(code = 10001, message = "返回信息")       |      @ApiResponses里用       |
|      忽略注解      |     @ApiIgnore     |                          @ApiIgnore                          |      类，方法，方法参数      |
 */

@Api(tags = "DesignTaco-Restful")
@RestController
@RequestMapping(path = "/api/design", produces = "application/json")
@CrossOrigin
public class RestfulDesignTacoController
{
    private final TacoRepository tacoRepository;

    @Autowired
    public RestfulDesignTacoController(TacoRepository tacoRepository)
    {
        this.tacoRepository = tacoRepository;
    }

    @ApiOperation("Get Recent Tacos")
    @GetMapping("/recent")
    public Iterable<Taco> recentTacos()
    {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }


    @ApiOperation("Get Taco By ID")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "Taco ID", required = true, dataType = "long", paramType = "path"),})
    @GetMapping("/{id}")
    public Taco tacoById(@PathVariable("id") long id)
    {

        return tacoRepository.findById(id).orElse(null);
    }

    @ApiOperation("Save Taco")
    @ApiImplicitParam(name = "taco", value = "Taco Infomation", required = true, dataType = "Taco", paramType = "body")
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco)
    {
        return tacoRepository.save(taco);
    }
}
