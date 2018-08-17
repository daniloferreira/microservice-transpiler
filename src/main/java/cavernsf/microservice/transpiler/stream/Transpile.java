package cavernsf.microservice.transpiler.stream;

import cavernsf.microservice.transpiler.definitions.Source;
import cavernsf.microservice.transpiler.definitions.Specification;
import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

public class Transpile {

    private Specification specification;


    public Transpile(Specification specification) {
        this.specification = specification;
    }

    public Chainr given(Specification specification) {
        return Chainr.fromSpec(JsonUtils.jsonToObject(specification.getValue()));
    }

    public String using(Source source) {
        return JsonUtils.toJsonString(given(this.specification)
                .transform(JsonUtils.jsonToObject(source.getValue())));
    }


}
