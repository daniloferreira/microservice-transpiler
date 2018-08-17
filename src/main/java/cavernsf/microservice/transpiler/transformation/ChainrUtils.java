package cavernsf.microservice.transpiler.transformation;

import cavernsf.microservice.transpiler.definitions.Source;
import cavernsf.microservice.transpiler.definitions.Specification;
import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

public class ChainrUtils {

    public static Chainr from(Specification specification) {
        return Chainr.fromSpec(JsonUtils.jsonToObject(specification.getValue()));
    }

    public static String transform(Source source, Specification specification) {
        return JsonUtils.toJsonString(from(specification).transform(JsonUtils.jsonToObject(source.getValue())));
    }
}
