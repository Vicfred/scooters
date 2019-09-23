# scooters api

##
The main business logic of the code is in [ScooterController.java](src/main/java/net/lagmental/scooters/ScooterController.java)

The code for the tests is in [ScooterControllerTest.groovy](src/test/groovy/net/lagmental/scooters/ScooterControllerTest.groovy)

## Instructions to run

### Requirements
- Java 8+

####
Check if you have the correct java version (tested on java 8 and java 11):
```
java -version
```
![javaversion](https://raw.githubusercontent.com/Vicfred/scooters/master/img/javaversion.png "Java Version")

#### clone the repo and cd to it:
```
git clone https://github.com/Vicfred/scooters.git
cd scooters
```

#### use the included gradle wrapper to run the application (this can take some time depending on your internet)
```
./gradlew bootrun
```
![spring](https://raw.githubusercontent.com/Vicfred/scooters/master/img/spring.png "Spring")

the application will now run on port 8080

![postman](https://raw.githubusercontent.com/Vicfred/scooters/master/img/postman.png "Postman")

#### you can run the tests with
```
./gradlew test
```

![testresults](https://raw.githubusercontent.com/Vicfred/scooters/master/img/testresults.png "Test Results")
