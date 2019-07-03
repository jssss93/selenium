package shell_prog;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShellTest {

	public static void main(String args[]) {
		Runtime rt = Runtime.getRuntime();
		Process proc = null;
		InputStream is = null;
		BufferedReader bf = null;

		String param1 = "aa";
		String param2 = "bb";
		String param3 = "cc";

		try {
			param1 = args[0];
			param2 = args[1];
			System.out.println("11");
			System.out.println(param1);
			System.out.println(param2);

			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("/home/gasystem/SNS_Write/bin/python/test.sh");
			InputStream is2 = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is2);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("22");

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
