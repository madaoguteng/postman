package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by caojun on 2017/11/9.
 */
@Configuration
@ComponentScan(value={"io.postman.repository.hibernate"})
public class AppConfiguration {
}
