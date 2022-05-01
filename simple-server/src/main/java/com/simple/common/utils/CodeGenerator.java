package com.simple.common.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.simple.common.entity.common.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description: CodeGeneratorService <br>
 * date: 2020/8/6 10:06 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
public class CodeGenerator {

    private final static Logger log = LoggerFactory.getLogger(CodeGenerator.class);
    private static String TABLE_NAME;
    private static String ENTITY_NAME;
    private static String AUTHOR;
    private static String PREFIX;
    /**
     * 包路径
     */
    private static String BASE_PATH;
    /**
     * web工程目录 绝对路径
     */
    private static String WEB_PROJECT_PATH;


    public static void main(String[] args) {
        // 表名
        TABLE_NAME = "sys_login_record";
        // 实体类名
        ENTITY_NAME = "LoginRecord";
        // 表名前缀
        PREFIX = "sys_";
        // web项目路径
        WEB_PROJECT_PATH = "/Users/ws/Documents/projects/simple-admin/simple-web";
        // 包路径
        BASE_PATH = "/src/main/java/com/simple";
        // 作者
        AUTHOR = "ws";
        generateCommonModule();
        generateServiceModule();
        generateControllerModule();
    }

    public CodeGenerator(String tableName, String entityName) {
        TABLE_NAME = tableName;
        ENTITY_NAME = entityName;
        String username = System.getenv().get("USERNAME");
        if (!StringUtils.isEmpty(username)) {
            AUTHOR = username;
        } else {
            AUTHOR = "";
        }
    }

