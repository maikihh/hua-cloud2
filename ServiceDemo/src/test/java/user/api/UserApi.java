package user.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class UserApi extends BaseApi{

    @Test
    public Response get(String userid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", userid);
        setParams(params);
        return parseMethod();
    }

}
