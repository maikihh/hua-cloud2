package user.testcase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import user.api.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestUser {



    @Test
    public void get(){
        User user = new User();
        user.get("xiaozhan").then().body("name", equalTo("肖战"));
    }

    @Test
    public void update(){
        User user = new User();
        String userid = "lisi";
        String newName = "new_lisi";
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("name", newName);
        userData.put("address", "new_address");

        user.update(userid, userData);
        user.get(userid).then().body("name", equalTo(newName));
    }

    public void create(){
        User user = new User();
        String userid = "maiki" + System.currentTimeMillis();
        String name = "麦麦" + System.currentTimeMillis();
        String mobile = String.valueOf(System.currentTimeMillis()).substring(0,11);

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("userid", userid);
        userData.put("name", name);
        userData.put("department", 1);
        userData.put("mobile", mobile);
        user.create(userid, userData).then().body("errcode", equalTo(0));
    }

    @Test
    public void cloneUser() throws IOException {
        User user = new User();
        String currentTime = String.valueOf(System.currentTimeMillis());
        String userid = currentTime;

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("userid", userid);
        userData.put("name", "慧"+currentTime);
        userData.put("mobile", currentTime.substring(0, 11));

        user.clone(userid, userData).then().body("errmsg", equalTo("created"));
    }

    @Test
    public void delete(){
        User user = new User();
         String userid = "maiki" + System.currentTimeMillis();
        String name = "麦麦" + System.currentTimeMillis();
        String mobile = String.valueOf(System.currentTimeMillis()).substring(0,11);

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("userid", userid);
        userData.put("name", name);
        userData.put("department", 1);
        userData.put("mobile", mobile);
        user.create(userid, userData).then().body("errcode", equalTo(0));

        user.delete(userid).then().body("errcode", equalTo(0));
        user.get(userid).then().body("errcode", not(0));
    }

    @ParameterizedTest
    @CsvFileSource( resources = "TestUser.csv")
    public void deleteFromCsv(String userid, String name){
        User user = new User();
        if (userid == null){
            userid = "maiki" + System.currentTimeMillis();
        }
        //   String name = "麦麦" + System.currentTimeMillis();
        String mobile = String.valueOf(System.currentTimeMillis()).substring(0,11);

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("userid", userid);
        userData.put("name", name);
        userData.put("department", 1);
        userData.put("mobile", mobile);
        user.create(userid, userData).then().body("errcode", equalTo(0));

        user.delete(userid).then().body("errcode", equalTo(0));
        user.get(userid).then().body("errcode", not(0));
    }

    @ParameterizedTest
    @MethodSource("deleteFromYamlData")
    public void deleteFromYaml(String userid, String name, List<Integer> departments){
        User user = new User();
        if (userid == null){
            userid = "maiki" + System.currentTimeMillis();
        }
        if (departments == null){
            departments = Arrays.asList(1);
        }
        //   String name = "麦麦" + System.currentTimeMillis();
        String mobile = String.valueOf(System.currentTimeMillis()).substring(0,11);

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("userid", userid);
        userData.put("name", name);
        userData.put("department", 1);
        userData.put("mobile", mobile);
        user.create(userid, userData).then().body("errcode", equalTo(0));
        user.delete(userid).then().body("errcode", equalTo(0));
        user.get(userid).then().body("errcode", not(0));
    }

    static Stream<Arguments> deleteFromYamlData(){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>() {};
        List<HashMap<String, Object>> data;
        try {
            data = mapper.readValue(
                    TestUser.class.getResourceAsStream("TestUser.yaml"),
                    typeRef);
            ArrayList<Arguments> results = new ArrayList<>();
            data.forEach(map -> {
                results.add(arguments(
                        map.get("name").toString(),
                        map.get("userid").toString(),
                        map.get("departments")
                ));
            });
            return results.stream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.of();
    }
}
