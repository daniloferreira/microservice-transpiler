package com.example.bpmpoc;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class HttpDelegate {

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        });
        this.restTemplate = restTemplate;
    }

    public String execute(DelegateExecution execution, String url, String method, String headers) {
//        ExpressionFactory factory = new ExpressionFactoryImpl();
//        ELContext cachedElContext = ((VariableScopeImpl) execution).getCachedElContext();
//
//        ValueExpression expr = factory.createValueExpression(cachedElContext, "${}", String.class);
//        expr.setValue(cachedElContext, execution);

        Map<String, Object> stringObjectMap = JsonUtils.jsonToMap(headers);
        LinkedMultiValueMap<String, String> headersMap = new LinkedMultiValueMap<>();
        stringObjectMap.entrySet().stream().forEach(item -> {
            headersMap.add(item.getKey(), item.getValue().toString());
        });
        ResponseEntity<Object> exchange = restTemplate.exchange(url, HttpMethod.resolve(method), new HttpEntity<>("", headersMap), Object.class);
        Optional<FieldExtension> responseJolt = ((ServiceTask) execution.getCurrentFlowElement()).getFieldExtensions().stream().filter(field -> field.getFieldName().equals("responseJolt")).findAny();
        if(responseJolt.isPresent()) {
            Response response = new Response(exchange.getStatusCodeValue(), exchange.getBody());
            Chainr chainr = Chainr.fromSpec(JsonUtils.jsonToObject(responseJolt.get().getStringValue()));
            Object transform = chainr.transform(JsonUtils.jsonToObject(JsonUtils.toJsonString(response)));
            return JsonUtils.toJsonString(transform);
        }
        return String.valueOf(exchange.getStatusCodeValue());
    }

    public int jsonPathResults(String json, String expression) {
        JSONArray result = JsonPath.compile(expression).read(json);
        return result.size();
    }
}
