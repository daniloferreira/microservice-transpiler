package cavernsf.microservice.transpiler.http_client;

import org.apache.http.client.methods.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MethodFactory {


    private static Map<String, Class<? extends HttpRequestBase>> supportedMethod = new HashMap<>(5);

    static {
        supportedMethod.put("GET", HttpGet.class);
        supportedMethod.put("POST", HttpPost.class);
        supportedMethod.put("HEAD", HttpHead.class);
        supportedMethod.put("DELETE", HttpDelete.class);
        supportedMethod.put("PUT", HttpPut.class);
    }

    public static <T extends HttpRequestBase> T from(Optional<String> methodName, String uri) throws Exception {
        Class<? extends HttpRequestBase> clazz = supportedMethod.getOrDefault(methodName.orElse("GET").toUpperCase(),
                HttpGet.class);
        Constructor<?> constructor = clazz.getConstructor(String.class);
        return (T) constructor.newInstance(uri);
    }

}
