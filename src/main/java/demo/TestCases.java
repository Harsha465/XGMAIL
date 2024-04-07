package demo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestCases {
    ChromeDriver driver;
    public TestCases()
    {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.INFO);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Connect to the chrome-window running on debugging port
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    
    public  void testCase01(){
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.google.com");
        driver.get("https://calendar.google.com/");
        if(driver.getCurrentUrl().contains("calendar")){
            System.out.println("Success : The URL of the Calendar homepage contains calendar");
        }else{
            System.out.println("Failed ...");
        }
        System.out.println("end Test case: testCase01");
    }

    public  void testCase02() throws InterruptedException {
        System.out.println("Start Test case: testCase02");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement target = driver.findElement(By.xpath("//button[@jsname='jnPWCc']"));

        target.click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        WebElement month_ele = driver.findElement(By.xpath("//li[@jslog='157012']/span[@jsname='K4r5Ff']"));
        if(month_ele.getText().equals("Month")){
            System.out.println("Switched to Month view successfully..");
        }
        wait.until(ExpectedConditions.elementToBeClickable(month_ele)).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@aria-label='Create']")).click();
        Thread.sleep(1000);

        WebElement task = driver.findElement(By.xpath("/html/body/div[21]/div/div/span[2]/div[2]/div"));
//        WebDriverWait wait2 = new WebDriverWait(driver,Duration.ofSeconds(3));
//        wait2.until(ExpectedConditions.elementToBeClickable(task)).click();
        task.click();
        driver.findElement(By.xpath("//button[@id='tabTask']")).click();
        WebElement input_title = driver.findElement(By.xpath("//input[@aria-label='Add title']"));
        input_title.sendKeys("Crio INTV Task Automation");
        WebElement description = driver.findElement(By.xpath("//textarea[@aria-label='Add description']"));
        description.sendKeys("Crio INTV Calendar Task Automation");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@jsname='x8hlje']")).click();
        Thread.sleep(2000);
        driver.get("https://calendar.google.com/");
        System.out.println("The Calendar switched to month view and a task was created.");
        System.out.println("end Test case: testCase02");
    }

    public  void testCase03() throws InterruptedException {
        System.out.println("Start Test case: testCase03");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement crio_task = driver.findElement(By.xpath("//span[text()='Crio INTV Task Automation']"));
        crio_task.click();
        WebElement edit_task = driver.findElement(By.xpath("//button[@aria-label='Edit task']"));
        edit_task.click();
        WebElement description = driver.findElement(By.xpath("//textarea[@placeholder='Add description']"));
        description.clear();
        description.sendKeys("Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@jsname='x8hlje']/span[text()='Save']")).click();
        Thread.sleep(1000);
        crio_task.click();
        Thread.sleep(1000);
        String updated = driver.findElement(By.xpath("//div[@class='toUqff D29CYb']")).getText();
        Thread.sleep(1000);
        if(updated.contains("Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application")){
            System.out.println("Description Updated sucessfully..");
        }

        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@aria-label='Close' and @data-id='TvD9Pc']")).click();

        System.out.println("end Test case: testCase03");
    }

    public  void testCase04() throws InterruptedException {
        System.out.println("Start Test case: testCase04");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
        driver.get("https://calendar.google.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement existingTask = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Crio INTV Task Automation']")));
        existingTask.click();

        WebElement verifyTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='rAECCd']")));
        String actualTitle = verifyTitle.getText();
        String expectedTitle = "Crio INTV Task Automation";

        if (!actualTitle.equals(expectedTitle)) {
            System.out.println("The title of the task is not verified.");
        } else {
            System.out.println("The title of the task is successfully verified..");
        }

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='pPTZAe']//button)[2]")));
        deleteButton.click();

        try {
            WebElement taskDeleted = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[text()='Task deleted'])[1]")));
            String taskdel = taskDeleted.getText();
            if (taskdel.contains("Task deleted")) {
                System.out.println("Task deleted");
            }
        } catch (TimeoutException e) {
            System.out.println("Task deletion confirmation not found. Deletion may have failed.");
        }
        System.out.println("end Test case: testCase04");
    }



}
