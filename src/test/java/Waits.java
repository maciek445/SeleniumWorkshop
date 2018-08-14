import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Waits {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.navigate().to("http://automationpractice.com/index.php");
    }

    @Test
    public void implicitWaitTest() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.cssSelector("[class='search_query form-control ac_input']")).clear();
        driver.findElement(By.cssSelector("[class='search_query form-control ac_input']")).sendKeys("Printed Dress");
        driver.findElement(By.cssSelector("button[name='submit_search']")).click();
        driver.findElement(By.cssSelector("#center_column > ul > li:nth-child(2) > div > div.left-block > div > a.product_img_link > img")).click();
        driver.switchTo().frame(driver.findElement(By.className("fancybox-iframe")));
        driver.findElement(By.xpath("//*[@id=\"add_to_cart\"]/button")).click();

        String message = driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2")).getText();
        Assert.assertEquals("Product successfully added to your shopping cart", message);
    }

    @Test
    public void ExplicitWaitTest() {
        WebElement searchInput = driver.findElement(By.cssSelector("[class='search_query form-control ac_input']"));
        searchInput.clear();
        searchInput.sendKeys("Printed Dress");

        WebElement searchButton = driver.findElement(By.cssSelector("button[name='submit_search']"));
        searchButton.click();

        WebElement printedDress = driver.findElement(By.cssSelector("#center_column > ul > li:nth-child(2) > div > div.left-block > div > a.product_img_link"));
        printedDress.click();

        driver.switchTo().frame(driver.findElement(By.className("fancybox-iframe")));

        WebElement quantity = driver.findElement(By.cssSelector("#quantity_wanted"));
        wait.until(ExpectedConditions.visibilityOf(quantity));
        quantity.clear();
        quantity.sendKeys("4");

        WebElement dressSizeList = driver.findElement(By.cssSelector("#group_1"));
        Select dressSize = new Select(dressSizeList);
        dressSize.selectByIndex(2);

        WebElement addToCartButton = driver.findElement(By.xpath("//*[@id=\"add_to_cart\"]/button"));
        addToCartButton.click();

        String message = driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2")).getText();
        Assert.assertEquals("Product successfully added to your shopping cart", message);
    }

    @Test
    public void discountOnTheDress() {
        WebElement dressLink = driver.findElement(By.cssSelector("#block_top_menu > ul > li:nth-child(2) > a"));
        dressLink.click();

        wait.until(ExpectedConditions.titleIs("Dresses - My Store"));

        List<WebElement> listOfDresses = driver.findElements(By.cssSelector("div.product-container"));
        List<Double> newPrices = new ArrayList<>();
        List<Double> listOfPricesAfterDiscount = new ArrayList<>();

        for (WebElement e: listOfDresses) {

            String priceWithDollarBeforeDiscount = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[3]/div/div[2]/div[1]/span[2]")).getText();
            String priceWithoutDollarBeforeDiscount = priceWithDollarBeforeDiscount.replace("$", "");
            double priceBeforeDiscount = Double.parseDouble(priceWithoutDollarBeforeDiscount);

            String priceWithDollarAfterDiscount = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[3]/div/div[2]/div[1]/span[1]")).getText();
            String priceWithoutDollarAfterDiscount = priceWithDollarAfterDiscount.replace("$", "");
            double priceAfterDiscount = Double.parseDouble(priceWithoutDollarAfterDiscount);
            listOfPricesAfterDiscount.add(priceAfterDiscount);

            String discountWithPercent = driver.findElement(By.cssSelector("div > div.right-block > div.content_price > span.price-percent-reduction")).getText();
            String discountWithoutPercent = discountWithPercent.replaceAll("[^0-9]", "");
            double discount = Double.parseDouble(discountWithoutPercent);

            if(e.getText().contains("%")) {
                newPrices.add(((discount / 100) * priceBeforeDiscount) + priceAfterDiscount);
            }
        }

        Assert.assertEquals(newPrices, listOfPricesAfterDiscount);
    }

    @AfterClass
    public static void afterClass() {
        driver.close();
        driver.quit();
    }
}

