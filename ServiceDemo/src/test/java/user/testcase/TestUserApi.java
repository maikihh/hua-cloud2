package user.testcase;

import org.junit.jupiter.api.Test;
import user.api.UserApi;

import static org.hamcrest.Matchers.*;

public class TestUserApi {

    @Test
    public void get(){
        UserApi userApi = new UserApi();
        userApi.get().then().body("errcode", equalTo(0));
    }

    @Test
    public void create(){
        UserApi userApi = new UserApi();
        userApi.create().then().body("errcode", equalTo(0));
    }

    @Test
    public void update(){
        UserApi userApi = new UserApi();
        userApi.update().then().body("errcode", equalTo(0));
    }

    @Test
    public void delete(){
        UserApi userApi = new UserApi();
        userApi.delete().then().body("errcode", equalTo(0));
    }

    @Test
    public void batchdelete(){
        UserApi userApi = new UserApi();
        userApi.batchdelete().then().body("errcode", equalTo(0));
    }

    @Test
    public void simplelist(){
        UserApi userApi = new UserApi();
        userApi.simplelist().then().body("errcode", equalTo(0));
    }

    @Test
    public void list(){
        UserApi userApi = new UserApi();
        userApi.list().then().body("errcode", equalTo(0));
    }
  /*  @ParameterizedTest
    @MethodSource("getFromYamlData")
    public void getFromYaml(String userid){
        UserApi user = new UserApi();
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("userid", userid);
        user.get(userid).then().body("errcode", equalTo(0));
    }

    static Stream<Arguments> getFromYamlData(){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>() {};
        List<HashMap<String, Object>> data;
        try {
            data = mapper.readValue(
                    TestUserApi.class.getResourceAsStream("TestUserApi.yaml"),
                    typeRef);
            ArrayList<Arguments> results = new ArrayList<>();
            data.forEach(map -> {
                results.add(arguments(
                        //map.get("name").toString(),
                        map.get("userid").toString()
                       // map.get("departments")
                ));
            });
            return results.stream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.of();
    }
*/
}
