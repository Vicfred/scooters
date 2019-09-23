# scooters api

## Instruction to run

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

#### use the included gradle wrapper to run the application
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
