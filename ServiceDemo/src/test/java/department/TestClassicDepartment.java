package department;

import io.restassured.http.ContentType;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestClassicDepartment {
    static String token;

    public void getToken() {
        token = given()
                .param("corpid", "wwa3a4c9ecbda6afc4")
                .param("corpsecret", "WJJbgmw_1Bojpmu93HkFKFS3gUVyqxm5itMxKeRFFok")
        .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
        .then().log().all()
                .body("errcode", equalTo(0))
                .extract().path("access_token");
          System.out.println(token);
    }



    public void getDepartmentList(){
        given()
                .queryParam("access_token", token)
                .contentType(ContentType.JSON)
                .param("id", 2)
        .when()
                .log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
        .then()
                .log().all()
                .body("errcode", equalTo(0));
    }


    public void departmentCreate(String name, int parentId){
        HashMap<String, Object> departData = new HashMap<>();
        departData.put("name", name);
        departData.put("parentid", parentId);
         given()
                .queryParam("access_token", token)
                .contentType(ContentType.JSON)
                .body(departData)
         .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
         .then()
                .log().all()
                .body("errcode", equalTo(0));

    }




}
