package org.springframework.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.domain.Blog;
import org.springframework.test.service.ShopService;

public class App  {
    private static final Logger logger = LogManager.getLogger(App.class);
    
    public static void main( String[] args ) {
        logger.entry();
        
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        
        ShopService shopService = context.getBean(ShopService.class);
        Blog b = shopService.test();
        System.out.println(b.getId());
        System.out.println(b.getName());
        System.out.println(b.getContext());
    }
}
