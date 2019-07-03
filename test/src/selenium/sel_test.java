package selenium;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class sel_test {
	private static WebDriver driver;
	String Title = null;
	String URL = null;
	String alertText = "";

	
	@BeforeClass
	public static void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "D:/2019/chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		//fake user-agent 설정
		options.addArguments("User-Agent={Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36}");
//		options.addArguments("headless");
		options.addArguments("window-size=1920x1080");
		options.addArguments("disable-gpu");
		

		driver = new ChromeDriver(options);
		JavascriptExecutor js_exe=(JavascriptExecutor)driver;
		driver.get("https://finance.naver.com");
		
		//fake 플러그인 생성
//		js_exe.executeScript("Object.defineProperty(navigator, 'plugins', {get: function() {return[1, 2, 3, 4, 5]}})");
		//fake language 설정
//		js_exe.executeScript("Object.defineProperty(navigator, 'languages', {get: function() {return ['ko-KR', 'ko']}})");
		//fake GPU 정보 생성
//		js_exe.executeScript("const getParameter = WebGLRenderingContext.getParameter;WebGLRenderingContext.prototype.getParameter = function(parameter) {if (parameter === 37445) {return 'NVIDIA Corporation'} if (parameter === 37446) {return 'NVIDIA GeForce GTX 1050';}return getParameter(parameter);};");
		
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//*[@id=\"gnb_login_button\"]/span[3]")).click();
		js_exe.executeScript("document.getElementsByName('id')[0].value=\'ga_blog\'");
		js_exe.executeScript("document.getElementsByName('pw')[0].value=\'jw21250!@#\'");
		driver.findElement(By.xpath("//*[@id=\"frmNIDLogin\"]/fieldset/input")).click();
		Thread.sleep(1000);
		driver.get("https://blog.naver.com/ga_blog");
		Thread.sleep(1000);
	    driver.switchTo().frame("mainFrame");
	    
	    driver.findElement(By.xpath("//*[@id='post-admin']/a[1]")).click();
	    
	    
	    js_exe.executeScript("document.getElementById('subject').value=\'제목입니다\'");
	    
	    //내용입력시작.
	    driver.switchTo().frame("se2_iframe");
	    driver.findElement(By.xpath("/html/body")).sendKeys("test_content");
	    
	    driver.switchTo().defaultContent();
	    driver.switchTo().frame("mainFrame");
	    
	    String filepath = "D:/image.png";
	    String parentHandle= driver.getWindowHandle();
	    sele_file(driver,filepath);		//파일 시작
	    
		driver.switchTo().window(parentHandle);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("mainFrame");
		
		sele_image(driver,filepath);	//사진 시작
	    
		Thread.sleep(1000);
		
		//전송
		driver.switchTo().window(parentHandle);
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("mainFrame");
		driver.switchTo().frame("se2_iframe");
	    driver.findElement(By.xpath("/html/body")).sendKeys("\ntest_content2");
	    
	    driver.switchTo().defaultContent();
		driver.switchTo().frame("mainFrame");
		
		driver.findElement(By.xpath("//*[@id=\"btn_preview\"]")).click();
		
	}
	
	public static void sele_file(WebDriver driver,String filePath) throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"se2_tool\"]/div[1]/ul[1]/li[4]")).click();
		Thread.sleep(1000);
		//Switch to new window opened 
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		
		WebElement fileInput = driver.findElement(By.xpath("//*[@id=\"file_select\"]"));
		fileInput.sendKeys(filePath);

		driver.findElement(By.xpath("//*[@id=\"submitBtn\"]")).click();
		
		Thread.sleep(1000);
	}
	
	public static void sele_image(WebDriver driver, String imagePath) throws InterruptedException {
		//사진버튼
		driver.findElement(By.xpath("//*[@id=\"se2_tool\"]/div[1]/ul[1]/li[1]")).click();
		Thread.sleep(5000);
		// Switch to new window opened
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		// X버튼 클릭(alert제거)
		driver.findElement(By.xpath("/html/body/div[2]/div/button")).click();

		// 내PC버튼
		WebElement imageInput = driver.findElement(By.xpath("//*[@id=\"pc_image_file\"]"));
		System.out.println(imageInput.getText());
		Thread.sleep(2000);
		imageInput.sendKeys(imagePath);
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[3]/header/div[2]/button")).click();
	}
	@Test
	public void Step_01_지식인_로그인_Test() throws Exception {
//		HttpServletRequest req = driver.;
//		System.out.println(req.getHeader("User-Agent"));
		
//		System.out.println("123123");
//		driver.findElement(By.className("mn_kin")).click();
//		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
//		Thread.sleep(3000);
//		driver.findElement(By.className("gnb_txt")).click();
//		driver.findElement(By.id("id")).sendKeys("cjs0533");
//		Thread.sleep(3000);
//		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//		driver.findElement(By.id("pw")).sendKeys("1q2w3e4r#");
//		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//		Thread.sleep(3000);
//		driver.findElement(By.className("btn_global")).click();
		
//로그인 버튼 클릭
	}

	/*@Test
	public void Step_02_임시저장_Test() throws Exception {
		for (String Handle : driver.getWindowHandles())
			// 새창으로 스위칭
			driver.switchTo().window(Handle);
		driver.findElement(By.xpath("//*[@id='main_top_2']/div[1]/fieldset[1]/div/a/img")).click();
		// 질문하기 클릭
		driver.switchTo().frame("editor");
		// 질문하기 입력창 iframe으로 스위칭
		driver.findElement(By.xpath("//*[@id='title']")).sendKeys("원투쓰리포");
		// 제목입력
		driver.switchTo().frame("SmartEditorIframe");
		// 내용입력 창 iframe 스위칭
		driver.findElement(By.cssSelector("body")).sendKeys("일이삼사");
		// 내용입력
		driver.switchTo().defaultContent();
		// 스위칭 초기화
		// 다시 질문하기 창 iframe 스위칭
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
		driver.findElement(By.xpath("//*[@id='au_submit_button']/div[2]/a[1]/img")).click();
		// 임시저장 클릭
		String num = driver.findElement(By.id("temporary_save_count")).getText();
		// 현재 임시저장 글 수 카운트 추출
		Thread.sleep(1000);
		// 너무 빨라 얼럿 창 노출이 안되는 걸 방지하기 위해 슬립 추가
		Alert alert = driver.switchTo().alert();
		// 얼럿 창으로 스위칭
		alertText = alert.getText();
		// 얼럿 창 메시지 추출
		alert.accept();
		// 얼럿 창 확인 클릭
		System.out.println(alertText);
		// 얼랏 창 메시지 출력
		Thread.sleep(1000);
		System.out.println("임시저장 글 개수 : " + num);
		driver.findElement(By.xpath("//*[@id='title']")).clear();
		// 질문 제목 삭제
		driver.findElement(By.xpath("//*[@id='title']")).sendKeys("파이브식스세븐에잇");
		// 질문 다시 입력
		driver.switchTo().frame("SmartEditorIframe");
		// 내용 입력 창 iframe 스위칭
		driver.findElement(By.cssSelector("body")).sendKeys("오육칠팔");
		// 내용입력
		driver.switchTo().defaultContent();
		// 창 스위칭 초기화 //다시 질문하기 창 iframe 스위칭
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
		Thread.sleep(1000);
	}

	@Test
	public void Step_03_다음단계_Test() throws Exception {
		driver.findElement(By.xpath("//img[@alt='다음단계']")).click();
		// 다음단계 버튼 클릭
		// 카테고리
		Thread.sleep(1000);
		driver.findElement(By.linkText("디렉토리 직접 선택")).click();
		// 디렉토리 직접선택 클릭
		Select dropdown = new Select(driver.findElement(By.id("choose_dir_d1")));
		// 첫번째 셀렉 박스를 가져옴
		dropdown.deselectAll();
		// 선택 모두 초기화
		dropdown.selectByValue("1");
		// value 1인 값을 선택
		dropdown = new Select(driver.findElement(By.id("choose_dir_d2")));
		// 두번째 셀렉박스 가져옴
		dropdown.deselectAll();
		// 이하 동일
		dropdown.selectByValue("101");
		dropdown = new Select(driver.findElement(By.id("choose_dir_d3")));
		dropdown.deselectAll();
		dropdown.selectByValue("10103");
		dropdown = new Select(driver.findElement(By.id("choose_dir_d4")));
		dropdown.deselectAll();
		dropdown.selectByValue("1010303");
		Thread.sleep(1000);
		driver.switchTo().defaultContent();
		// 창 스위칭 초기화 //질문하기 창 iframe 스위칭
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
		driver.findElement(By.xpath("//*[@id='au_submit_button2']/div[2]/a[1]/img")).click();
		// 임시저장 클릭
		Thread.sleep(3000);
		Alert alert = driver.switchTo().alert();
		// 얼럿 창 스위칭
		alertText = alert.getText();
		alert.accept();
		// 얼럿 확인 클릭
		System.out.println("다른제목으로 " + alertText);
		String num = driver.findElement(By.id("temporary_save_count")).getText();
		// 임시저장 카운트 추출
		System.out.println("임시저장 글 개수 : " + num);
	}

	@Test
	public void Step_04_이전_글_임시저장_삭제_Test() throws Exception {
		// 임시저장 창
		driver.findElement(By.className("_tempsave_open_close")).click();
		// 임시저장 글 클릭
		driver.switchTo().defaultContent();
		// 창 초기화
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
		// 질문하기 iframe 창 스위칭
		String info = driver.findElement(By.className("info_tempsave")).getText();
		// 임시저장 창 안내 글 추출
		System.out.println("임시저장 창 " + info);
		WebElement tempList = driver.findElement(By.className("tempsave_list"));
		// 임시저장 글 목록 가져옴
		List<WebElement> list = tempList.findElements(By.className("q_on"));
		// 목록을 리스트에 넣어둠 //이전 글이니까 2번째인 1번 목록 삭제 버튼을 클릭
		list.get(1).findElement(By.tagName("img")).click();
		Thread.sleep(3000);
		Alert alert = driver.switchTo().alert();
		// 삭제 확인 얼럿 창
		alertText = alert.getText();
		alert.accept();
		// 확인 클릭
		System.out.println("이전" + alertText);
	}

	@Test
	public void Step_05_임시저장_삭제_Test() throws Exception {
		*//******* 이전 글 삭제와 동일 *******//*
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
		WebElement tempList1 = driver.findElement(By.className("tempsave_list"));
		List<WebElement> list1 = tempList1.findElements(By.className("q_on"));
		// 여기서 현재글 삭제를 위해 1번째인 0번째 목록 삭제버튼 클릭
		list1.get(0).findElement(By.tagName("img")).click();
		Thread.sleep(3000);
		Alert alert = driver.switchTo().alert();
		alertText = alert.getText();
		alert.accept();
		System.out.println(alertText);
		Thread.sleep(1000);
		String num = driver.findElement(By.id("temporary_save_count")).getText();
		System.out.println("임시저장 글 개수 : " + num);
		Thread.sleep(1000);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		driver.quit();
	}*/

}
