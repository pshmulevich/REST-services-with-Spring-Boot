# Building a RESTful Web Service with Spring Boot

Spring Boot uses Spring, which is a java-based framework that helps rapidly build and deploy an application. Spring Boot allows easy creation of a new Spring stand-alone application that you can run from a single jar file.


# Part 1: Creating a basic REST service app:
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
Open command window and enter this command: `mvn clean package spring-boot:run`.

Make sure the Maven build is successful and the application starts with no errors.

## Step 5: Open up the web browser and type: `http://localhost:8080`.
The page should successfully load and display "Placeholder" in the content area.

## Step 6: Adding a service:
To add a service, for example, to display a date on your page, do the following:

1) Create an interface DateService with method getDate() that returns the current date and time:
```
 /**
  * Interface for a DateService
  *
  */
 public interface DateService {
    /**
     * Returns current date
     *
     * @return date
     */
    public String getDate();
 }
```

2) Create the implementation class DateServiceImpl that implements the method getDate(). 
Add @Component annotation to the DateServiceImpl class which makes it a Spring Bean, or in other words it makes it available for dependency injection:
```
...
 /**
  * DateService implementation.
  * The @Component annotation makes it a Spring Bean.
  */
 @Component
 public class DateServiceImpl implements DateService {
 
    @Override
    public String getDate() {
        return new Date().toString();
    }
}
...
```

3) Use @Autowired annotation to inject the newly created DateServiceImpl by its interface DateService into the Controller class: 
```
// Inject DateService
 +  @Autowired
 +  private DateService dateService;
```

4) Use the injected service to render the date in the response:
```
return "Welcome to Spring Application with Boot. It is " + dateService.getDate();
```

## Step 7: Adding more url mappings 
1) The original Controller had mapping to only one url:
```
@RequestMapping("/")
public String index() {
    return "Welcome to Spring Application with Boot. It is " + dateService.getDate();
}
```
2) A more sophisticated Controller can route to several urls instead of just one, depending on which url is bound to it.
Create a new method in the Controller class, called `page1()` and add a new `@RequestMapping` annotation to it, for example: 

```
@RequestMapping("/page1")
public String page1(){
    return "page1";
}
```
The response to `http://localhost:8080/page1` will show the string "page1" on it.


## Step 8: Writing a unit test to check content matching

1) The pom.xml file needs to be updated with a new dependency:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

2) In the same package as your Controller class being tested create a test class that will ensure the Controller output matches the expected string:
```
...
mvc.perform(MockMvcRequestBuilders.get("/page1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Welcome to page 1")));
...
```
where mvc is a MockMvc instance that is autowired into your test class.

3) Right click on the class and run it as JUnit application.


4) The previous unit test verified absolute matching. This will be harder for larger content. Instead it may be more desirable to verify a substring matching. To do that, create a new unit test.

Implement a special Matcher that handles finding a substring of a given string using `org.mockito.internal.matchers.Contains` class:
```
    Matcher<? super String> matcher = new Contains("page 1");
    mvc.perform(MockMvcRequestBuilders.get("/page1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(matcher));
```
7) When running the test, it will find "page 1" in the full string "Welcome to page 1"

Note: This allows you to automate a test to verify that at least part of the string you want to find in your code is there. Using the mvc platform also allows an easier way to search for a string than to look up regex patterns. 

## Adding a parameter to a controller method:

A parameter to a controller method is the same as an http request query parameter

In the Controller class, add on the parameter to the method signature:
`public String page1(@RequestParam(value = "applicationTitle") String applicationTitle) {...` 

To use this parameter in the body of the method, reference `applicationTitle` when computing the return value:
`return "Welcome to page 1 of " + parameter;`

Note: the get request to this url should now look like: `http://localhost:8080/page1?applicationTitle=app` instead of `http://localhost:8080/page1`. Spring will validate that this required parameter is present else the page loading will result in 400 error.

To test the new parameter, you will need to modify the unit test as well:
In the Controller SubstringTest to add a parameter you update the line `mvc.perform(MockMvcRequestBuilders.get("/page1").accept(MediaType.APPLICATION_JSON))`
 to include the query parameter part and create a corresponding local variable `String queryParams`for it:
 ```
String queryParams = "?" + paramName1 + "=" + paramValue1;
mvc.perform(MockMvcRequestBuilders.get("/page1" + queryParams).accept(MediaType.APPLICATION_JSON))
 ```
 If the part of the content that the test is validating involves the new parameter, you may also have to update the Matcher to include it as well.

## Adding a webpage Controller to work with JSP pages: 
Even though this is a REST application, it may be useful to have a controller to handle webpages

1) Create a `WebController` class.

2) Create a new controller class called WebController:
```
@Controller
public class WebController { ...}
```
3) Create a method for welcome
Note: the method needs annotation @GetMapping("/"); @GetMapping same as @RequestMapping, but it is specifically for the GET method. 
```
@GetMapping("/") // default mapping
    public String welcome(Map<String, Object> model) {...}
```
4) you should return the corresponding "welcome" at the end to enable successful routing.

