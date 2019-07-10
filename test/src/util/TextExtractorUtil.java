package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlException;

import hwplib.object.HWPFile;
import hwplib.reader.HWPReader;
import hwplib.tool.textextractor.TextExtractMethod;
import hwplib.tool.textextractor.TextExtractor;

/**
 * 2019-01-25 파일 텍스트 추출 pdf, word, ppt, excel, hwp 파일 .ppt, .xls 일부 추출 안됨
 * 
 * 사용: TextExtractorUtil 생성 parser 메서드 사용 매개변수로 파일(파일경로) 필요
 * 
 * @author Zoo
 *
 */
public class TextExtractorUtil {

	/**
	 * pdf, word, ppt, excel 파일의 텍스트 추출
	 * 
	 * @param filepath: 파일 경로 및 이름
	 * @return 추출된 텍스트
	 */
	public static String parser(String filepath) {
		File file = new File(filepath);
		String text = "";

		if (filepath.endsWith(".pdf")) 			{text = readPdfFile(file);}
		else if (filepath.endsWith(".doc")) 	{text = readDocFile(file);}
		else if (filepath.endsWith(".docx"))	{text = readDocxFile(file);}
		else if (filepath.endsWith(".ppt")) 	{text = readPptFile(file);}
		else if (filepath.endsWith(".pptx")) 	{text = readPptxFile(file);}
		else if (filepath.endsWith(".xls")) 	{text = readXlsFile(file);}
		else if (filepath.endsWith(".xlsx")) 	{text = readXlsxFile(file);}
		else if (filepath.endsWith(".hwp")) 	{text = readHwpFile(filepath);}
		
		text=text.replaceAll("(\\r\\n|\\r|\\n|\\n\\r)", "<br>");
		
		return text;
	}

	/**
	 * pdf 파일 추출 시 시작 페이지 지정, excel 파일의 특정 시트의 텍스트 추출
	 * 
	 * @param filepath: 파일 경로 및 이름, num: pdf 추출시 시작페이지, excel 추출시 시트 번호
	 * @return 추출된 텍스트
	 */
	public String parser(String filepath, int num) {
		File file = new File(filepath);
		String text = "";

		if (filepath.endsWith(".pdf")) {
			text = readPdfFile(file, num);
		} else if (filepath.endsWith(".xls")) {
			text = readXlsFile(file, num);
		} else if (filepath.endsWith(".xlsx")) {
			text = readXlsxFile(file, num);
		}

		return text;
	}

	/**
	 * pdf 파일 추출 시 시작과 끝 페이지 지정
	 * 
	 * @param filepath: 파일 경로 및 이름, num: 시작페이지, num2: 끝페이지
	 * @return 추출된 텍스트
	 */
	public String parser(String filepath, int num, int num2) {
		File file = new File(filepath);
		String text = "";

		if (filepath.endsWith(".pdf")) {
			text = readPdfFile(file, num, num2);
		}

		return text;
	}
	
	/**
	 * pdf, word, ppt, excel 파일의 텍스트 추출
	 * 
	 * @param filepath: 파일 경로 및 이름, realName: 실제 파일이름
	 * @return 추출된 텍스트
	 */
	public String parser(String filepath, String realName) {
		File file = new File(filepath);
		String text = "";

		if (realName.endsWith(".pdf")) {
			text = readPdfFile(file);
		} else if (realName.endsWith(".doc")) {
			text = readDocFile(file);
		} else if (realName.endsWith(".docx")) {
			text = readDocxFile(file);
		} else if (realName.endsWith(".ppt")) {
			text = readPptFile(file);
		} else if (realName.endsWith(".pptx")) {
			text = readPptxFile(file);
		} else if (realName.endsWith(".xls")) {
			text = readXlsFile(file);
		} else if (realName.endsWith(".xlsx")) {
			text = readXlsxFile(file);
		} else if (realName.endsWith(".hwp")) {
			text = readHwpFile(filepath);
		}

		return text;
	}
	

