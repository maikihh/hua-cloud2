package framework;

import io.restassured.response.Response;

import java.util.HashMap;

public class ApiObjectModel {
    public ApiObjectMethodModel getMethods(String method) {
        return methods.get(method);
    }

    public HashMap<String, ApiObjectMethodModel> methods = new HashMap<>();

    public Response run(String method){
        return  getMethods(method).run();
    }

    public Response run(String method, HashMap<String, Object> params){
        return getMethods(method).run(params);
    }



}
