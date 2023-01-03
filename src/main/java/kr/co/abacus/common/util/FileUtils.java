package kr.co.abacus.common.util;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.*;

@Slf4j
public class FileUtils {

	/**
	 * 파일 확장자 체크
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExtension(String fileName, List<String> extensions) {
		String[] fileSplit = fileName.split("\\.");
		if (extensions.contains(fileSplit[fileSplit.length - 1].toLowerCase())) {
			return true;
		}
		return false;
	}

	public static boolean checkFileExt(HttpServletRequest request, List<String> extensions) {
		boolean flag = true;
		Map<String, MultipartFile> files = new HashMap<String, MultipartFile>();
		try {
			MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest) request;
			files = multipartReq.getFileMap();
		} catch (Exception e) {
			log.debug("No Upload file");
		}

		for (MultipartFile file : files.values()) {
			if (file != null) {
				String realFileNm = file.getOriginalFilename();
				if (!realFileNm.equals("")) {
					if (!isFileExtension(realFileNm, extensions)) {
						return false;
					}
				}
			}
		}

		return flag;
	}

	private static List<String> fileExtensions = new ArrayList<String>(); // 업로드 제한 파일 확장자

	public static List<String> getFileExtensions() {
		return fileExtensions;
	}

	public static void setFileExtensions(List<String> fileExtensions) {
		FileUtils.fileExtensions = fileExtensions;
	}

	static {
		fileExtensions.addAll(Arrays.asList(new String[] { "asp", "jsp", "html", "htm", "com", "exe", "sh", "php" }));
	}

	public static ArrayList<String[]> uploadMutilpleFile(HttpServletRequest request, String fileUploadLocation,
			long fileSize) {
		ArrayList<String[]> fileList = new ArrayList<String[]>();

		Map<String, MultipartFile> files = new HashMap<String, MultipartFile>();

		try {
			MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest) request;
			files = multipartReq.getFileMap();
		} catch (Exception e) {
			log.debug("No Upload file");
		}

		fileUploadLocation += DateUtils.timeStamp(ComConstant.DATE_FORMAT);
		for (MultipartFile file : files.values()) {
			if (file != null) {
				String realFileNm = file.getOriginalFilename();
				if (!realFileNm.equals("")) {
					String saveFileNm = changeToSaveFileName(realFileNm);
					fileList.add(FileUtils.uploadToOneFile(request, fileUploadLocation, file, saveFileNm, fileSize));

				}
			}
		}

		return fileList;
	}

	public static String[] uploadToOneFile(HttpServletRequest req, String saveFileLocation, MultipartFile file,
			String saveFileName, long fileSize) {
		String[] fileName = new String[4];
		fileName[0] = saveFileLocation;
		fileName[3] = String.valueOf(file.getSize()); // 2019.03.29 add 파일 사이즈
		String tempSaveFileLocation = saveFileLocation;
		File dir = null;
		if (file.getSize() > fileSize) {
			throw new BizException("fileSizeExceed");
		}

		tempSaveFileLocation = saveFileLocation;
		if (!"".equals(file.getOriginalFilename())) {
			fileName[1] = file.getOriginalFilename();

			if (fileName[1].indexOf("/") > 0 || fileName[1].indexOf("\\") > 0) {
				if (fileName[1].lastIndexOf("\\") > 0) {
					fileName[1] = fileName[1].substring(fileName[1].lastIndexOf("\\") + 1);
				} else if (fileName[1].lastIndexOf("/") > 0) {
					fileName[1] = fileName[1].substring(fileName[1].lastIndexOf("/") + 1);
				}
			}

			if (!checkFileExtension(saveFileName)) { // 업로드 제한 확인
				dir = new File(tempSaveFileLocation);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				try {
					fileName[2] = saveFileName;
					FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(new StringBuilder()
							.append(tempSaveFileLocation).append(File.separator).append(saveFileName).toString()));
				} catch (Exception e) {
					throw new BizException("fileUploadFail");
				}
			} else {
				// 업로드 제한일 경우
				throw new BizException("noUploadFile");
			}

		}
		return fileName;
	}

	public static String changeToSaveFileName(String fileName) {
		String[] fileNameSplit = fileName.split("\\.");
		Random rand = new Random(System.currentTimeMillis());
		return new StringBuilder().append(System.currentTimeMillis()).append(Math.round(rand.nextDouble() * 10000))
				.append(".").append(fileNameSplit[fileNameSplit.length - 1]).toString();
	}

	public static boolean checkFileExtension(String fileName) {
		String[] fileSplit = fileName.split("\\.");
		if (fileExtensions.contains(fileSplit[fileSplit.length - 1].toLowerCase())) {
			return true;
		}
		return false;
	}

	public static void fileDownload(HttpServletRequest request, HttpServletResponse response, String filePath,
			String fileNm, String realFileNm) {
		String realFilePath = "";
//		realFilePath = new StringBuilder().append(filePath).append(File.separator).append(fileNm).toString();
		realFilePath = new StringBuilder().append(filePath).append(fileNm).toString();

		log.debug("파일 경로 : " + realFilePath);

		File file = new File(realFilePath);
		if (file.exists()) {
			log.debug("파일 존재");
			String header = request.getHeader("User-Agent");
//			String userCharset = request.getCharacterEncoding();
			String docName = "";
			OutputStream os = null;
			InputStream is = null;
			try {

				if (header.indexOf("MSIE") > -1) {
					docName = URLEncoder.encode(realFileNm, "UTF-8").replaceAll("\\+", "%20");
				} else if (header.indexOf("Trident") > -1) {
					docName = URLEncoder.encode(realFileNm, "UTF-8").replaceAll("\\+", "%20");
				} else if (header.indexOf("Chrome") > -1) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < realFileNm.length(); i++) {
						char c = realFileNm.charAt(i);
						if (c > '~') {
							sb.append(URLEncoder.encode("" + c, "UTF-8"));
						} else {
							sb.append(c);
						}
					}
					docName = sb.toString();
				} else if (header.indexOf("Opera") > -1) {
					docName = "\"" + new String(realFileNm.getBytes("UTF-8"), "8859_1") + "\"";
				} else if (header.indexOf("Safari") > -1) {
					docName = "\"" + new String(realFileNm.getBytes("UTF-8"), "8859_1") + "\"";
					docName = URLDecoder.decode(docName);
				} else {
					docName = "\"" + new String(realFileNm.getBytes("UTF-8"), "8859_1") + "\"";
					docName = URLDecoder.decode(docName);
				}

				/** Set Response Header */
				response.setContentType("application/octet-stream; charset=utf-8");
//				response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				response.setContentLength((int) file.length());
				response.setHeader("Content-Disposition", "attachment;fileName=" + "\"" + docName + "\"" + ";");
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.setCharacterEncoding("UTF-8");

