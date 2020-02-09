package testcase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import framework.PageObjectMethod;
import org.junit.Test;
import page.App;
import page.BasePage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Demo {


    @Test
    public void demo1() throws IOException {
        //jackson库的使用 https://github.com/FasterXML/jackson-dataformats-text
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        //1. getResource()获取当前的resources目录。 2. Object[][].class：文件流读取出来是个二维数组
        Object[][] demo = mapper.readValue(this.getClass().getResourceAsStream("/page/demo.yaml"), Object[][].class);
        System.out.println(demo);
        System.out.println(demo[0][0]);
    }

    @Test
    public void demo2(){
        System.out.println(this.getClass().getResource("/"));
        System.out.println(this.getClass().getResource("/page/SearchPage.yaml"));
        System.out.println(this.getClass().getCanonicalName());
    }

/*    @Test
    public void demo3() throws JsonProcessingException {
        HashMap<String, PageObjectMethod> testcase = new HashMap<>();

        PageObjectMethod pageObjectStep = new PageObjectMethod();
        List<HashMap<String, String>> steps = new ArrayList<>();
        HashMap<String, String> step = new HashMap<>();
        step.put("id", "xxxx");
        step.put("send", "tengxun");
        steps.add(step);
        steps.add(step);

        pageObjectStep.setTestCaseSteps(steps);

        testcase.put("search", pageObjectStep);

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(testcase));

    }*/

    @Test
    public void demo4() throws IOException, InterruptedException {
        App.startUp();
        App app = new App();
        app.toSearchPage();



    }
}
