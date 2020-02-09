package testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page.App;
import page.SearchPage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


@RunWith(Parameterized.class)
public class TestSearch {
    public static SearchPage searchPage;

    @BeforeClass
    public static void beforeAll() throws MalformedURLException, InterruptedException {
        App.startUp();
    }

    @Before
    public void beforeMethod() throws IOException {
        searchPage = App.getInstance().toSearchPage();
        System.out.println("toSearch");

    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws IOException {
     /*   return Arrays.asList(new Object[][] {
                { "tengxun", 300f},
                { "jingdong", 30f},
                { "alibaba", 200f}
        });*/
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String path = "/" + TestSearch.class.getCanonicalName().replace('.', '/')+".yaml";
        Object[][] stockData = mapper.readValue(TestSearch.class.getResourceAsStream(path), Object[][].class);
        return Arrays.asList(stockData);
    }

    @Parameterized.Parameter(0)
    public String stockName;

    @Parameterized.Parameter(1)
    public Double stockPrice;

    @Test
    public void search() throws IOException {
        assertThat(searchPage.search(stockName).getCurrentPrice(), greaterThanOrEqualTo(stockPrice));
    }




    @After
    public void afterMethod(){
        searchPage.backSearchPage();
        searchPage.backAppPage();
    }

    @AfterClass
    public static void afterAll (){
      //  App.driver.quit();
    }
}