	/**
	 * pdf, word, ppt, excel 파일의 텍스트 추출
	 * 
	 * @param file: 파일
	 * @return 추출된 텍스트
	 */
	public String parser(File file) {
		String text = "";
		String filepath = file.getName();

		if (filepath.endsWith(".pdf")) {
			text = readPdfFile(file);
		} else if (filepath.endsWith(".doc")) {
			text = readDocFile(file);
		} else if (filepath.endsWith(".docx")) {
			text = readDocxFile(file);
		} else if (filepath.endsWith(".ppt")) {
			text = readPptFile(file);
		} else if (filepath.endsWith(".pptx")) {
			text = readPptxFile(file);
		} else if (filepath.endsWith(".xls")) {
			text = readXlsFile(file);
		} else if (filepath.endsWith(".xlsx")) {
			text = readXlsxFile(file);
		} else if (filepath.endsWith(".hwp")) {
			text = readHwpFile(file.getAbsolutePath());
		}

		return text;
	}

	/**
	 * pdf 파일 추출 시 시작 페이지 지정, excel 파일의 특정 시트의 텍스트 추출
	 * 
	 * @param file: 파일, num: pdf 추출시 시작페이지, excel 추출시 시트 번호
	 * @return 추출된 텍스트
	 */
	public String parser(File file, int num) {
		String text = "";
		String filepath = file.getName();

		if (filepath.endsWith(".pdf")) {
			text = readPdfFile(file, num);
		} else if (filepath.endsWith(".xls")) {
			text = readXlsFile(file, num);
		} else if (filepath.endsWith(".xlsx")) {
			text = readXlsxFile(file, num);
		}

		return text;
	}

	/**
	 * pdf 파일 추출 시 시작과 끝 페이지 지정
	 * 
	 * @param file: 파일, num: 시작페이지, num2: 끝페이지
	 * @return 추출된 텍스트
	 */
	public String parser(File file, int num, int num2) {
		String text = "";
		String filepath = file.getName();

		if (filepath.endsWith(".pdf")) {
			text = readPdfFile(file, num, num2);
		}

		return text;
	}
	
	/**
	 * pdf, word, ppt, excel 파일의 텍스트 추출
	 * 
	 * @param file: 파일, realName: 실제 파일이름
	 * @return 추출된 텍스트
	 */
	public String parser(File file, String realName) {
		String text = "";
		String filepath = file.getName();

		if (realName.endsWith(".pdf")) {
			text = readPdfFile(file);
		} else if (realName.endsWith(".doc")) {
			text = readDocFile(file);
		} else if (realName.endsWith(".docx")) {
			text = readDocxFile(file);
		} else if (realName.endsWith(".ppt")) {
			text = readPptFile(file);
		} else if (realName.endsWith(".pptx")) {
			text = readPptxFile(file);
		} else if (realName.endsWith(".xls")) {
			text = readXlsFile(file);
		} else if (realName.endsWith(".xlsx")) {
			text = readXlsxFile(file);
		} else if (realName.endsWith(".hwp")) {
			text = readHwpFile(file.getAbsolutePath());
		}

		return text;
	}
	

	/**
	 * 압축파일 풀기
	 * 
	 * @param zipFilePath: 압축파일 경로
	 * @param destDir: 압축해제한 파일 저장할 경로
	 */
	public void unZip(String zipFilePath, String destDir) {
//		String zipFilePath = "C:/Users/user/Downloads/Downloads.zip";
//		String destDir = "E:/uploads/p1";
		List<String> unZipList = null;

		unZipList = unZipFile(zipFilePath, destDir);

		for (String unZip : unZipList) {
			System.out.println("Unzipping to " + unZip);
		}
	}