				os = response.getOutputStream();
				is = null;
				is = new FileInputStream(file);
				FileCopyUtils.copy(is, os);
				os.flush();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new BizException("fileNotFound");
			} catch (IOException e) {
				e.printStackTrace();
				throw new BizException("fileDownloadFail");
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			log.debug("파일 없음");
			throw new BizException("fileNotFound");
		}

	}

	public static void deleteFile(HttpServletRequest request, String fullPath, String file) {
		String realPath = "";
		realPath = fullPath + file;
		File isDeleteFile = new File(realPath);
		if (isDeleteFile.exists()) {
			isDeleteFile.delete();
		}
	}

	public static void copy(String fOrg, String fTarget, String fTargetName) {
		try {
			File fsTarget = new File(fTarget);
			if (!fsTarget.exists())
				fsTarget.mkdirs();
			String fsTargetName = new StringBuilder().append(fsTarget).append(File.separator).append(fTargetName)
					.toString();

			FileInputStream inputStream = new FileInputStream(fOrg);
			FileOutputStream outputStream = new FileOutputStream(fsTargetName, true);

			FileChannel fcin = inputStream.getChannel();
			FileChannel fcout = outputStream.getChannel();

			long size = fcin.size();

			fcin.transferTo(0, size, fcout);

			fcout.close();
			fcin.close();
			inputStream.close();
			outputStream.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new BizException("fileNotCopy");
		}
	}
}
