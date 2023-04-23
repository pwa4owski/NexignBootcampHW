package nexign.bootcamp;

import nexign.bootcamp.cdr.service.CdrService;
import nexign.bootcamp.util.DataGenerationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        // Генерация данных БД при запуске приложения с параметром
        if(args.length > 0 && args[0].equals("generate-data")){
            context.getBean(DataGenerationService.class).generateData();
        }
        // первичная генерация CDR и автоматическая тарификация при запуске приложения
        context.getBean(CdrService.class).processTariffication();

    }

}
