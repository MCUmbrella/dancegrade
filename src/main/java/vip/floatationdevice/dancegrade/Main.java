package vip.floatationdevice.dancegrade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SuppressWarnings("unused")
@SpringBootApplication
public class Main
{
    private static final Logger l = LoggerFactory.getLogger(Main.class);
    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args)
    {
        for(String a : args)
            if("--fixdb".equals(a))
                try
                {
                    DataManager.resetDatabase();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    System.exit(0);
                }
        l.info("Starting up");
        SpringApplication.run(Main.class, args);
        l.info("Startup completed");
    }

    public static ConfigurableApplicationContext getContext(){return applicationContext;}

    public static void shutdown(int code)
    {
        applicationContext.close();
        System.exit(code);
    }

    @PostConstruct
    public void onStartup()
    {
        DataManager.initialize();
    }

    @PreDestroy
    public void onShutdown()
    {
        l.info("Shutting down");
    }
}
