# Building a RESTful Web Service with Spring Boot

Spring Boot uses Spring, which is a java-based framework that helps rapidly build and deploy an application. Spring Boot allows easy creation of a new Spring stand-alone application that you can run from a single jar file.


## Set up:
Either install or begin using eclipse for the project.
Check to make sure JDK version is at least 1.8, Maven 3.0

## Step 1: You'll want to do is to create a new Maven project in eclipse

Open pom.xml file and add the following under groupId and artifactId:

``` 
   <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.6.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <properties>
        <java.version>1.8</java.version>
    </properties>


    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build> 
```

## Step 2:  creating a web controller for your application.
Create a new java controller class and name it responsibly. Here is an example:

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

```
@RestController
public class Controller {

    @RequestMapping("/")
    public String index() {
        return "PlaceHolder";
    }
} 
```

## Step 3: Create the application class that will be controlled
Note: this is where the JDK 1.8 version is needed.

``` 
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
} 
```

## Step 4:
Open command window and enter this line:

``` mvn package && java -jar target/gs-spring-boot-0.1.0.jar ```
Make sure that the right jar file is running

## Step 5: Now you want to see your work. Open up the web browser and type:
``` http://localhost:8080 ```
The page will have the "Placeholder" message in it.

## Congrats! 
This is a baseline web application and it is running live! (until you stop it from running with ctrl-c in command window)



