package com.simple;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author ws
 * @Description TODO
 * @CreateTime 2021/03/25 14:16:00
 */
@Slf4j
public class CodeGenerator {
    public static void main(String[] args) {
        CodeGeneratorSupport codeGeneratorSupport = new CodeGeneratorSupport("filter_keyword", "FilterKeyword");
        codeGeneratorSupport.generateCommonModule();
        codeGeneratorSupport.generateServiceModule();
        codeGeneratorSupport.generateControllerModule();
    }
}
