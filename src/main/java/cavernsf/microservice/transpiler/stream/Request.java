package cavernsf.microservice.transpiler.stream;

import java.util.Map;

public interface Request {


    TranspileResult execute() throws Exception;

    String getUrl();

    String getMethod();

    Map<String, String> getHeaders();

    Object getBody();
}
