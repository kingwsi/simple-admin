package com.simple.handler;

import com.simple.common.utils.TokenUtils;
import com.simple.common.entity.common.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Description: sql拦截器，拦截新增/更新操作，对sql进行，记录操作者/操作时间
 * Author: ws
 * Date: 2020/3/30 22:34
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Component
@Slf4j
public class AuditorHandler implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        if (parameter instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) parameter;
            if (SqlCommandType.INSERT == sqlCommandType) {
                entity.setCreatedDate(LocalDateTime.now());
                entity.setCreator(TokenUtils.getCurrentPrincipalId());
            } else if (SqlCommandType.UPDATE == sqlCommandType) {
                entity.setCreatedDate(null);
                entity.setCreator(null);
                entity.setLastUpdater(TokenUtils.getCurrentPrincipalId());
                entity.setLastUpdateDate(LocalDateTime.now());
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
