package department;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestClassicDepartment {
    static String token;

    @Test
    public void testJson(){
        given()
                .when().get("http://192.168.80.128:8000/test.json")
                .then()
                .body("store.book.category", hasItems("reference"))
                .body("store.book[0].author", equalTo("Nigel Rees"))
                .body("store.book.findAll { book -> book.price >= 5 && book.price <= 10}.author", hasItems("Nigel Rees","Herman Melville"))
                .body("store.book.find { book -> book.price == 8.95f }.author", equalTo("Nigel Rees"));
    }

    @Test
    public void testXml(){
        given()
                .when().get("http://192.168.80.128:8000/test.xml")
                .then()
                .body("shopping.category.item[0].name", equalTo("Chocolate"))
                .body("shopping.category[2].item.size()", equalTo(1))
                .body("shopping.category[1].item[1].name", equalTo("Pens"))
                .body("shopping.category.findAll { it.@type == 'groceries' }.size()", equalTo(1))
                .body("shopping.category.item.findAll { item -> def price = item.price.toFloat(); price >= 0 && price <= 10 }.name", hasItems("Chocolate", "Paper"))
                .body("**.find { it.name == 'Chocolate' }.price", equalTo("10"))
        ;
    }




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
