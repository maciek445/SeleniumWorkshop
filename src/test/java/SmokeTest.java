import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SmokeTest {

    @Test
    public void openHomePage() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to("http://automationpractice.com/index.php");
        Assert.assertEquals("Wrong page title", "My Store", driver.getTitle());

        driver.close();
        driver.quit();
    }

    @Test
    public void searchDress() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to("http://automationpractice.com/index.php");

        driver.findElement(By.cssSelector("[class='search_query form-control ac_input']")).sendKeys("Printed Dress");
        driver.findElement(By.cssSelector("button[name='submit_search']")).click();
        driver.findElement(By.cssSelector("img[src='http://automationpractice.com/img/p/1/0/10-home_default.jpg']")).click();

        Assert.assertEquals("demo_4", driver.findElement(By.cssSelector("span[class='editable']")).getText());

        driver.close();
        driver.quit();


    }

}
