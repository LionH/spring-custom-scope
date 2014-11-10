package org.tesolin.scope.definition;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.stereotype.Component;

@Component
public class ConversationScope implements Scope {

	private final Logger logger = LoggerFactory
			.getLogger(ConversationScope.class);

	@Autowired
	private ConfigurableBeanFactory beanFactory;

	private MultiKeyMap<String, Object> scopedBeans;

	@PostConstruct
	public void initialize() {
		logger.debug("Initializing solve scope");
		scopedBeans = new MultiKeyMap<String, Object>();
		beanFactory.registerScope("conversation", this);
	}

	@Override
	public synchronized Object get(String name, ObjectFactory<?> objectFactory) {
		String conversationId = getConversationId();
		Object result = null;
		result = scopedBeans.get(conversationId, name);
		boolean create = false;
		if (null == result) {
			create = true;
			result = objectFactory.getObject();
			scopedBeans.put(conversationId, name, result);
		}
		logger.debug("Getting bean [{}] for conversation [{}] has been created? [{}]", name,
				conversationId, create);
		return result;
	}

	@Override
	public Object remove(String name) {
		return scopedBeans.removeMultiKey(getConversationId(), name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object resolveContextualObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConversationId() {
		return ThreadedConversation.get();
	}

	public void registerConversation(String conversationId) {
		ThreadedConversation.register(conversationId);
	}

	public synchronized String endConversation() {
		String conversationId = ObjectUtils.defaultIfNull(getConversationId(),
				"");
		for (Map.Entry<MultiKey<? extends String>, Object> entry : scopedBeans
				.entrySet()) {
			String entryConversationId = entry.getKey().getKey(0);
			if (conversationId.equals(entryConversationId)) {
				beanFactory.destroyBean(entry.getKey().getKey(1),
						entry.getValue());
			}
		}
		scopedBeans.removeAll(conversationId);
		return conversationId;
	}

	private static class ThreadedConversation {

		private static final ThreadLocal<String> conversation = new InheritableThreadLocal<String>();

		public static void register(String conversationId) {
			conversation.set(conversationId);
		}

		public static String get() {
			return conversation.get();
		}
	}

}
