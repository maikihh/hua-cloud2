package department.testcase;

import department.api.Department;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TestDepartment {
    static Department department = new Department();

    @BeforeAll
    public static void beforeAll(){
        ArrayList<Integer> ids = department.list()
                .then().extract().body().path("department.findAll{d->d.parentid==" +department.defaultParentId+ "}.id");
        ids.forEach(id -> {
            department.delete(id);
        });
        System.out.println(ids);

    }

    @Test
    public void list(){
        //deparement.list(id).assertThat
       // System.out.println(department.getDepartmentList(2));

        department.list(3).then().body("errmsg", equalTo("ok"));

    }

    @Test
    public void create(){
        String name = "部门test2" + String.valueOf(System.currentTimeMillis());
        department.create(name).then().body("errcode", equalTo(0));
        department.list()
                .then().body("department.findAll{d->d.name=='" +name+ "'}.id", hasSize(1));
        //   department.list().then().body("errcode",equalTo(0));

    }

    @Test
    public void delete(){
       int id = department.create("部门3").then().body("errcode", equalTo(0))
               .extract().body().path("id");
        System.out.println(id);
        department.delete(id).then().body("errmsg", equalTo("deleted"));
    }

}
