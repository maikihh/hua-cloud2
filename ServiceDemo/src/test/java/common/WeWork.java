package common;

import static io.restassured.RestAssured.given;

public class WeWork {
    public static String token;
  //  public static Integer parentId = 2;

    public static WeWork weWork;
    public static WeWork getInstance(){
        if(weWork == null){
            weWork = new WeWork();
        }
        return weWork;
    }

    public String getToken() {
        token = given()
                .param("corpid", "wwa3a4c9ecbda6afc4")
                .param("corpsecret", "WJJbgmw_1Bojpmu93HkFKFq9Ol4W86wMgcrxW-rbelU")
        .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
        .then().log().all()
             //   .body("errcode", equalTo(0))
                .extract().path("access_token");
     //   System.out.println(token);
        return token;
    }





}
