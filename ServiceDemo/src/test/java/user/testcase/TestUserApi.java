package user.testcase;

import org.junit.jupiter.api.Test;
import user.api.UserApi;

import static org.hamcrest.Matchers.equalTo;

public class TestUserApi {

    @Test
    public void get(){
        UserApi userApi = new UserApi();
        userApi.get("lisi").then().body("errcode", equalTo(0));
    }



}
