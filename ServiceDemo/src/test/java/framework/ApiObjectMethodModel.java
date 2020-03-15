package framework;

import common.WeWork;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ApiObjectMethodModel {
    public HashMap<String, Object> params;
  //  public HashMap<String, Object> query;
    public HashMap<String, Object> header;
    public HashMap<String, Object> postBody;
    public String postBodyRaw;
    public String method = "get";
    public String url = "";

    public Response run(){
        RequestSpecification request = given();
        request.queryParam("access_token", WeWork.getInstance().getToken());

        if (params !=null){
            params.entrySet().forEach(entrySet -> {
                request.queryParam(entrySet.getKey(), entrySet.getValue().toString());
            });
        }
        if (header != null){
            header.entrySet().forEach(entrySet -> {
                request.header(entrySet.getKey(), entrySet.getValue().toString());
            });
        }
        if (postBody != null){
            request.body(postBody);
        }
        if (postBodyRaw != null){
            request.body(postBodyRaw);
        }

        return request
                .when().log().all().request(method, url)
                .then().log().all().extract().response();
    }

/*    public String replace(String raw){
        for(Map.Entry<String, Object> kv : params.entrySet()){
            String matcher = "${" + kv.getKey() + "}";
            if (raw.contains(matcher)){
                raw = raw.replace(matcher, kv.getValue().toString());
            }

        }
        return raw;
    }*/

 /*   public Response run(HashMap<String, Object> params){
        this.params = params;
        return run();
    }*/

}
