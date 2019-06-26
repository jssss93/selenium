package test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

//출처: https://yonoo88.tistory.com/603 [yonoo's]

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class test20190626 {

	private static WebDriver driver;
	String Title = null;
	String URL = null;
	String alertText = "";

	@BeforeClass
	public static void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe"); // 크롬 드라이버 파일 경로설정
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);// 응답시간 5초설정
		driver.get("http://www.naver.com");// 접속할 사이트
	}

	@Test
	public void Step_01_지식인_로그인_Test() throws Exception {
		driver.findElement(By.id("svc.kin")).click(); // 지식인 클릭
		driver.findElement(By.id("login_btn")).click(); // NAVER로그인 클릭
		driver.findElement(By.id("id")).sendKeys("ID"); // ID
		driver.findElement(By.id("pw")).sendKeys("PASSWORD"); // 비번
		driver.findElement(By.className("int_jogin")).click(); // 로그인 버튼 클릭
	}

	@Test
	public void Step_02_임시저장_Test() throws Exception {
		for (String Handle : driver.getWindowHandles())// 새창으로 스위칭
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
		Thread.sleep(1000); // 너무 빨라 얼럿 창 노출이 안되는 걸 방지하기 위해 슬립 추가
		Alert alert = driver.switchTo().alert(); // 얼럿 창으로 스위칭
		alertText = alert.getText(); // 얼럿 창 메시지 추출
		alert.accept(); // 얼럿 창 확인 클릭
		System.out.println(alertText); // 얼랏 창 메시지 출력
		Thread.sleep(1000);
		System.out.println("임시저장 글 개수 : " + num);
		driver.findElement(By.xpath("//*[@id='title']")).clear(); // 질문 제목 삭제
		driver.findElement(By.xpath("//*[@id='title']")).sendKeys("파이브식스세븐에잇"); // 질문 다시 입력
		driver.switchTo().frame("SmartEditorIframe"); // 내용 입력 창 iframe 스위칭
		driver.findElement(By.cssSelector("body")).sendKeys("오육칠팔"); // 내용입력
		driver.switchTo().defaultContent(); // 창 스위칭 초기화//다시 질문하기 창 iframe 스위칭
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
		Thread.sleep(1000);
	}

	@Test
	public void Step_03_다음단계_Test() throws Exception {
		driver.findElement(By.xpath("//img[@alt='다음단계']")).click();
		// 다음단계 버튼 클릭 //카테고리
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
		driver.switchTo().defaultContent(); // 창 스위칭 초기화 //질문하기 창 iframe 스위칭
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
		driver.findElement(By.xpath("//*[@id='au_submit_button2']/div[2]/a[1]/img")).click();// 임시저장
		// 클릭
		Thread.sleep(3000);
		Alert alert = driver.switchTo().alert(); // 얼럿 창
		// 스위칭
		alertText = alert.getText();
		alert.accept(); // 얼럿 확인 클릭
		System.out.println("다른제목으로 " + alertText);
		String num = driver.findElement(By.id("temporary_save_count")).getText(); // 임시저장 카운트
		// 추출
		System.out.println("임시저장 글 개수 : " + num);
	}

	@Test
	public void Step_04_이전_글_임시저장_삭제_Test() throws Exception { // 임시저장 창
		driver.findElement(By.className("_tempsave_open_close")).click(); // 임시저장 글 클릭
		driver.switchTo().defaultContent(); // 창 초기화
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));// 질문하기 iframe 창 스위칭

		String info = driver.findElement(By.className("info_tempsave")).getText(); // 임시저장 창 안내 글 추출
		System.out.println("임시저장 창 " + info);
		WebElement tempList = driver.findElement(By.className("tempsave_list")); // 임시저장 글 목록 가져옴
		List<WebElement> list = tempList.findElements(By.className("q_on"));// 목록을 리스트에 넣어둠 //이전 글이니까 2번째인 1번 목록 삭제 버튼을
																			// 클릭
		list.get(1).findElement(By.tagName("img")).click();
		Thread.sleep(3000);
		Alert alert = driver.switchTo().alert(); // 삭제 확인 얼럿 창
		alertText = alert.getText();
		alert.accept(); // 확인 클릭
		System.out.println("이전" + alertText);
	}

	@Test
	public void Step_05_임시저장_삭제_Test() throws Exception {
		/******* 이전 글 삭제와 동일 *******/
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
		WebElement tempList1 = driver.findElement(By.className("tempsave_list"));
		List<WebElement> list1 = tempList1.findElements(By.className("q_on"));// 여기서 현재글 삭제를 위해 1번째인 0번째 목록 삭제버튼 클릭
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
	}

}
