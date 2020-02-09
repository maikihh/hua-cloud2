package page;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import framework.PageObjectMethod;
import framework.PageObjectModel;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasePage {
    public static AndroidDriver<WebElement> driver;
    private PageObjectModel pageObjectModel = new PageObjectModel();

     //测试步骤参数化
    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public static HashMap<String, Object> getParams() {
        return params;
    }

    private static HashMap<String, Object> params=new HashMap<>();

    //测试步骤结果读取
    public static HashMap<String, Object> getResults() {
        return results;
    }

    private static HashMap<String, Object> results=new HashMap<>();

   /* public BasePage(){
        model.engine="appium";
    }*/



    public static WebElement findElement(By by) {
        try {
            System.out.println(by);
            return driver.findElement(by);
        } catch (Exception e) {
            handleAlert();
            return driver.findElement(by);
        }
    }

    public static void findElementAndClick(By by) {
        try {
            System.out.println(by);
            driver.findElement(by).click();
        } catch (Exception e) {
            handleAlert();
            driver.findElement(by).click();
            ;
        }
    }

    public static void handleAlert() {
        List<By> alertBox = new ArrayList<>();
        alertBox.add(By.id("tv_skip"));
        alertBox.forEach(alert -> {
            List<WebElement> ads = driver.findElements(alert);
            if (ads.size() > 0) {
                ads.get(0).click();
            }
        });
    }

    public static List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public void parseSteps() {
       // String method = Thread.currentThread().getStackTrace().getMethodName();
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        System.out.println(method);
        parseSteps(method);
    }

    public void parseSteps(String method) {
        String path = "/" + this.getClass().getCanonicalName().replace('.', '/') + ".yaml";
        System.out.println(path);
        parseSteps(path, method);

    }

    public void parseSteps(String path, String method) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        //  String path = "/" + this.getClass().getCanonicalName().replace('.', '/') + ".yaml";
        try {
            pageObjectModel = mapper.readValue(this.getClass().getResourceAsStream(path), PageObjectModel.class);
            parseSteps(pageObjectModel.methods.get(method));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseSteps(PageObjectMethod steps){
         /*  TypeReference<HashMap<String, PageObjectMethod>> typeReference = new TypeReference<HashMap<String, PageObjectMethod>>(){};
        HashMap<String, PageObjectMethod> TestCaseSteps = mapper.readValue(this.getClass().getResourceAsStream(path), typeReference);*/
        steps.getSteps().forEach(step -> {
                    WebElement element = null;
                    //可能出现的多种定位
                    String id = step.get("id");
                    if (id != null) {
                        element = findElement(By.id(id));
                    }
                    String xpath = step.get("xpath");
                    if (xpath != null) {
                        element = findElement(By.xpath(xpath));
                    }
                    String aid = step.get("aid");
                    if (aid != null) {
                        element = findElement(MobileBy.AccessibilityId(aid));
                    }

                    // 没有其它操作，默认点击元素
                    String send = step.get("send");
                    if (send != null) {
                        for(Map.Entry<String, Object> kw: params.entrySet()){
                            String match = "${" + kw.getKey() + "}";
                            if(send.contains(match)){
                                System.out.println(kw);
                                send = send.replace(match, kw.getValue().toString());
                            }
                        }
                        element.sendKeys(send);
                    } else if (step.get("get") != null) {
                        String attribute = element.getAttribute(step.get("get"));
                        getResults().put(step.get("dump"), attribute);
                    } else {
                        element.click();
                    }
                }
        );
    }


}
