package net.lagmental.scooters

import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(
        classes = ScootersApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles(value = "test")
@ContextConfiguration
class ScooterControllerTest extends Specification {
    RESTClient restClient = new RESTClient("http://localhost:8080")
    def testid = 'ZYXW'
    def testmail = 'vicfred.test@lagmental.net'
    def requestBody = [scooterId: testid, email: testmail]

    def 'User should be able to perform create request'() {
        when:
        def response = restClient.post(path: '/create', body: requestBody, requestContentType: 'application/json')

        then:
        200         == response.status
        "ZYXW"      == response.responseData["scooterId"]
        "CREATED"   == response.responseData["result"]

        cleanup:
        deleteTestScooter(testid)
    }

    def 'User should be able to change from a created to maintenance'() {
        given:
        restClient.post(path: '/create', body: requestBody, requestContentType: 'application/json')

        when:
        def response = restClient.post(path: '/setup', body: requestBody, requestContentType: 'application/json')

        then:
        200             == response.status
        "ZYXW"          == response.responseData["scooterId"]
        "MAINTENANCE"   == response.responseData["result"]

        cleanup:
        deleteTestScooter(testid)
    }

    def 'User should NOT be able to change directly from a state to another'() {
        given:
        restClient.post(path: '/create', body: requestBody, requestContentType: 'application/json')

        when:
        def response = restClient.post(path: '/placed', body: requestBody, requestContentType: 'application/json')

        then:
        200                 == response.status
        "INVALID STATUS"    == response.responseData["result"]

        cleanup:
        deleteTestScooter(testid)
    }

    def 'User should be able to change from maintenance to distributing'() {
        given:
        restClient.post(path: '/create', body: requestBody, requestContentType: 'application/json')
        restClient.post(path: '/setup', body: requestBody, requestContentType: 'application/json')

        when:
        def response = restClient.post(path: '/moving', body: requestBody, requestContentType: 'application/json')

        then:
        200             == response.status
        "ZYXW"          == response.responseData["scooterId"]
        "DISTRIBUTING"  == response.responseData["result"]

        cleanup:
        deleteTestScooter(testid)
    }

    def 'User should be able to change from distributing to on street'() {
        given:
        restClient.post(path: '/create', body: requestBody, requestContentType: 'application/json')
        restClient.post(path: '/setup', body: requestBody, requestContentType: 'application/json')
        restClient.post(path: '/moving', body: requestBody, requestContentType: 'application/json')

        when:
        def response = restClient.post(path: '/placed', body: requestBody, requestContentType: 'application/json')

        then:
        200         == response.status
        "ZYXW"      == response.responseData["scooterId"]
        "ONSTREET"  == response.responseData["result"]

        cleanup:
        deleteTestScooter(testid)
    }

    def 'User should be able to change from on street to maintenance'() {
        given:
        restClient.post(path: '/create', body: requestBody, requestContentType: 'application/json')
        restClient.post(path: '/setup', body: requestBody, requestContentType: 'application/json')
        restClient.post(path: '/moving', body: requestBody, requestContentType: 'application/json')
        restClient.post(path: '/placed', body: requestBody, requestContentType: 'application/json')

        when:
        def response = restClient.post(path: '/repair', body: requestBody, requestContentType: 'application/json')

        then:
        200         == response.status
        "ZYXW"      == response.responseData["scooterId"]
        "MAINTENANCE"  == response.responseData["result"]

        cleanup:
        deleteTestScooter(testid)
    }

    def 'User should be able to change from on street to distributing'() {
        given:
        restClient.post(path: '/create', body: requestBody, requestContentType: 'application/json')
        restClient.post(path: '/setup', body: requestBody, requestContentType: 'application/json')
        restClient.post(path: '/moving', body: requestBody, requestContentType: 'application/json')
        restClient.post(path: '/placed', body: requestBody, requestContentType: 'application/json')

        when:
        def response = restClient.post(path: '/movefromstreet', body: requestBody, requestContentType: 'application/json')

        then:
        200         == response.status
        "ZYXW"      == response.responseData["scooterId"]
        "DISTRIBUTING"  == response.responseData["result"]

        cleanup:
        deleteTestScooter(testid)
    }

    def deleteTestScooter(scooterId) {
        return restClient.delete(path: '/delete/'+scooterId)
    }
}
