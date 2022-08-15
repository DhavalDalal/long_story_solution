# Getting Started

## To Start Dev Loop 
1. In the first Terminal (Boot Run) ==> ```gradle bootRun -Dspring.profiles.active=development``` or to run on another 
   port ```gradle bootRun -PjvmArgs="-Dserver.port=10001"```.  To see the Log INFO statements, 
   use ```gradle bootRun -Dspring.profiles.active=development -info```.  If ```-Dspring.profiles.active``` is not given, 
   then the default, ```development``` profile gets selected.  
    * To run a different profile at start-up, use ```gradle bootRun -Dspring.profiles.active=jenkins```.  In the 
    Other available profile is ```production```.
2. In the second Terminal (Reload Latest Changes) ==> 
    * To reload latest classes in the JVM, use ```gradle compileJava```  
    * To reload latest changes in static HTML files, use ```gradle reload```  
3. In the third Terminal (Running Tests) ==>  
   * Either use ```gradle -t test``` or run it from within the IDE.   
    
## To Run Specific Tests
* To run a specific test like the ```PortfolioApplicationSpecs```, use==> ```gradle test --tests com.tsys.long_story.PortfolioApplicationSpecs```.
    
## To Run
* Using Command-Line 
    * Open Terminal and run ```java -Dspring.profiles.active=development -jar ./build/libs/long_story-0.0.1-SNAPSHOT.jar```
    * Usually for production profile, server port is supplied from outside as a environment parameter ```-Dserver.port=10002```.  Open Terminal and run ```java -Dserver.port=10002 -Dspring.profiles.active=production -jar ./build/libs/long_story-0.0.1-SNAPSHOT.jar```
    * To see the INFO level Log output use, ```java -Dspring.profiles.active=development -debug -jar ./build/libs/long_story-0.0.1-SNAPSHOT.jar```
* Using Intellij IDE
    * Edit the run configuration for ```PortfolioApplication``` and add ```-Dspring.profiles.active=development``` to the VM arguments.
* Using only Eclipse IDE???
    * Edit the run configuration for ```PortfolioApplication``` and add ```-Dspring.profiles.active=development``` to the VM arguments.

## To Debug
* Using only Intellij IDE
    * Debugging is as simple as navigating to the class with the main method, right-clicking the triangle icon, and choosing Debug.
* Using another JVM process and Intellij IDE
    1. In one Terminal ==> ```gradle bootRun -Dserver.port=10001 -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000```
        * Understanding the Java Debug Args - By default, the JVM does not enable debugging. This is because:
          * It is an additional overhead inside the JVM. 
          * It can potentially be a security concern for apps that are in public.
          Hence debugging is only done during development and never on production systems.
          
          Before attaching a debugger, we first configure the JVM to allow debugging. 
          We do this by setting a command line argument for the JVM:
          ```-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000```
          
          * ```-agentlib:jdwp``` - Enable the Java Debug Wire Protocol (JDWP) agent inside the JVM. This is the main command line argument that enables debugging.
          * ```transport=dt_socket``` - Use a network socket for debug connections. Other options include Unix sockets and shared memory.
          * ```server=y``` - Listen for incoming debugger connections. When set to n, the process will try to connect to a debugger instead of waiting for incoming connections. Additional arguments are required when this is set to n.
          * ```suspend=n``` - Do not wait for a debug connection at startup. The application will start and run normally until a debugger is attached. When set to y, the process will not start until a debugger is attached.
          * ```address=8000``` - The network port that the JVM will listen for debug connections. Remember, this should be a port that is not already in use.
    2. On Intellij IDE ==> 
        * Open menu Run | Edit Configurations...
        * Click the + button and Select 'Remote' from Templates

## Configuring Spring Boot to use GSON instead of Jackson 
Article from [https://www.callicoder.com/configuring-spring-boot-to-use-gson-instead-of-jackson](https://www.callicoder.com/configuring-spring-boot-to-use-gson-instead-of-jackson)

Spring Boot uses Jackson by default for serializing and deserializing request and response objects in your REST APIs.

If you want to use GSON instead of Jackson then it’s just a matter of adding Gson dependency in your pom.xml file and specifying a property in the application.properties file to tell Spring Boot to use GSON as your preferred json mapper.

Force Spring Boot to use GSON instead of Jackson
####1. Add Gson dependency
Open your ```pom.xml``` file and add the GSON dependency like so -
```
<!-- Include GSON dependency -->
<dependency>
	<groupId>com.google.code.gson</groupId>
	<artifactId>gson</artifactId>
	<version>2.8.4</version>
</dependency>
```
Once you do that, Spring Boot will detect Gson dependency on the classpath and 
automatically create a Gson bean with sensible default configurations. 
You can also autowire gson in your spring components directly like so -

```
@Autowire
private Gson gson;
```
If you’re curious how Spring Boot does that, then take a look at this [GsonAutoConfiguration](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/gson/GsonAutoConfiguration.java)  class. Notice how it uses @ConditionalOnClass(Gson.class) annotation to trigger the auto-configuration when Gson is available on the classpath.

Jackson is also configured in a similar fashion with [JacksonAutoConfiguration](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jackson/JacksonAutoConfiguration.java) class.

####2. Set the preferred json mapper to gson
You can now ask Spring Boot to use Gson as your preferred json mapper by specifying the following property in the application.properties file -

```
# Preferred JSON mapper to use for HTTP message conversion.
spring.http.converters.preferred-json-mapper=gson
```
That’s all you need to do to force Spring Boot to use Gson instead of Jackson.


####3. Configure GSON in Spring Boot
Now that your Spring Boot application is using Gson, you can configure Gson by specifying various properties in the application.properties file. The following properties are taken from Spring Boot Common Application Properties index page -
```
# GSON (GsonProperties)

# Format to use when serializing Date objects.
spring.gson.date-format= 

# Whether to disable the escaping of HTML characters such as '<', '>', etc.
spring.gson.disable-html-escaping= 

# Whether to exclude inner classes during serialization.
spring.gson.disable-inner-class-serialization= 

# Whether to enable serialization of complex map keys (i.e. non-primitives).
spring.gson.enable-complex-map-key-serialization= 

# Whether to exclude all fields from consideration for serialization or deserialization that do not have the "Expose" annotation.
spring.gson.exclude-fields-without-expose-annotation= 

# Naming policy that should be applied to an object's field during serialization and deserialization.
spring.gson.field-naming-policy= 

# Whether to generate non executable JSON by prefixing the output with some special text.
spring.gson.generate-non-executable-json= 

# Whether to be lenient about parsing JSON that doesn't conform to RFC 4627.
spring.gson.lenient= 

# Serialization policy for Long and long types.
spring.gson.long-serialization-policy= 

# Whether to output serialized JSON that fits in a page for pretty printing.
spring.gson.pretty-printing= 

# Whether to serialize null fields.
spring.gson.serialize-nulls= 
```

All the above properties are bound to a class called GsonProperties defined in Spring Boot. The GsonAutoConfiguration class uses these properties to configure Gson.

Excluding Jackson completely
If you want to get rid of Jackson completely then you can exclude it from spring-boot-starter-web dependency in the ```pom.xml``` file like so -

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
	<!-- Exclude the default Jackson dependency -->
	<exclusions>
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-json</artifactId>
		</exclusion>
	</exclusions>
</dependency>
```

## Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.2/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.2/gradle-plugin/reference/html/#build-image)

## Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

