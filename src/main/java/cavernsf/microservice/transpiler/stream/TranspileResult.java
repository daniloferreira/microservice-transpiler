package cavernsf.microservice.transpiler.stream;

import cavernsf.microservice.transpiler.definitions.Specification;

public interface TranspileResult {
        String transpileResult(Specification spec);
}