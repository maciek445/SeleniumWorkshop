import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class SmokeTest {

    private WebDriver driver;

    @Test
    public void openHomePage() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to("http://automationpractice.com/index.php");
        Assert.assertEquals("Wrong page title", "My Store", driver.getTitle());

        driver.quit();
    }

    @Test
    public void searchDress() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to("http://automationpractice.com/index.php");
        driver.findElement(By.cssSelector("[class='search_query form-control ac_input']")).sendKeys("Printed Dress");
        driver.findElement(By.cssSelector("button[name='submit_search']")).click();
        driver.findElement(By.cssSelector("img[src='http://automationpractice.com/img/p/1/0/10-home_default.jpg']")).click();

        Assert.assertEquals("demo_4", driver.findElement(By.cssSelector("span[class='editable']")).getText());

        driver.quit();
    }

    @Test
    public void tryToSignInWithUsedEmailTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to("http://automationpractice.com/index.php");
        driver.findElement(By.cssSelector("a[class='login']")).click();
        driver.findElement(By.cssSelector("input[class='is_required validate account_input form-control']")).sendKeys("test@test.com");
        driver.findElement(By.cssSelector("div[class='submit'] > button")).click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String errorMessage=driver.findElement(By.xpath("//*[@id=\'create_account_error\']")).getText();

        Assert.assertEquals("An account using this email address has already been registered. Please enter a valid password or request a new one.", errorMessage);

        driver.quit();
    }

    @Test
    public void addDressToCart() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to("http://automationpractice.com/index.php");
        driver.findElement(By.cssSelector("a[class='product_img_link'][title='Printed Dress']")).click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.cssSelector("p[id='add_to_cart'] > button[class='exclusive']")).click();

        String message = driver.findElement(By.cssSelector("div[class=\"layer_cart_product col-xs-12 col-md-6\"] > h2")).getText();

        Assert.assertEquals("Product successfully added to your shopping cart", message);

        driver.quit();
    }

}
