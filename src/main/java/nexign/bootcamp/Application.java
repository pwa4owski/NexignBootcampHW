package nexign.bootcamp;

import nexign.bootcamp.cdr.service.CdrService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        //TODO: первичная генерация CDR при запуске приложения
        context.getBean(CdrService.class).processTariffication();

    }

}
