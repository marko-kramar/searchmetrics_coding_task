Coding task solution
===============
> A REST service developed in Spring Boot, that constantly checks latest and historical Bitcoin rates in USD
-------------
- [Live demo testing tool](http://markokramar.com:9090/codingtask)

Service has two endpoints that return corresponding JSON:
-------------
1. [/codingtask/latest](http://markokramar.com:9090/codingtask/latest)

Retrieves latest available Bitcoin rate.

2. [/codingtask/historical](http://markokramar.com:9090/codingtask/historical?startDate=2020-01-01&endDate=2020-02-02)

Retrieves historical Bitcoin rates - `startDate` and `endDate` request parameters are mandatory! Date format for parameters is `yyyy-mm-dd`.

Example: `http://your-domain.com/codingtask/historical?startDate=2020-01-01&endDate=2020-02-01`

Scheduled job
-------------

Service has scheduled job that constantly checks and retrieves latest rates in the background. Check period (in miliseconds) can be configured in `application.properties` file:

```properties
bitcoin.price.check.period.ms=5000
```

Usage
-------------
As any Spring Boot Maven project, it can be imported to Eclipse/IntelliJ and you can run it from there. Also, if you want, you can run it from command line.

You can run the application with `./mvnw spring-boot:run` command. Also, if you want to build an executable JAR, you can use `./mvnw clean package`, and then:

```sh
java -jar codingtask.jar
```
