package org.tesolin.scope.definition;

import java.util.UUID;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class ConversationScopedAdvice {
    
    private final Logger log = LoggerFactory.getLogger(ConversationScopedAdvice.class);
    
    @Autowired
    private ConversationScope conversationScope;
    
//    @Around("execution(public * *(..)) && @annotation(ConversationScoped)")
//    public Object aroundConversation(MethodInvocation invocation) throws Throwable {
//        log.debug("Ending conversation");
////        conversationScope.registerConversation();
//        Object ret = invocation.proceed();
////        conversationScope.endConversation();
//        return ret;
//    }
    
    @Before("@annotation(ConversationTransaction)")
    public void beforeConversation() throws Throwable {
        log.debug("Begining conversation");
        String uuid = UUID.randomUUID().toString();
        conversationScope.registerConversation(uuid);
    }
    
    @After("@annotation(ConversationTransaction)")
    public void afterConversation() throws Throwable {
        log.debug("Ending conversation");
        conversationScope.endConversation();
    }
}
