package shell_prog;

//\

//2010. 11. 23. 16:22
//복사http://blog.naver.com/hex91/120118947585
//번역하기
//
//전용뷰어 보기
//셀 스크립트 명 : barcode_expire.sh
//전달 파라미터값 : [스크립트명, 파라메타1, 파라메타2, 파라메타3]

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class tt {

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

//		try {
//
//			String prg = "import sys\nprint int(sys.argv[1])+int(sys.argv[2])\n";
//			
//			BufferedWriter out = new BufferedWriter(new FileWriter("test1.py"));
//			out.write(prg);
//			out.close();
//			int number1 = 10;
//			int number2 = 32;
//
//			ProcessBuilder pb = new ProcessBuilder("python", "E:\\test\\test.py", "" + number1, "" + number2);
//			Process p = pb.start();
//
//			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//			int ret = new Integer(in.readLine()).intValue();
//			System.out.println("value is : " + ret);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//

//		 try{
//	            String pythonPath = "E:\\test\\test.py";
//	            //String pythonExe = "C:/Users/AppData/Local/Continuum/Anaconda/python.exe";
//	            ProcessBuilder pb = new ProcessBuilder("python", pythonPath);
//	            Process p = pb.start();
//
//	            BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
//	            String line = "";
//	            System.out.println("Running Python starts: " + line);
//	            line = bfr.readLine();
//	            System.out.println("First Line: " + line);
//	            while ((line = bfr.readLine()) != null){
//	                System.out.println("Python Output: " + line);
//
//
//	            }
//
//	        }catch(Exception e){System.out.println(e);}
//	    }

		// 프로세스빌더
//	try{
//		String prm1="test1";
//		String prm2="test2";
//		
//		String pythonPath = "/home/gasystem/SNS_Write/bin/python/test.sh";
//		   String prg = "import sys\nprint int(sys.argv[1])+int(sys.argv[2])\n";
////		   BufferedWriter out = new BufferedWriter(new FileWriter("test1.py"));
////		   out.write(prg);
////		   out.close();
//		   int number1 = 10;
//		   int number2 = 32;
//
//		   ProcessBuilder pb = new ProcessBuilder("python","test.py",""+number1,""+number2);
//		   Process p = pb.start();
//
//		   BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//		   int ret = new Integer(in.readLine()).intValue();
//		   System.out.println("value is : "+ret);
//		}catch(Exception e){System.out.println(e);}
//	}
	}
}
