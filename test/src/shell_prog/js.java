package shell_prog;

public class js {

	public static void main(String args[]) {

		System.out.println("start");

		String[] command = { "/bin/sh", "-c", "/home/gasystem/SNS_Write/bin/python/test.sh 11 22" };

		try {
			System.out.println("명령:"+command[0]+command[1]+command[2]);
			Process ps = Runtime.getRuntime().exec(command);
			ps.waitFor();
			System.out.println(ps.exitValue());
			ps.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end");

	}
}
