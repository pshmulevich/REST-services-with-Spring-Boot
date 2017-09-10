# Building a RESTful Web Service with Spring Boot

Spring Boot uses Spring, which is a java-based framework that helps rapidly build and deploy an application. Spring Boot allows easy creation of a new Spring stand-alone application that you can run from a single jar file.


## Set up:
Either install or begin using eclipse for the project.
Check to make sure JDK version is at least 1.8, Maven 3.0

## Step 1: Create a new Maven project in eclipse

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

## Step 2:  Create a web controller for the application.
Create a new java controller class and name it responsibly. Here is an example:

```
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

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
Open command window and enter this command:

```
mvn clean package spring-boot:run
```
Make sure that the right jar file is running

## Step 5: Open up the web browser and type:
```
http://localhost:8080
```
The page will have the "Placeholder" message in it.

# Part 2:  Deploying to AWS

How to launch Rest project on AWS:

## Step 1: Create EC2 Instance
Create an AWS account (more details [here](http://docs.aws.amazon.com/AmazonSimpleDB/latest/DeveloperGuide/AboutAWSAccounts.html)) or log into your existing account on AWS.

* After logging in and When you are in the AWS services console, enter "EC2" in search bar

* On the Resources page, under the Create Instance section, click on the ```Launch Instance``` button.

* On the Choose an "Amazon Machine Image" page, open the ```Quick Start``` tab which should display a list of images.

* From the list of images, select free tier eligible instance Ubuntu

* Click through the steps at the top of the page and accept the defaults for this example

* On ```5. Add Tags``` step, add a tag called "Name" and in Value, enter the name for your EC2 instance, for example, "REST_Tutorial_EC2_Instance"



* Now click on ```Configure Security Group```

* For SSH "rule", its ok to keep Source as 0.0.0.0/0, but for your custom TCP rule, you will need to fix the port at 8080 for the current example

* Open a web browser and type in "whatsmyip" after which Google will display your public IP address. Copy it and put that IP address into the rightmost Source drop down after you have selected "My IP". 

Special note: if you want your instance to run perpetually regardless of shutting everything down, you'll need to use the following command:
"nohup java -<your jarfile>"
To terminate it at any time, use the command:
"kill -9 PID"

* Select "Review and Launch"

* Click "Launch" and you will be prompted with the dialogue: "Select and existing key pair or create a new key pair" for the purpose of this tutorial, we will create a new key pair.

* Select Create a new key pair, name it appropriately

* Download the created Key Pair in a location you will remember

* At this point click Review and Launch. The Instance is officially deployed on AWS.

## How to access what you created:

* Click on Connect and make a note of your EC2 public DNS address 

* Open command window (on linux system)

* Use the command: "chmod 400 <key file location>" to make sure that your private key is only visible to you

* Log in via ssh with the command: "ssh ubuntu@<your ec2 public address>"

* sudo apt install default-jdk

* sudo apt install maven

* Now create a folder named git in your home folder called /home/ubuntu

* cd git

* git clone <URL of your github project>

*  mvn clean package spring-boot:run


Referenced link:
1) https://spring.io/guides/gs/spring-boot/
2) https://mydevgeek.com/deploying-spring-boot-application-aws-using-ec2-rds/

