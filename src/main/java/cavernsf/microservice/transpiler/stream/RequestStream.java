package cavernsf.microservice.transpiler.stream;

import cavernsf.microservice.transpiler.definitions.Specification;
import cavernsf.microservice.transpiler.transformation.InputTranspiler;

public class RequestStream {

    public static FromSource given(Specification spec) {
        return new InputTranspiler(spec);
    }

}
