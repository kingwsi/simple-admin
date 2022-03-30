package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${package.Entity}.${entity}VO;
import ${package.Service}.${entity}Service;
import com.simple.common.bean.ResponseData;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* description: ${table.comment} controller <br>
* date: ${date} <br>
* author: ${author} <br>
*/

<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/api/${controllerMappingHyphen}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    private final ${entity}Service ${entity?uncap_first}Service;

    public ${entity}Controller(${entity}Service ${entity?uncap_first}Service) {
        this.${entity?uncap_first}Service = ${entity?uncap_first}Service;
    }

    @PutMapping
    public ResponseData<String> updateById(@RequestBody ${entity}VO ${entity?uncap_first}VO) {
        if (StringUtils.isEmpty(${entity?uncap_first}VO.getId())) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = ${entity?uncap_first}Service.updateById(${entity?uncap_first}VO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @PostMapping
    public ResponseData<String> create(@RequestBody ${entity}VO ${entity?uncap_first}VO) {
        boolean result = ${entity?uncap_first}Service.create(${entity?uncap_first}VO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @DeleteMapping("/{id}")
    public ResponseData<String> deleteById(@PathVariable Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = ${entity?uncap_first}Service.removeById(id);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @GetMapping("/page")
    public ResponseData<IPage<${entity}VO>> listOfPage(Page<${entity}VO> page, ${entity}VO ${entity?uncap_first}VO) {
        IPage<${entity}VO> pageInfo = ${entity?uncap_first}Service.listOfPage(page, ${entity?uncap_first}VO);
        return ResponseData.OK(pageInfo);
    }
}
</#if>
