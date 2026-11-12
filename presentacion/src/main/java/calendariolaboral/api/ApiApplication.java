package calendariolaboral.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "controladores",
    "servicios",
    "repositorios",
    "persistencia",
    "clientes",
    "handlers"
})
@EntityScan(basePackages = {
    "persistencia.entidades"
})
@EnableJpaRepositories(basePackages = {
    "persistencia.repositorios.jpa"
})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}