    /**
     * 获取数据源
     *
     * @return
     */
    protected static DataSourceConfig getDataSource() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://115.227.24.85:3306/simple_admin?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("IOuw@ier8#Oi");
        return dataSourceConfig;
    }

    /**
     * EntityService
     * EntityMapper
     * EntityMapper.xml
     */
    protected static void generateServiceModule() {
        AutoGenerator mpg = new AutoGenerator();
        String fileSeparator = System.getProperty("file.separator");
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java".replaceAll("/", fileSeparator));
        log.info("service 生成输出路径->{}", gc.getOutputDir());
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setEntityName(ENTITY_NAME);
        gc.setServiceName(ENTITY_NAME + "Service");
        gc.setMapperName(ENTITY_NAME + "Mapper");
        gc.setXmlName(ENTITY_NAME + "Mapper");
        gc.setIdType(IdType.NONE);

        mpg.setGlobalConfig(gc);
        mpg.setDataSource(getDataSource());

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.simple");
        pc.setEntity("common.entity." + ENTITY_NAME.toLowerCase());
        pc.setService("service");
        pc.setMapper("mapper");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 自定义属性注入
        // 在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                setMap(map);
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir(BASE_PATH + "/mapper/" + ENTITY_NAME.toLowerCase() + "Mapper");
                if (fileType == FileType.MAPPER) {
                    log.warn("{} 已存在", filePath);
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                log.info("允许生成模板文件");
                return true;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity(null);
        templateConfig.setService("templates/service.java");
        templateConfig.setController(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setMapper("templates/mapper.java");
        templateConfig.setXml("templates/mapper.xml");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix(PREFIX);
        strategy.setInclude(TABLE_NAME);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * EntityController
     */
    public static void generateControllerModule() {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();

        String fileSeparator = System.getProperty("file.separator");
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java".replaceAll("/", fileSeparator));
        log.info("Controller 生成输出路径->{}", gc.getOutputDir());
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setEntityName(ENTITY_NAME);
        gc.setServiceName(ENTITY_NAME + "Service");
        gc.setMapperName(ENTITY_NAME + "Mapper");
        gc.setControllerName(ENTITY_NAME + "Controller");
        gc.setIdType(IdType.ASSIGN_UUID);

        mpg.setGlobalConfig(gc);
        mpg.setDataSource(getDataSource());

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.simple");
        pc.setEntity("common.entity." + ENTITY_NAME.toLowerCase());
        pc.setService("service");
        pc.setController("api");
        pc.setMapper("mapper");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                setMap(map);
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir(BASE_PATH + "/api/".replaceAll("/", fileSeparator) + ENTITY_NAME + "Controller");
                if (fileType == FileType.CONTROLLER) {
                    log.warn("{} 已存在", filePath);
                    return !new File(filePath).exists();
                }
                return true;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity(null);
        templateConfig.setService(null);
        templateConfig.setController("templates/controller.java");
        templateConfig.setServiceImpl(null);
        templateConfig.setMapper(null);
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix(PREFIX);
        strategy.setInclude(TABLE_NAME);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * common module
     * 生成 entity,vo,mapper,covertMapper,
     */
    public static void generateCommonModule() {
        AutoGenerator mpg = new AutoGenerator();
        GlobalConfig gc = new GlobalConfig();
        String fileSeparator = System.getProperty("file.separator");
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java".replaceAll("/", fileSeparator));
        log.info("common 生成输出路径->{}", gc.getOutputDir());
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setEntityName(ENTITY_NAME);
        gc.setServiceName(ENTITY_NAME + "Service");
        gc.setMapperName(ENTITY_NAME + "Mapper");
        gc.setIdType(IdType.ASSIGN_UUID);
        mpg.setGlobalConfig(gc);
        String outputDir = (projectPath + BASE_PATH + "/common/entity/" + ENTITY_NAME.toLowerCase() + "/").replaceAll("/", fileSeparator);
        mpg.setDataSource(getDataSource());

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.simple");
        pc.setEntity("common.entity." + ENTITY_NAME.toLowerCase());
        mpg.setPackageInfo(pc);

        // 自定义属性注入
        // 在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                setMap(map);
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templates/convertMapper.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return outputDir + ENTITY_NAME + "ConvertMapper" + StringPool.DOT_JAVA;
            }
        });
        /*EntityVO*/
        focList.add(new FileOutConfig("/templates/entityVO.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return outputDir + ENTITY_NAME + "VO" + StringPool.DOT_JAVA;
            }

        });
        // 替换目录 生成vue相关文件
        focList.add(new FileOutConfig("/templates/formModal.vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String modulesDir = (WEB_PROJECT_PATH + "/src/views/" + ENTITY_NAME.toLowerCase() + "/modules/").replaceAll("/", fileSeparator);
                File dirCheck = new File(modulesDir);
                if (!dirCheck.exists()) {
                    boolean mkdirs = dirCheck.mkdirs();
                    log.warn("web项目目录{}不存在，->创建目录{}", modulesDir, mkdirs);
                }
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return modulesDir + "FormModal.vue";
            }

        });
        focList.add(new FileOutConfig("/templates/list.vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String modulesDir = (WEB_PROJECT_PATH + "/src/views/" + ENTITY_NAME.toLowerCase() + "/").replaceAll("/", fileSeparator);
                File dirCheck = new File(modulesDir);
                if (!dirCheck.exists()) {
                    boolean mkdirs = dirCheck.mkdirs();
                    log.warn("web项目目录{}不存在，->创建目录{}", modulesDir, mkdirs);
                }
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return modulesDir + "List.vue";
            }

        });
        focList.add(new FileOutConfig("/templates/index.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String modulesDir = (WEB_PROJECT_PATH + "/src/api/").replaceAll("/", fileSeparator);
                File dirCheck = new File(modulesDir);
                if (!dirCheck.exists()) {
                    boolean mkdirs = dirCheck.mkdirs();
                    log.warn("web项目目录{}不存在，->创建目录{}", modulesDir, mkdirs);
                }

                return modulesDir + toSymbol(ENTITY_NAME, "-") + ".js";
            }

        });
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir(BASE_PATH + "/common/entity/".replaceAll("/", fileSeparator) + ENTITY_NAME + StringPool.DOT_JAVA);
                if (fileType == FileType.MAPPER) {
                    log.warn("{} 已存在", filePath);
                    return !new File(filePath).exists();
                }
                return true;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/entity.java");
        templateConfig.setService(null);
        templateConfig.setController(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setMapper(null);
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix(PREFIX);
        strategy.setInclude(TABLE_NAME);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * -转驼峰
     */
    public static String toHump(String str, String symbol) {
        Matcher matcher = Pattern.compile(symbol + "(\\w)").matcher(str.toLowerCase());
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线,效率比上面高
     */
    public static String toSymbol(String str, String symbol) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, symbol + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        if (sb.indexOf(symbol) == 0) {
            return sb.substring(1);
        }
        return sb.toString();
    }
}