5) Update the application.properties file:

i)This is the location for the jsp pages within `src/main/webapp`: `spring.mvc.view.prefix: /WEB-INF/jsp/`
ii) the jsp extension: `spring.mvc.view.suffix`: `.jsp`
iii) an override for `application.message` property; `application.message: Custom Application Message`

Additional Notes:
About displaying messages:

In the application.properties file, 

    spring.mvc.view.prefix: /WEB-INF/jsp/
    spring.mvc.view.suffix: .jsp
    application.message: Custom Application Message

6) Adding test for web page controller using jsp as view: 
see `WebControllerSubstringTest` class for details

# Part 2: Adding persistence

## Integrating REST services with a database using Hibernate
To make a useful REST application, we would need to read data from and store it in a database.

## Overall project structure
```
project-root
|
+-> src/main/java
|      |
|      +------> com.rest.example
|      |                    |
|      |                    +-------Application.java
|      |
|      +------> com.rest.example.bean
|      |                    |
|      |                    +-------Stock.java
|      |
|      +------> com.rest.example.controller
|      |                    |
|      |                    +-------MainController.java
|      |
|      +------> com.rest.example.persistence
|      |                    |
|      |                    +-------StockRepository.java
|      |
|      +------> src/main/resources
|                           |
|                           +-------application.properties
|            
+ -> src/test/java
|      |
|      +--------------> com.rest.example.controller
|                           |
|                           +-------ControllerTest.java
|
+-pom.xml
+-README.md
```
## Step 1: Update pom.xml file
Add the following dependencies to your pom.xml:
```
<!-- JPA Data (We are going to use Repositories, Entities, Hibernate, etc...) -->       
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Use MySQL Connector-J -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```
## Step 2: Create a database
 In this example, the Heidi MySQL client was used to manually create a new database.
 In the lefthand tab, right-click -> create new -> database and enter the database name `rest_example_db`.
 
## Step 3: Update the `application.properties` file
Add this property to automatically create tables:
`spring.jpa.hibernate.ddl-auto=create`
Configure your datasource url that consists of type, database name, host name
`spring.datasource.url=jdbc:mysql://<database-server-hostname>:<port>/<databaseName>`
Add your database credentials:

`spring.datasource.username=<username>`
`spring.datasource.password=<pwd>`

## Step 4: Create a bean class
You need a Bean class to map a database table to a Java object. For this example we will be using a Stock class. 
Apply the annotation `@Entity` to the class definition. This tells hibernate to map it to a database table.
Create only the default constructor and no others. Then add getters and setters. Build and run the project with this command: `mvn clean package spring-boot:run`.

Note that the command window output should have lines similar to:
```
org.hibernate.tool.hbm2ddl.SchemaExport  : HHH000227: Running hbm2ddl schema export
org.hibernate.tool.hbm2ddl.SchemaExport  : HHH000230: Schema export complete
```
These lines indicate that tables were created in the database.
 
To verify table creation go to your database client to check that you successfully created stock database table, using query commands:
`select * from stock`
Or:
`descr table stock`

## Step 5: Add the Controller class
This class uses the `@GetMapping` annotation. This annotation maps to the HTTP GET request, just like `@PostMapping` maps to the HTTP POST request.
Create separate methods for different REST operations like add, delete, get, getAll, etc.
For example:
```
    @GetMapping(path = "/add")
    public @ResponseBody String addNewUser(
            @RequestParam String symbol,
            @RequestParam Date date,
            @RequestParam Double previousClose,
            @RequestParam Double price) {
            ...
    }
```
## Step 6: Testing
There are three ways to test your application:
- Browser: enter this url into your browser: 
```
http://localhost:8080/demo/add?symbol=AAPL&date=09/21/2017&previousClose=100&price=100
```
After the page loads, you should see the message, "Saved new entry".
- Curl command: use a curl command to send a request to your controller. 
For example: 
```
curl 'localhost:<port>/demo/add?<parameter definitions>'
```
- Write a functional test using JUnit like StockControllerTest.

# Part 3:  Deploying to AWS

How to launch Rest project on AWS:

## Step 1: Create EC2 Instance
Create an AWS account (more details [here](http://docs.aws.amazon.com/AmazonSimpleDB/latest/DeveloperGuide/AboutAWSAccounts.html)) or log into your existing account on AWS.

* After logging in and When you are in the AWS services console, enter "EC2" in search bar

* On the Resources page, under the Create Instance section, click on the ```Launch Instance``` button.

* On the Choose an "Amazon Machine Image" page, open the ```Quick Start``` tab which should display a list of images.

* From the list of images, select free tier eligible instance Ubuntu

* Click through the steps at the top of the page and accept the defaults for this example

* On `5. Add Tags` step, add a tag called "Name" and in Value, enter the name for your EC2 instance, for example, "REST_Tutorial_EC2_Instance"

* Now click on `Configure Security Group`

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
