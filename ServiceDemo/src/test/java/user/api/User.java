package user.api;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import common.WeWork;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class User {

    public Response get(String userid) {
        return given()
                .queryParam("access_token", WeWork.getInstance().getToken())
                .queryParam("userid", userid)
                .when()
                .log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
                .then()
                .log().all()
                .extract().response();
    }

    public Response update(String userid, HashMap<String, Object> userData) {
        userData.put("userid", userid);
        return given()
                .queryParam("access_token", WeWork.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(userData)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/update")
                .then().log().all().extract().response();
    }

    public Response create(String userid, HashMap<String, Object> userData) {
        userData.put("userid", userid);
        return given()
                .queryParam("access_token", WeWork.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(userData)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();
    }

    public Response clone(String userid, HashMap<String, Object> userData) {
        userData.put("userid", userid);
        String body = template("/user/api/user.json", userData);
        return given()
                .queryParam("access_token", WeWork.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(body)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();
    }

    public String template(String templatePath, HashMap<String, Object> userData) {
        Writer writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(this.getClass().getResource(templatePath).getPath());
        //   Mustache mustache = mf.compile("/Users/hzl-mac/Desktop/HH/003IdeaProjects/ServiceDemo/src/main/resources/user.api/user.json");
        mustache.execute(writer, userData);
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public Response delete(String userid) {
        return given()
                .queryParam("access_token", WeWork.getInstance().getToken())
                .queryParam("userid", userid)
                .when()
                .log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/delete")
                .then()
                .extract().response();
    }
}
