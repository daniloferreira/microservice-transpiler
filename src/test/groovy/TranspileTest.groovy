import cavernsf.microservice.transpiler.definitions.Source
import cavernsf.microservice.transpiler.stream.Transpile
import spock.lang.Specification

class TranspileTest extends Specification{

    def 'should transform json according with specification'() {
        given: 'a valid json'
        def source = """{"embedded": { "cars": [{"id": "123456", "brand": "new"}, {"id": "dffsdfdsf", "brand": "old"}] }}"""
        and: "a valid jolt specification"
        def specification = """
            [
              {
                "operation": "shift",
                "spec": {
                  "embedded": {
                    "cars": {
                      "*": {
                        "brand": "brands.[]"
                      }
                    }
                  }
                }
              }]
        """
        when: 'transpile is called'
        def output = new Transpile(new cavernsf.microservice.transpiler.definitions.Specification(specification)).using(new Source(source))
        then: 'ouput is the expected'
        output == """{"brands":["new","old"]}"""
    }
}
