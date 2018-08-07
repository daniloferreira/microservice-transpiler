import com.bazaarvoice.jolt.Chainr
import com.bazaarvoice.jolt.JsonUtils
import com.example.bpmpoc.HttpDelegate
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class HttpCallTest extends Specification {

    @Autowired
    private HttpDelegate delegate

    def 'should parse response'(){
        given: ''
        def input = JsonUtils.jsonToObject("""
             {
                  "_embedded": {
                    "my-special-list": [
                      {
                        "my-simple-field": 13,
                        "my-complex-field": {
                          "my-complex-field": {
                            "foo": "bar"
                          }
                        }
                      },
                      {
                        "my-simple-field": 15,
                        "not-mine-simple-field": 13,
                        "not-mine-complex-field": {
                          "not-mine-complex-field-2": {
                            "bar": "foot"
                          }
                        }
                      }
                    ]
                  }
}
        """)
        when: ''
        Chainr chainr = Chainr.fromSpec(JsonUtils
                .jsonToObject( """
                    [
                      {
                        "operation": "shift",
                        "spec": {
                          "_embedded": {
                            "my-special-list": {
                              "*": {
                                "my-simple-field": "ids.[]"
                              }
                            }
                          }
                        }
                      }
                    ]

                """.toString()))


        Object output = chainr.transform( input )
        then:  ''
        output.toString() == '[ids:[13, 15]]'
    }

}
