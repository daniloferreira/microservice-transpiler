package com.example.bpmpoc;

public class Response {

    Integer status;
    Object body;

    public Response(Integer status, Object body) {
        this.status = status;
        this.body = body;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
