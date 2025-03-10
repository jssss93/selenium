package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class test20190627 {
	public static void main(String[] args) {
		test20190627 selTest = new test20190627();
        selTest.crawl();
        
    }
    //WebDriver
    private WebDriver driver;
    
    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "D:/2019/chromedriver.exe";
    
    //크롤링 할 URL
    private String base_url;
        public test20190627() {
        super();
 
        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        
        ChromeOptions options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
//        options.addArguments("headless");
        System.out.println("options==>");
        System.out.println(options);
        

        System.out.println("------------------");
        
        //Driver SetUp
        driver = new ChromeDriver(options);
        base_url = "https://finance.naver.com/";
//        base_url="https://nid.naver.com/nidlogin.login?url=http%3A%2F%2Fwww.naver.com";
        
        
    }
 
    public void crawl() {
        try {
        	
            driver.get(base_url);
            Thread.sleep(500);
            
//            driver.findElement(By.className("gnb_login_button")).click(); // 지식인 클릭
//    		driver.findElement(By.id("gnb_login_button")).click(); // NAVER로그인 클릭
            driver.findElement(By.xpath("//*[@id=\"gnb_login_button\"]/span[3]")).click();
    		driver.findElement(By.id("id")).sendKeys("cjs0533"); // ID
    		
    		Thread.sleep(1000);
    		driver.findElement(By.id("pw")).sendKeys("1q2w3e4r#"); // 비번
    		Thread.sleep(2000);
    		driver.findElement(By.xpath("//*[@id=\"frmNIDLogin\"]/fieldset/input")).click();
//    		driver.findElement(By.className("btn_global")).click(); // 로그인 버튼 클릭
            
            
    
        } catch (Exception e) {
            
            e.printStackTrace();
        
        } finally {
 
//            driver.close();
        }
 
    }
}
