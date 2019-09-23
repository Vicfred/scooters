package net.lagmental.scooters

import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(
        classes = ScootersApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles(value = "test")
@ContextConfiguration
class ScooterControllerTest extends Specification {
    @Shared
    RESTClient restClient = new RESTClient("http://localhost:8080")

    def 'User should be able to perform create request'() {
        given:
        def requestBody = [scooterId: 'ZYXW', email: 'vicfred.test@gmail.com']

        when:
        def response = restClient.post(path: '/create', body: requestBody, requestContentType: 'application/json')

        then:
        response.status == 200
        response.responseData["scooterId"] == "ZYXW"
        response.responseData["result"] == "CREATED"
    }
}
