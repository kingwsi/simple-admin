package com.simple;

import com.simple.common.entity.role.RoleVO;
import com.simple.service.ResourceService;
import com.simple.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BaseServiceApplicationTests {

    @Autowired
    RoleService roleService;

    @Autowired
    ResourceService resourceService;

    @Test
    public void contextLoads() {
        RoleVO roleWithResources = roleService.getRoleWithResources(1);
        log.info("role info -> {}", roleWithResources);
    }

    @Test
    public void generateCodeTest() {
        CodeGeneratorSupport codeGeneratorSupport = new CodeGeneratorSupport("customer", "Customer");
        codeGeneratorSupport.generateCommonModule();
        codeGeneratorSupport.generateServiceModule();
        codeGeneratorSupport.generateControllerModule();
    }

    @Test
    public void testRedisUtils() {
        String property = System.getProperty("user.dir");
        log.info("user.dir->{}", property);
    }

    @Test
    public void testMybatis(){
        List<String> apis = resourceService.listUrisByMethodAndUser("GET", 1);
        log.info("apis->{}", apis);
    }
}