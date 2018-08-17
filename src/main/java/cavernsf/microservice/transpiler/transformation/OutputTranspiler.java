package cavernsf.microservice.transpiler.transformation;


import cavernsf.microservice.transpiler.definitions.Source;
import cavernsf.microservice.transpiler.definitions.Specification;
import cavernsf.microservice.transpiler.stream.TranspileResult;

public class OutputTranspiler implements TranspileResult {

    private final Source result;

    public OutputTranspiler(Source result) {
        this.result = result;
    }

    @Override
    public String transpileResult(Specification specification) {
        return ChainrUtils.transform(this.result, specification);
    }
}
