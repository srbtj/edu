package com.syzton.sunread.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Petri Kainulainen
 */
@Configuration
@ComponentScan(basePackages = {
        "com.syzton.sunread.todo.service",
        "com.syzton.sunread.service",
        "com.syzton.sunread.model",
        "com.syzton.sunread.scheduler"

})
@Import({WebAppContext.class, PersistenceContext.class})
@PropertySource("classpath:application.properties")
@EnableScheduling
public class ApplicationContext {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

}
