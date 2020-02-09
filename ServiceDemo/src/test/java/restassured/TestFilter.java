package restassured;

import common.WeWork;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TestFilter {
    @Test
    public void testFilterDemo(){
        given()
                .queryParam("access_token", WeWork.getInstance().getToken())
                .queryParam("userid", "lisi")
                .filter(new filterDemo())
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
                .then().log().all()
                .statusCode(200);
    }

}
