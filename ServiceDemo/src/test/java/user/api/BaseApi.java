package user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import framework.ApiObjectModel;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.HashMap;

public class BaseApi {
    ApiObjectModel apiObjectModel = new ApiObjectModel();
    HashMap<String, Object> params;


    public Response parseMethod(){
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        System.out.println(method);
        String path = "/" + this.getClass().getCanonicalName().replace('.', '/') + ".yaml";
        System.out.println(path);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            if (apiObjectModel.methods.entrySet() != null){
                apiObjectModel = mapper.readValue(this.getClass().getResourceAsStream(path), ApiObjectModel.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apiObjectModel.run(method, params);
    }

    public void setParams(HashMap<String, Object> params){
        this.params = params;
    }

}
