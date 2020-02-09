package page;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.io.IOException;
import java.util.HashMap;

import static java.lang.String.valueOf;

public class SearchPage extends BasePage {
    private By searchBox = By.xpath("//android.widget.EditText");
    private By backSearch = By.id("action_back");
    private By backApp = By.id("SnowBall, Navigate up");

    public SearchPage search(String keyword) throws IOException {
        /*findElement(searchBox).sendKeys(keyword);
        findElementAndClick(By.id("stockName"));*/
        HashMap<String, Object> data = new HashMap<>();
        data.put("keyword", keyword);
        setParams(data);
        parseSteps();
        return this;
    }

    public Double getCurrentPrice() {
       // MobileElement currentPrice = (MobileElement) findElement(By.id("stock_current_price"));
        parseSteps();
        return Double.valueOf(getResults().get("price").toString());
    }

    public SearchPage backSearchPage() {
      //  findElementAndClick(backSearch);
        parseSteps();
        return this;
    }

    public App backAppPage() {
      //  findElementAndClick(backApp);
        parseSteps();
        return new App();
    }


}
