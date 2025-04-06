package dev.artsupplier;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class Application{

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone(); // or Clock.systemUTC()
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
