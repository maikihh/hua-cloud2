package department.api;
import common.WeWork;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

public class Department {

    public Integer defaultParentId = 4;

    public Response list(int parentId){
        return given()
                .queryParam("access_token", WeWork.getInstance().getToken())
                .contentType(ContentType.JSON)
                .param("id", parentId)
        .when()
                .log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
        .then()
                .log().all()
                .extract().response();
             //   .body("errcode", equalTo(0));
    }

    public Response list(){
        return list(defaultParentId);
    }

    public Response create(String name, int parentId){
        HashMap<String, Object> departmentData = new HashMap<>();
        departmentData.put("name", name);
        departmentData.put("parentid", parentId);
        return given()
                .queryParam("access_token", WeWork.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(departmentData)
        .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
        .then()
                .log().all()
                .extract().response();
            //    .body("errcode", equalTo(0));
    }

    public Response create(String name){
        return create(name, defaultParentId);
    }

    public Response delete(int id){
        return given()
                .queryParam("access_token", WeWork.getInstance().getToken())
                .queryParam("id", id)
        .when()
                .log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
        .then()
                .log().all()
                .extract().response();
    }
}
