package com.example.social.sports.factory.bookings;


import com.example.social.sports.service.bookings.BookingService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class SportFacilityBooking implements ApplicationContextAware {

    private static ApplicationContext context;

    private static final Map<String, Class<? extends BookingService>> instances = new HashMap<>();

    public static void register(String sport, Class<? extends BookingService> instance) {
        if (sport != null && instance != null) {
            instances.put(sport, instance);
        }
    }

    public static BookingService getInstance(String sport) {
        if (instances.containsKey(sport)) {
            return context.getBean(instances.get(sport));
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SportFacilityBooking.context = applicationContext;
    }
}
