package parse_doc;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class test {
	/**
	 * 踰꾪띁 �궗�씠利�
	 */
	final static int size = 1024;

	/**
	 * fileAddress�뿉�꽌 �뙆�씪�쓣 �씫�뼱, �떎�슫濡쒕뱶 �뵒�젆�넗由ъ뿉 �떎�슫濡쒕뱶
	 * 
	 * @param fileAddress
	 * @param localFileName
	 * @param downloadDir
	 */
	public static void fileUrlReadAndDownload(String fileAddress, String localFileName, String downloadDir) {
		OutputStream outStream = null;
		URLConnection uCon = null;

		InputStream is = null;
		try {

			System.out.println("-------Download Start------");

			URL Url;
			byte[] buf;
			int byteRead;
			int byteWritten = 0;
			Url = new URL("fileAddress");
			outStream = new BufferedOutputStream(new FileOutputStream(downloadDir + "\\" + localFileName));

			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((byteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, byteRead);
				byteWritten += byteRead;
			}

			System.out.println("Download Successfully.");
			System.out.println("File name : " + localFileName);
			System.out.println("of bytes  : " + byteWritten);
			System.out.println("-------Download End--------");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param fileAddress
	 * @param downloadDir
	 */
	public static void fileUrlDownload(String fileAddress, String downloadDir) {

		int slashIndex = fileAddress.lastIndexOf('/');
		int periodIndex = fileAddress.lastIndexOf('.');

		// �뙆�씪 �뼱�뱶�젅�뒪�뿉�꽌 留덉�留됱뿉 �엳�뒗 �뙆�씪�씠由꾩쓣 痍⑤뱷
		String fileName = fileAddress.substring(slashIndex + 1);

		if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < fileAddress.length() - 1) {
			fileUrlReadAndDownload(fileAddress, fileName, downloadDir);
		} else {
			System.err.println("path or file name NG.");
		}
	}

	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// �뙆�씪 �뼱�뱶�젅�뒪
		String url = "http://localhost/download/index.php";
		// �떎�슫濡쒕뱶 �뵒�젆�넗由�
		String downDir = "C:/Temp";

		// �떎�슫濡쒕뱶 �샇異�
		fileUrlDownload(url, downDir);

	}
}