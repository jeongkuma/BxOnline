package kr.co.auiot.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.GroupLayout.Alignment;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 엑셀 관련 유틸리티
 *
 * @author will
 *
 */
@Slf4j
@SuppressWarnings({ "rawtypes", "deprecation" })
public class ExcelUtils {

	public static final int FILE_BUFFER_SIZE = 1024;
	public static final int EXCEL_SHEET_ROW_COUNT = 36000; // 한시트에 들어갈 행 갯수

	/**
	 * 첫줄(Title) 스타일
	 *
	 * @method Name : headCellStyle
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param wb
	 * @return @. deprecated :
	 */
	@SuppressWarnings("static-access")
	public static XSSFCellStyle headCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();

		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);

		style.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 채우기

		style.setTopBorderColor(IndexedColors.GREY_80_PERCENT.index);
		style.setRightBorderColor(IndexedColors.GREY_80_PERCENT.index);
		style.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.index);
		style.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.index);

		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);

		style.setAlignment(HorizontalAlignment.CENTER); // 가운데 정렬
		style.setVerticalAlignment(VerticalAlignment.CENTER); // 가운데 정렬(세로)

		return style;
	}

	/**
	 * 엑셀파일에 Sheet를 만들고 첫줄(Title)을 생성
	 *
	 * @method Name : createSheetTiles
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param wb
	 * @param excelMap
	 * @return @. deprecated :
	 */
	public static XSSFSheet createSheetTiles(XSSFWorkbook wb, Map excelMap) {
		XSSFSheet sheet = wb.createSheet();
		XSSFDataFormat format = (XSSFDataFormat)wb.createDataFormat();
		XSSFCellStyle style = (XSSFCellStyle)wb.createCellStyle();
		style.setDataFormat(format.getFormat("@"));
		XSSFRow header = sheet.createRow(0);
		XSSFCell cell = null;

		Set titles = excelMap.keySet();
		Iterator iter = titles.iterator();

		int i = 0;
		while (iter.hasNext()) {
			cell = header.createCell((short) i);
			cell.setCellStyle(headCellStyle(wb));
			cell.setCellValue(iter.next().toString());
			i++;
		}
		for (int j = 0; j < 1001; j++) {
			sheet.setDefaultColumnStyle(j, style);
		}
		return sheet;
	}

	/**
	 * 엑셀파일에 Sheet를 만들고 첫줄(Title)을 생성
	 *
	 * @method Name : createSheetTiles
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param wb
	 * @param excelMap
	 * @param sheetName
	 * @return @. deprecated :
	 */
	public static XSSFSheet createSheetTiles(XSSFWorkbook wb, Map excelMap, String sheetName) {
		XSSFDataFormat format = (XSSFDataFormat)wb.createDataFormat();
		XSSFCellStyle style = (XSSFCellStyle)wb.createCellStyle();
		style.setDataFormat(format.getFormat("@"));
		XSSFSheet sheet = wb.createSheet(sheetName);

		XSSFRow header = sheet.createRow(0);
		XSSFCell cell = null;

		Set titles = excelMap.keySet();
		Iterator iter = titles.iterator();

		int i = 0;
		while (iter.hasNext()) {
			cell = header.createCell((short) i);
			cell.setCellStyle(headCellStyle(wb));
			cell.setCellValue(iter.next().toString());
			i++;
		}
		for (int j = 0; j < 1001; j++) {
			sheet.setDefaultColumnStyle(j, style);
		}
		return sheet;
	}

	/**
	 * 엑셀파일에 Sheet를 만들고 첫줄(Title)을 생성
	 *
	 * @method Name : createSheetTiles
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param wb
	 * @param titles
	 * @param sheetName
	 * @return @. deprecated :
	 */
	public static XSSFSheet createSheetTiles(XSSFWorkbook wb, String[] titles, String sheetName) {
		XSSFSheet sheet = wb.createSheet(sheetName);
		XSSFRow header = sheet.createRow(0);
		XSSFCell cell = null;

		for (int i = 0; i < titles.length; i++) {
			cell = header.createCell((short) i);
			cell.setCellStyle(headCellStyle(wb));
			cell.setCellValue(titles[i]);
		}
		return sheet;
	}

	/**
	 * 엑셀 파일 생성
	 *
	 * @method Name : createExcelFile
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param wb
	 * @param objectList
	 * @param excelMap   @. deprecated :
	 */
	public static void createExcelFile(XSSFWorkbook wb, List objectList, Map excelMap) {
		createExcelFile(wb, objectList, excelMap, "");
	}

	public static void createExcelFile(XSSFWorkbook wb, List objectList, Map excelMap, String sheetNm) {
		createExcelFile(wb, objectList, excelMap, sheetNm, null);
	}

	public static void createExcelFile(XSSFWorkbook wb, List objectList, Map excelMap, String sheetNm, Map opt) {
		createExcelFile(wb, objectList, excelMap, sheetNm, null, null);
	}

	/**
	 * 엑셀 파일 생성
	 *
	 * @method Name : createExcelFile
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param wb
	 * @param objectList
	 * @param excelMap
	 * @param sheetNm
	 * @param opt
	 * @param request    @. deprecated :
	 */
	public static void createExcelFile(XSSFWorkbook wb, List objectList, Map excelMap, String sheetNm, Map opt,
			HttpServletRequest request) {


		XSSFSheet sheet;

		if (null == sheetNm || "".equals(sheetNm)) {
			sheet = createSheetTiles(wb, excelMap);
		} else {
			sheet = createSheetTiles(wb, excelMap, sheetNm);
		}

		Collection attributes = excelMap.values();
		Iterator iter = attributes.iterator();
		String[] attributeList = new String[excelMap.size()];
		int l = 0;
		while (iter.hasNext()) {
			attributeList[l] = iter.next().toString();
			l++;
		}
		short rowNum = 1;
		XSSFCell cell;
		// 셀 스타일 텍스트로 고정
		XSSFCellStyle style = (XSSFCellStyle)wb.createCellStyle();
		XSSFDataFormat dataFormat = (XSSFDataFormat)wb.createDataFormat();
		for (int i = 0; i < objectList.size(); i++) {
			Object object = objectList.get(i);
			XSSFRow row = sheet.createRow(rowNum++);
			for (int j = 0; j < attributeList.length; j++) {

				// dataFormat.getFormat("@") -> @값이 텍스트 스타일.
				style.setDataFormat(dataFormat.getFormat("@"));
				// 줄바꿈 허용 -> \r\n
				style.setWrapText(true);

				cell = row.createCell((short) j);
				cell.setCellStyle(style);
				cell.setCellType(CellType.STRING);
				Object value = null;

				try {
					if (object instanceof Map) {
						value = ((Map) object).get(attributeList[j]);

					} else if (attributeList[j]
							.matches("[a-zA-Z][a-zA-Z0-9_]*\\.[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z][a-zA-Z0-9_]*)*")) {
						value = PropertyUtils.getNestedProperty(object, attributeList[j]);
//						log.debug("value : " + value);

					} else if (attributeList[j].matches("[a-zA-Z][a-zA-Z0-9_]*")) {
						value = PropertyUtils.getSimpleProperty(object, attributeList[j]);
//						log.debug("value : " + value);

					}

					// option
					if (null != opt) {
						String o = (String) opt.get(attributeList[j]);
						if (!StringUtils.isEmpty(o)) {
							if ("dateTime".equals(o)) {
								// value = DateUtil.isDateTime(value.toString(), null, request);

							} else if (o.indexOf("=") > 0) {
								String groupCd = o.split("=")[1];
								if (!StringUtils.isEmpty(groupCd)) {
									// value = CodeUtil.codeName(groupCd, value.toString(), request);
								}
							}
						}
					}

				} catch (Exception e) {
					if (log.isDebugEnabled()) {
						log.debug(e.getMessage());
					}
				}

				cell.setCellValue(new XSSFRichTextString((value == null) ? "" : value.toString()));
			}
		}
	}

	/**
	 * 특정객체의 값을 엑셀파일의 sheet에 cell로 그려준다.
	 *
	 * @method Name : makeExcelCell
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param object
	 * @param attribute
	 * @param sheet
	 * @param rowNum
	 * @param colNum    @. deprecated :
	 */
	public static void makeExcelCell(Object object, String attribute, XSSFSheet sheet, int rowNum, int colNum) {
		XSSFRow row = sheet.createRow(rowNum);
		XSSFCell cell = row.createCell((short) colNum);
		Object value = null;
		try {
			if (attribute.matches("[a-zA-Z][a-zA-Z0-9_]*\\.[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z][a-zA-Z0-9_]*)*")) {
				value = PropertyUtils.getNestedProperty(object, attribute);
			} else if (attribute.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
				value = PropertyUtils.getSimpleProperty(object, attribute);
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage());
			}
		}
		cell.setCellValue(new XSSFRichTextString((value == null) ? "" : value.toString()));
	}

	/**
	 * 특정객체의 값을 엑셀파일의 sheet에 cell로 그려준다.
	 *
	 * @method Name : makeExcelCell
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param value
	 * @param sheet
	 * @param rowNum
	 * @param colNum @. deprecated :
	 */
	public static void makeExcelCell(String value, XSSFSheet sheet, int rowNum, int colNum) {
		XSSFRow row = sheet.createRow(rowNum);
		XSSFCell cell = row.createCell((short) colNum);
		cell.setCellValue(new XSSFRichTextString((value == null) ? "" : value.toString()));
	}

	/**
	 * 액셀파일 다운로드
	 *
	 * @method Name : excelFileDownload
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param wb
	 * @param request
	 * @param response
	 * @deprecated
	 */
	public static void excelFileDownload(XSSFWorkbook wb, HttpServletRequest request, HttpServletResponse response) {
		ServletContext servletContext = request.getSession().getServletContext();

		String file = "";
		if (null != request.getAttribute("file") || request.getAttribute("file") == "") {
			file = (String) request.getAttribute("file");
		} else {
			file = request.getParameter("file");
		}

		String fileName = file.substring(file.lastIndexOf("/") + 1);

		BufferedOutputStream outfile = null;
		String mimetype = servletContext.getMimeType(file);

		response.setContentType(mimetype);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");

		try {
			outfile = new BufferedOutputStream(response.getOutputStream(), FILE_BUFFER_SIZE);
			wb.write(outfile);
			outfile.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outfile != null)
				try {
					outfile.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * 엑셀 내용을 List로 반환한다.
	 *
	 * @method Name : excelFileParsing
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param xslInputStream
	 * @param excelPropList
	 * @param clazz
	 * @return
	 * @throws Exception @deprecated
	 */
	@SuppressWarnings({ "unchecked", "resource" })
	public static List excelFileParsing(InputStream xslInputStream, List excelPropList, Class clazz) throws Exception {
		List dataList = new ArrayList();
		XSSFWorkbook wb = new XSSFWorkbook(xslInputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		int lastNum = sheet.getLastRowNum();
		int lastCellNum = sheet.getRow(0).getLastCellNum();
		FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

		// Excel Header 를 제외하기 위해 시작 index를 1로 지정한다.
		for (int i = 1; i <= lastNum; i++) {
			Object clazzObject = clazz.newInstance();
			for (int k = 0; k <= lastCellNum; k++) {
				try {
					XSSFCell cell = sheet.getRow(i).getCell((short) k);
					switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
					case NUMERIC:
						if( DateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
							cell.setCellValue(formatter.format(cell.getDateCellValue()));
						} else {
							cell.setCellType(CellType.STRING);
						}
						PropertyUtils.setNestedProperty(clazzObject, (String) excelPropList.get(k),
								cell.getStringCellValue());
						break;
					case STRING:
						PropertyUtils.setNestedProperty(clazzObject, (String) excelPropList.get(k),
								cell.getStringCellValue());
						break;
					default:
						PropertyUtils.setNestedProperty(clazzObject, (String) excelPropList.get(k),
								cell.getStringCellValue());
						break;
					}

				} catch (Exception e) {
				}
			}
			dataList.add(clazzObject);
		}
		return dataList;
	}

	/**
	 * 엑셀 내용을 List로 반환한다.
	 *
	 * @param xslInputStream : Excel file의 inputStream
	 * @return List : Title(0행)을 포함한 엑셀데이터
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "resource" })
	public static List excelFileParsing(InputStream xslInputStream) throws Exception {
		List dataList = new ArrayList();

		XSSFWorkbook wb = new XSSFWorkbook(xslInputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		int lastNum = sheet.getLastRowNum();
		int lastCellNum = sheet.getRow(0).getLastCellNum();
		FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

		for (int i = 0; i <= lastNum; i++) {
			List data = new ArrayList();
			for (int k = 0; lastCellNum > k; k++) {
				XSSFCell cell = sheet.getRow(i).getCell((short) k);
				switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
				case NUMERIC:
					data.add(String.valueOf(cell.getNumericCellValue()));
					break;
				case STRING:
					data.add(cell.getRichStringCellValue().getString());
					break;
				default:
					data.add(cell.getRichStringCellValue().getString());
					break;
				}
			}
			dataList.add(data);
		}
		return dataList;
	}

	/**
	 * 엑셀 파일 생성 (대량 데이타 시트 분할)
	 *
	 * @method Name : createBigExcelFile
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param wb
	 * @param objectList
	 * @param excelMap   @. deprecated :
	 */
	public static void createBigExcelFile(XSSFWorkbook wb, List objectList, Map excelMap) {
		createBigExcelFile(wb, objectList, excelMap, "");
	}

	/**
	 * 엑셀 파일 생성 (대량 데이타 시트 분할)
	 *
	 * @method Name : createBigExcelFile
	 * @date : 2019. 04. 09.
	 * @author : will
	 * @method :
	 * @description :
	 * @param wb
	 * @param objectList
	 * @param excelMap
	 * @param sheetNm    @. deprecated :
	 */
	public static void createBigExcelFile(XSSFWorkbook wb, List objectList, Map excelMap, String sheetNm) {

		XSSFSheet sheet;
		XSSFCell cell;
		Collection attributes;
		Iterator iter;
		String[] attributeList;
		short rowNum;
		int l, rowCount, sheetTotalCnt;

		rowCount = EXCEL_SHEET_ROW_COUNT;
		sheetTotalCnt = objectList.size() / rowCount;

		for (int sheetCnt = 0; sheetCnt <= sheetTotalCnt; sheetCnt++) {
			int startRow = sheetCnt * rowCount;
			int endRow = (sheetCnt + 1) * rowCount;

			if (endRow > objectList.size()) {
				endRow = objectList.size();
			}

			if (null == sheetNm || "".equals(sheetNm)) {
				sheet = createSheetTiles(wb, excelMap);
			} else {
				sheet = createSheetTiles(wb, excelMap, sheetNm);
			}

			attributes = excelMap.values();
			iter = attributes.iterator();
			attributeList = new String[excelMap.size()];

			l = 0;
			while (iter.hasNext()) {
				attributeList[l] = iter.next().toString();
				l++;
			}
			rowNum = 1;

			for (int i = startRow; i < endRow; i++) {
				Object object = objectList.get(i);
				XSSFRow row = sheet.createRow(rowNum++);
				for (int j = 0; j < attributeList.length; j++) {
					cell = row.createCell((short) j);
					Object value = null;

					try {
						if (attributeList[j]
								.matches("[a-zA-Z][a-zA-Z0-9_]*\\.[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z][a-zA-Z0-9_]*)*")) {
							value = PropertyUtils.getNestedProperty(object, attributeList[j]);
							log.debug("value : " + value);

						} else if (attributeList[j].matches("[a-zA-Z][a-zA-Z0-9_]*")) {
							value = PropertyUtils.getSimpleProperty(object, attributeList[j]);
							log.debug("value : " + value);

						}
					} catch (Exception e) {
						if (log.isDebugEnabled()) {
							log.debug(e.getMessage());
						}
					}

					cell.setCellValue(new XSSFRichTextString((value == null) ? "" : value.toString()));
				}
			}
		}
	}

	// 엑셀 파일 읽기
	public static List<Object> excelParsing(String filePath, String fileName, String[] params,
			HashMap<String, String> defaultData) {
		List<Object> result = new ArrayList<Object>();

		// 확장자 xlx 와 xlxs에 따라 분기
		if (fileName.indexOf("xlsx") > 0) {
			result = excelXlxsParsing(filePath, fileName, params, defaultData);

		} else {
			result = excelXlxParsing(filePath, fileName, params, defaultData);
		}

		return result;
	}

	@SuppressWarnings("resource")
	public static List<Object> excelXlxParsing(String filePath, String fileName, String[] params,
			HashMap<String, String> defaultData) {
		List<Object> result = new ArrayList<Object>();

		try {
			FileInputStream fis = new FileInputStream(filePath + File.separator + fileName);

			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			int rowindex = 0;
			int columnindex = 0;

			// sheet 는 첫번째것만 가져와 읽는다
			HSSFSheet sheet = workbook.getSheetAt(0);

			// 총 rows를 구한다
			int rows = sheet.getPhysicalNumberOfRows();
			HashMap<String, String> map = null;

			for (rowindex = 1; rowindex < rows; rowindex++) {
				if (rowindex == 1)
					continue;
				HSSFRow row = sheet.getRow(rowindex);

				map = new HashMap<String, String>();
				if (row != null) {
					map.putAll(defaultData);
					int cells = row.getPhysicalNumberOfCells();

					for (columnindex = 0; columnindex <= cells; columnindex++) {
						HSSFCell cell = row.getCell(columnindex);

						if (cell != null) {
							map.put(params[columnindex], excelData(cell));
						} else {
							map.put(params[columnindex], "");

						}

					}

					result.add(map);
				}
				// log.debug("엑셀 rows : " + rowindex);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("resource")
	public static List<Object> excelXlxsParsing(String filePath, String fileName, String[] params,
			HashMap<String, String> defaultData) {
		List<Object> result = new ArrayList<Object>();

		try {
			XSSFWorkbook work = new XSSFWorkbook(new File(filePath + File.separator + fileName));

			// sheet 는 첫번째것만 가져와 읽는다
			XSSFSheet sheet = work.getSheetAt(0);

			// 총 rows를 구한다
			int rows = sheet.getPhysicalNumberOfRows();
			HashMap<String, String> map = null;

			for (int rownum = 0; rownum < rows; rownum++) {
				if (rownum == 0)
					continue;
				XSSFRow row = sheet.getRow(rownum);
				map = new HashMap<String, String>();
				if (row != null) {
					if (null != defaultData) {
						map.putAll(defaultData);
					}
					// int cells = row.getPhysicalNumberOfCells();
					int cells = row.getLastCellNum();
					log.debug("총셀수 : " + cells);
					for (int cellnum = 0; cellnum < cells; cellnum++) {
						log.debug("셀수 : " + cellnum);
						XSSFCell cell = row.getCell(cellnum);
						if (cell != null) {
							map.put(params[cellnum], excelData(cell));
						} else {
							map.put(params[cellnum], "");
						}
					}
					log.debug(map.toString());
					result.add(map);
				}
				// log.debug("엑셀 rows : " + rownum);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}

		return result;
	}

	private static String excelData(Cell cell) {
		String result = "";
		switch (cell.getCellType()) {

		case FORMULA:
			switch (cell.getCachedFormulaResultType()) {
			case STRING:
				result = cell.getStringCellValue();
				break;
			case NUMERIC:
				result = cell.getNumericCellValue() + "";
				break;
			default:
			}
			break;
		case NUMERIC:
			result = String.valueOf(new Double(cell.getNumericCellValue()).intValue());
			// log.debug("CELL_TYPE_NUMERIC :" + result);
			break;
		case STRING:
			result = cell.getStringCellValue() == null ? new String("") : cell.getStringCellValue();
			// log.debug("CELL_TYPE_STRING :" + result);
			break;
		case BLANK:
			result = "";
			// log.debug("CELL_TYPE_BLANK :" + result);
			break;
		case BOOLEAN:
			result = Boolean.toString(cell.getBooleanCellValue());
			// log.debug("CELL_TYPE_BOOLEAN :" + result);
			break;
		case ERROR:
			result = "";
			// log.debug("CELL_TYPE_ERROR :" + result);
			break;
		default:
			// log.debug("default :" + result);
			break;
		}

		return result;
	}

	// 2007 이외 파일
	public LinkedList<LinkedList<String>> readExcel(String excel) throws IOException {

		LinkedList<LinkedList<String>> dataList = null;
		// check file
		File file = new File(excel);
		if (!file.exists() || !file.isFile() || !file.canRead()) {
			throw new IOException(excel);
		}

		// Workbook
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
		try {
			// Text Extraction
			ExcelExtractor extractor = new ExcelExtractor(wb);
			extractor.setFormulasNotResults(true);
			extractor.setIncludeSheetNames(false);
//			System.out.println( extractor.getText() );

			// Getting cell contents
			dataList = new LinkedList<LinkedList<String>>();
			int idx_row = 0;
			for (Row row : wb.getSheetAt(0)) {
				if (idx_row == 0) {
					idx_row++;
					continue;
				}
				LinkedList<String> dataLink = new LinkedList<String>();
				for (Cell cell : row) {
					// Cell Reference
					CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
//					System.out.println("row.getRowNum()::"+row.getRowNum());
//					System.out.println("cell.getColumnIndex()::"+cell.getColumnIndex());
//					System.out.print(cellRef.formatAsString());

//					System.out.print(" - ");
					String data = "";
					switch (cell.getCellType()) {

					case STRING:
						data = cell.getRichStringCellValue().getString();
//							System.out.println( cell.getRichStringCellValue().getString() );
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//								System.out.println(cell.getDateCellValue());
							data = sdf.format(cell.getDateCellValue()).toString();
						} else {
//							System.out.println(cell.getNumericCellValue());
							data = cell.getNumericCellValue() + "";
						}
						break;
					case BOOLEAN:
//							System.out.println(cell.getBooleanCellValue());
						if (cell.getBooleanCellValue()) {
							data = "1";
						} else {
							data = "0";
						}
						break;
					case FORMULA:
						if (((int) Double.parseDouble(cell.getCellFormula())) == Double
								.parseDouble(cell.getCellFormula())) {
							data = (int) Double.parseDouble(cell.getCellFormula()) + "";
						} else {
							data = cell.getCellFormula() + "";
						}
//							System.out.println(data);

						break;
					default:
//							System.out.println("");
						break;
					} // switch end

					dataLink.add(data);

				} // Cell cell : row end
				dataList.add(dataLink);
			}

			log.debug("########### dataList::" + dataList.toString());

		} catch (Exception e) {
			log.error("", e);
		}
		return dataList;
	}

	// 2007 파일
	public LinkedList<LinkedList<String>> readExcel2007(String excelFile) throws IOException {

		LinkedList<LinkedList<String>> dataList = null;
		File file = new File(excelFile);
		if (!file.exists() || !file.isFile() || !file.canRead()) {
			throw new IOException(excelFile);
		}

		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		try {
			XSSFExcelExtractor extractor = new XSSFExcelExtractor(wb);
			extractor.setFormulasNotResults(false);
			extractor.setIncludeSheetNames(false);

			dataList = new LinkedList<LinkedList<String>>();
			int rowCnt = 0;
			for (Row row : wb.getSheetAt(0)) {
				if (rowCnt == 0) {
					rowCnt++;
					continue;
				}

				LinkedList<String> dataLink = new LinkedList<String>();

				for (int i = 0; i < row.getLastCellNum(); i++) {

					Cell cell = row.getCell(i);
					DataFormatter fmt = new DataFormatter();

					// Cell Reference
//					CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
//					System.out.print(cellRef.formatAsString());
//					System.out.print(" - ");
					String data = "";

//					log.debug("CELL TYPE : {}", cell.getCellType());
					if (cell == null) {
						data = "";
					} else {
						switch (cell.getCellType()) {
						case STRING:
							data = cell.getRichStringCellValue().getString();
							// System.out.println( cell.getRichStringCellValue().getString() );
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
								// System.out.println(cell.getDateCellValue());
								data = sdf.format(cell.getDateCellValue()).toString();
							} else {

								if (((int) cell.getNumericCellValue()) == cell.getNumericCellValue()) {
									data = (int) cell.getNumericCellValue() + "";
								} else {
									data = cell.getNumericCellValue() + "";
								}

								if (!StringUtils.isNumeric(data)) {
									data = fmt.formatCellValue(cell).toString();
								}

								// System.out.println(data);
								// data = cell.getNumericCellValue()+"";
							}
							break;
						case BOOLEAN:
//								System.out.println(cell.getBooleanCellValue());
							if (cell.getBooleanCellValue()) {
								data = "1";
							} else {
								data = "0";
							}
							break;
						case BLANK:
							data = "";
							break;
						case FORMULA:
							// System.out.println(cell.getCellFormula());
							data = cell.getCellFormula() + "";
							break;
						default:
							data = "";
							// System.out.println("");
							break;
						}// switch end
					}
					dataLink.add(data);
				} // Cell cell : row end
				dataList.add(dataLink);
			} // Row row : wb.getSheetAt(i) end

			log.debug("########### dataList::" + dataList.toString());

		} catch (Exception e) {
			log.error("", e);
		}
		return dataList;
	}

	public static String urlDecodeFileName(String userAgentHeader, String fileName) throws UnsupportedEncodingException {
		if (userAgentHeader.indexOf("MSIE") > -1) {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else if (userAgentHeader.indexOf("Trident") > -1) {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else if (userAgentHeader.indexOf("Chrome") > -1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < fileName.length(); i++) {
				char c = fileName.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			fileName = sb.toString();
		} else if (userAgentHeader.indexOf("Opera") > -1) {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (userAgentHeader.indexOf("Safari") > -1) {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
			fileName = URLDecoder.decode(fileName);
		} else {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
			fileName = URLDecoder.decode(fileName);
		}

		return fileName;
	}

	public static void addFileDownloadHeader(HttpServletResponse response, String fileName) {
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");
		response.setHeader("Content-Type",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
	}

	public static void addErrorFileDownloadHeader(HttpServletResponse response) {
		response.setHeader("Set-Cookie", "fileDownload=false; path=/");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Content-Type", "text/html; charset=utf-8");
	}
}
