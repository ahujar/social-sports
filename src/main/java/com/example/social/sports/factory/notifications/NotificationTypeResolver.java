package com.example.social.sports.factory.notifications;

import com.example.social.sports.service.notification.NotificationService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationTypeResolver implements ApplicationContextAware {

    private static ApplicationContext context;

    private static final Map<String, Class<? extends NotificationService>> instances = new HashMap<>();

    public static void register(String notifier, Class<? extends NotificationService> instance) {
        if (notifier != null && instance != null) {
            instances.put(notifier, instance);
        }
    }

    public static NotificationService getInstance(String notifier) {
        if (instances.containsKey(notifier)) {
            return context.getBean(instances.get(notifier));
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
