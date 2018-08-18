# microservice-transpiler

Mechanism for transform json objects between  http calls

  
        given: 'current json'
        def source = """
                {
                  "status": "notok",
                  "payload": {
                    "name": "joe",
                    "_links": {
                      "go": {
                        "href": "http://localhost:2345/test"
                      }
                    }
                  }
                }
            """
        and: 'transformation input specification'
        def inputSpec = """
                [
                  {
                    "operation": "shift",
                    "spec": {
                      "payload": {
                        "_links": {
                          "go": {
                            "@href": "url"
                          }
                        },
                        "name": "body.name"
                      }
                    }
                  },
                  {
                    "operation": "default",
                    "spec": {
                      "headers": {
                        "Content-Type": "application/json"
                      },
                      "method": "GET"
                    }
                  }
                ]
            """
        and: 'transformation output specification'
        def ouputSpec = """
                [
                  {
                    "operation": "shift",
                    "spec": {
                      "status": {
                        "success": {
                          "#ok": "result"
                        },
                        "*": {
                          "#fail": "result"
                        }
                      }
                    }
                  }
                ]
            """
        and: 'test service works'
        stubFor(get(urlEqualTo("/test")).withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                {
                  "status": "success",
                  "payload": {
                    "name": "joe",
                    "_links": {
                      "go": {
                        "href": "http://localhost:2345/test"
                      }
                    }
                  }
                }
                """)))

        when: ''
        def result = RequestStream
                .given(new cavernsf.microservice.transpiler.definitions.Specification(inputSpec))
                .from(new Source(source))
                .parse()
                .execute()
                .transpileResult(new cavernsf.microservice.transpiler.definitions.Specification(ouputSpec))
        then:' result is'
        result == '{"result":"ok"}'
                
