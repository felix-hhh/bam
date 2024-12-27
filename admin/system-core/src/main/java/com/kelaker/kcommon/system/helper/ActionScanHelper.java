package com.kelaker.kcommon.system.helper;

import com.kelaker.kcommon.system.dto.SysActionDto;
import com.kelaker.kcommon.system.entity.SysAction;
import com.kelaker.kcommon.system.service.SysActionService;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.R;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import jakarta.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ActionScanHelper {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private SysActionService sysActionService;

    @PostConstruct
    public void init() {
        Set<String> allAction = sysActionService.getAllActionList().stream().map(SysAction::getActionCode).collect(Collectors.toSet());
        sysActionService.deleteByActionCodes(allAction);
        Map<String, Object> manageController = applicationContext.getBeansWithAnnotation(InModule.class);

        manageController.forEach((key, value) -> {
            Class<?> object = value.getClass();
            InModule module = object.getAnnotation(InModule.class);
            Map<String, String> pathPattern = this.getPathPattern();
            String moduleCode = module.moduleCode();

            for (Method method : object.getMethods()) {
                HasAction action = method.getAnnotation(HasAction.class);
                if (ValidateUtil.isNotBlank(action)) {
                    String actionCode = action.actionCode();
                    String actionName = action.actionName();

                    String methodName = method.toString();
                    String url = pathPattern.get(methodName);
                    url = url.replaceAll("\\{.*\\}", "*");

                    SysActionDto dto = new SysActionDto();
                    dto.setActionCode(actionCode);
                    dto.setActionName(actionName);
                    dto.setModuleCode(moduleCode);
                    dto.setUrl(url);

                    this.sysActionService.addSystemAction(dto);
                    log.debug("actionCode:{}, actionName:{}, methodName:{}", actionCode, actionName, methodName);

                }
            }

        });
    }

    /**
     * 取回所有请求字符串
     */
    private Map<String, String> getPathPattern() {
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        return handlerMethods
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getValue().getMethod().toString(),
                        e -> e.getKey().getPathPatternsCondition().getFirstPattern().getPatternString()
                ));
    }

}
