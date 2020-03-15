package user.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class UserApi extends BaseApi{
/*   @Test
    public Response get(String userid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", userid);
        setParams(params);
        return parseMethod();
    }*/

    @Test
    public Response gettoken() {
        return parseMethod();
    }

    @Test
    public Response get() {
        return parseMethod();
    }

    @Test
    public Response create() {
        return parseMethod();
    }

    @Test
    public Response update() {
        return parseMethod();
    }

    @Test
    public Response delete() {
        return parseMethod();
    }

    @Test
    public Response batchdelete() {
        return parseMethod();
    }

    @Test
    public Response simplelist() {
        return parseMethod();
    }

    @Test
    public Response list() {
        return parseMethod();
    }

}
