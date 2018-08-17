package cavernsf.microservice.transpiler.stream;

import cavernsf.microservice.transpiler.definitions.Source;

public interface FromSource {
        ParametersParser from(Source source);
}