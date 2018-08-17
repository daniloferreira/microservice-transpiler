import cavernsf.microservice.transpiler.http_client.MethodFactory
import org.apache.http.client.methods.HttpGet
import spock.lang.Specification

class MethodFactoryTest extends Specification {


    def 'Should accept null method name'() {
        given: 'a call without method name'
        def methodName = null
        and: 'valid uri'
        def uri = "http://localhost"
        when: 'method factory is called'
        def httpMethod = MethodFactory.from(Optional.ofNullable(methodName), uri)
        then: 'not exception are thrown'
        notThrown Exception
        and: 'the method will be GET'
        httpMethod instanceof HttpGet
    }

    def 'Should accept empty method name'() {
        given: 'a call with empty method name'
        def methodName = ""
        and: 'valid uri'
        def uri = "http://localhost"
        when: 'method factory is called'
        def httpMethod = MethodFactory.from(Optional.ofNullable(methodName), uri)
        then: 'not exception are thrown'
        notThrown Exception
        and: 'the method will be GET'
        httpMethod instanceof HttpGet
    }

    def 'Should accept invalid method name'() {
        given: 'a call with empty method name'
        def methodName = ""
        and: 'valid uri'
        def uri = "http://localhost"
        when: 'method factory is called'
        def httpMethod = MethodFactory.from(Optional.ofNullable(methodName), uri)
        then: 'not exception are thrown'
        notThrown Exception
        and: 'the method will be GET'
        httpMethod instanceof HttpGet
    }

    def 'Should throw error with null uri'() {
        given: 'a call with a valid method name'
        def methodName = "GET"
        and: ' null uri'
        def uri = null
        when: 'method factory is called'
        MethodFactory.from(Optional.ofNullable(methodName), uri)
        then: 'exception are thrown'
        thrown Exception
    }
}
