package com.lsj.usercenter.aop;


import com.lsj.usercenter.model.common.ErrCode;
import com.lsj.usercenter.model.constant.SystemConstants;
import com.lsj.usercenter.model.domain.Operation;
import com.lsj.usercenter.model.domain.RequestContext;
import com.lsj.usercenter.service.audit.AuditAdapter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
@Slf4j
public class OpreationAspect {


    @Resource
    private AuditAdapter auditAdapter;

    private final Map<String, Operation> operations;

    public OpreationAspect() {
        this.operations = new HashMap<>();
    }

    @Pointcut("@annotation(Operator)")
    void pointcut(){}


    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {

        Operation operation = getOperation(joinPoint);

        ServletRequestAttributes attributes = Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = attributes.getRequest();

        RequestContext requestContext = RequestContext.build(request);
        requestContext.setOperation(operation);

        attributes.setAttribute(SystemConstants.DEFAULT_REQUST_ATTRIBUTES, requestContext, RequestAttributes.SCOPE_REQUEST);

        log.info("{} {} {}",request.getMethod(), request.getRequestURI(), operation.getMethodSignature());

        OpreationExecutor executor = new OpreationExecutor(requestContext, auditAdapter, joinPoint);
        return executor.execute();

    }

    Operation getOperation(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String key = signature.toLongString();

        Operation operation;
        synchronized (operations) {
            operation =  operations.get(key);
            if (operation != null) {
                return operation;
            }
        }

        Method method = signature.getMethod();
        Operator annotation = Objects.requireNonNull(method.getAnnotation(Operator.class), "Operation cant not be null");
        List<String> anyRoles = Arrays.stream(annotation.anyRoles()).filter(StringUtils::isNotBlank).toList();
        List<String> mustRoles = Arrays.stream(annotation.mustRoles()).filter(StringUtils::isNotBlank).toList();
        operation = new Operation(key, annotation.value());
        operation.setAuditEnabled(annotation.auditEnabled());
        operation.setAnyRoles(anyRoles);
        operation.setMustRoles(mustRoles);
        synchronized (operations) {
            operations.put(key, operation);
        }
        return operation;
    }

}
