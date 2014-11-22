package org.tesolin.scope.monitoring;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.tesolin.scope.definition.ConversationScope;

@Component
@ManagedResource(
        objectName="bean:name=app-monitoring",
        description="Conversation Scope monitoring",
        log=true,
        logFile="jmx.log",
        currencyTimeLimit=1,
        persistPolicy="OnUpdate",
        persistPeriod=200,
        persistLocation="foo",
        persistName="bar")
public class AppMonitoring {
	@Autowired
	private ConversationScope conversationScope;
	
	@ManagedAttribute(description="Monitored conversations", currencyTimeLimit=1)
	public Collection<String> getMonitorConversations() {
		return conversationScope.getCurrentConversations();
	}
	
	@ManagedOperation(description="Monitored beans by conversation", currencyTimeLimit=-1)
	@ManagedOperationParameters({@ManagedOperationParameter(name="conversationId", description="The conversation id")})
	public Collection<String> getMonitorConversations(String conversationId) {
		return conversationScope.getCurrentConversations();
	}
}