	/* PDF Parser */
	/* .pdf 텍스트 추출 후 반환 */
	private static String readPdfFile(File file) {
		PDFParser parser = null;
		String text = "";
		PDFTextStripper stripper = null;
		PDDocument pdoc = null;
		COSDocument cdoc = null;

		try {
			parser = new PDFParser(new FileInputStream(file));
			parser.parse();
			cdoc = parser.getDocument();
			stripper = new PDFTextStripper();
			pdoc = new PDDocument(cdoc);
			text = stripper.getText(pdoc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}

	/* .pdf 텍스트 추출 후 반환 (시작 페이지 지정) */
	private String readPdfFile(File file, int first) {
		PDFParser parser = null;
		String text = "";
		PDFTextStripper stripper = null;
		PDDocument pdoc = null;
		COSDocument cdoc = null;

		try {
			parser = new PDFParser(new FileInputStream(file));
			parser.parse();
			cdoc = parser.getDocument();
			stripper = new PDFTextStripper();
			pdoc = new PDDocument(cdoc);
			stripper.setStartPage(first); // 시작페이지
			text = stripper.getText(pdoc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}

	/* .pdf 텍스트 추출 후 반환(시작 페이지와 끝 페이지 지정) */
	private String readPdfFile(File file, int first, int end) {
		PDFParser parser = null;
		String text = "";
		PDFTextStripper stripper = null;
		PDDocument pdoc = null;
		COSDocument cdoc = null;

		try {
			parser = new PDFParser(new FileInputStream(file));
			parser.parse();
			cdoc = parser.getDocument();
			stripper = new PDFTextStripper();
			pdoc = new PDDocument(cdoc);
			stripper.setStartPage(first); // 시작페이지
			stripper.setEndPage(end); // 종료 페이지
			text = stripper.getText(pdoc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}
	/* PDF Parser */

	/* DOC & DOCX Parser */
	/* .doc 텍스트 추출 후 반환 */
	private static String readDocFile(File file) {
		String text = null;

		try {
			InputStream is = new FileInputStream(file);
			WordExtractor wd = new WordExtractor(is);
			text = wd.getText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}

	/* .docx 텍스트 추출 후 반환 */
	private static String readDocxFile(File file) {
		String text = "";

		FileInputStream fis;
		try {
			fis = new FileInputStream(file.getAbsolutePath());
			XWPFDocument document = new XWPFDocument(fis);
			List<XWPFParagraph> paragraphs = document.getParagraphs();

			for (XWPFParagraph para : paragraphs) {
				text += para.getText() + "\n";
			}

			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}
	/* DOC & DOCX Parser */

	/* PPT & PPTX Parser */
	/* .ppt 텍스트 추출 후 반환(슬라이드 안에 있는 표의 텍스트 추출 안됨) */
	private static String readPptFile(File file) { // 표 출력 안됨
		POIFSFileSystem fs = null;
		String text = "";

		try {
			fs = new POIFSFileSystem(new FileInputStream(file));
			PowerPointExtractor extractor = new PowerPointExtractor(fs);
			text = extractor.getText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}

	/* .pptx 텍스트 추출 후 반환 */
	private static String readPptxFile(File file) {
		String text = "";
		XSLFPowerPointExtractor xp = null;
		FileInputStream fs = null;

		try {
			fs = new FileInputStream(file);
			OPCPackage d = OPCPackage.open(fs);
			xp = new XSLFPowerPointExtractor(d);
			text = xp.getText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			e.printStackTrace();
		}

		return text;
	}
	/* PPT & PPTX Parser */

	/* XLS & XLSX Parser */
	/* .xls 텍스트 추출 후 반환 */
	private static String readXlsFile(File file) {
		String text = "";

		FileInputStream fis;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;

		try {
			fis = new FileInputStream(file);
			workbook = new HSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int rowindex = 0;
		int columnindex = 0;

		// 시트 반복
		for (int sheetindex = 0; sheetindex < workbook.getNumberOfSheets(); sheetindex++) {
			// 시트 불러오기
			sheet = workbook.getSheetAt(sheetindex);
			// 시트이름 출력
			text += "Sheet : " + workbook.getSheetName(sheetindex) + "\n";
			// 행의 수
			int rows = sheet.getPhysicalNumberOfRows();
			for (rowindex = 0; rowindex < rows; rowindex++) {
				// 행을 읽는다
				HSSFRow row = sheet.getRow(rowindex);
				if (row != null) {
					// 셀의 수
					int cells = row.getPhysicalNumberOfCells();
					for (columnindex = 0; columnindex <= cells; columnindex++) {
						// 셀값을 읽는다
						HSSFCell cell = row.getCell(columnindex);
						String value = "";
						// 셀이 빈값일경우를 위한 널체크
						if (cell == null) {
							continue;
						} else {
							// 타입별로 내용 읽기
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_FORMULA:
								value = cell.getCellFormula();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								value = cell.getNumericCellValue() + "";
								break;
							case HSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue() + "";
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								value = cell.getBooleanCellValue() + "";
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								value = cell.getErrorCellValue() + "";
								break;
							}
						}
						text += value + "\n";
					}
				}
			}
		}

		return text;
	}

	/* .xls 텍스트 추출 후 반환(특정 시트) */
	private String readXlsFile(File file, int num) {
		String text = "";

		FileInputStream fis;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;

		try {
			fis = new FileInputStream(file);
			workbook = new HSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int rowindex = 0;
		int columnindex = 0;

		// 시트 불러오기
		sheet = workbook.getSheetAt(num);
		// 행의 수
		int rows = sheet.getPhysicalNumberOfRows();
		for (rowindex = 0; rowindex < rows; rowindex++) {
			// 행을 읽는다
			HSSFRow row = sheet.getRow(rowindex);
			if (row != null) {
				// 셀의 수
				int cells = row.getPhysicalNumberOfCells();
				for (columnindex = 0; columnindex <= cells; columnindex++) {
					// 셀값을 읽는다
					HSSFCell cell = row.getCell(columnindex);
					String value = "";
					// 셀이 빈값일경우를 위한 널체크
					if (cell == null) {
						continue;
					} else {
						// 타입별로 내용 읽기
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_FORMULA:
							value = cell.getCellFormula();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							value = cell.getNumericCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							value = cell.getBooleanCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = cell.getErrorCellValue() + "";
							break;
						}
					}
					text += value + "\n";
				}
			}
		}

		return text;
	}

	/* .xlsx 텍스트 추출 후 반환(특정 시트) */
	private String readXlsxFile(File file, int num) {
		FileInputStream fis;
		String text = "";
		int lastSheet = 0;

		try {
			fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			lastSheet = workbook.getNumberOfSheets();
			// 시트 불러오기
			XSSFSheet sheet = workbook.getSheetAt(num);
			// 행을 구한다
			Iterator<Row> rowIt = sheet.iterator();
			while (rowIt.hasNext()) {
				Row row = rowIt.next();
				// 셀을 구한다
				Iterator<Cell> cellIterator = row.cellIterator();
				// 셀값을 읽는다
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					text += cell.toString() + "\n";
				}
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("현재 파일은 0 - " + lastSheet + "까지 추출이 가능합니다.");
		}

		return text;
	}

	/* .xlsx 텍스트 추출 후 반환 */
	private static String readXlsxFile(File file) {
		FileInputStream fis;
		String text = "";

		try {
			fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			// 시트 반복해서 불러오기
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				text += "sheet : " + sheet.getSheetName() + "\n";
				// 행을 구한다
				Iterator<Row> rowIt = sheet.iterator();
				while (rowIt.hasNext()) {
					Row row = rowIt.next();
					// 셀을 구한다
					Iterator<Cell> cellIterator = row.cellIterator();
					// 셀값을 읽는다
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						text += cell.toString() + "\n";
					}
				}
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}
	/* XLS & XLSX Parser */

	/* HWP Parser */
	private static String readHwpFile(String filepath) {
		String text = "";

		HWPFile hwpFile;
		try {
			TextExtractMethod tem = TextExtractMethod.InsertControlTextBetweenParagraphText;
			hwpFile = HWPReader.fromFile(filepath);
			text = TextExtractor.extract(hwpFile, tem);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}
	/* HWP Parser */

	/* UnZip */
	private ArrayList<String> unZipFile(String zipFilePath, String destDir) {
		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		ArrayList<String> unZipList = new ArrayList();

		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis, Charset.forName("EUC-KR"));
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(destDir + File.separator + fileName);
				unZipList.add(newFile.getAbsolutePath());
				// create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return unZipList;
	}
	/* UnZip */
}
