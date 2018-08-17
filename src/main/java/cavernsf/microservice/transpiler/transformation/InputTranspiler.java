package cavernsf.microservice.transpiler.transformation;

import cavernsf.microservice.transpiler.definitions.Source;
import cavernsf.microservice.transpiler.definitions.Specification;
import cavernsf.microservice.transpiler.stream.FromSource;
import cavernsf.microservice.transpiler.stream.ParametersParser;
import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

public class InputTranspiler implements FromSource {
    private final Chainr chainr;

    public InputTranspiler(Specification specification) {
        chainr = ChainrUtils.from(specification);
    }

    @Override
    public ParametersParser from(Source source) {
        String jsonString = JsonUtils.toJsonString(chainr.transform(JsonUtils.jsonToObject(source.getValue())));
        return RequestParser.json(jsonString);
    }
}
