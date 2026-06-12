package ec.edu.uteq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicacion Spring Boot.
 * Arranca un servidor Tomcat embebido en el puerto 8080.
 */
@SpringBootApplication
public class Practica7Application {

    public static void main(String[] args) {
        SpringApplication.run(Practica7Application.class, args);
    }
}
