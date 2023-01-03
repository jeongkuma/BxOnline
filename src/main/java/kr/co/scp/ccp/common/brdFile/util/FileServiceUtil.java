package kr.co.scp.ccp.common.brdFile.util;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.FileUtils;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileServiceUtil {
	
	@Autowired
	BrdFileListDAO brdFileListDAO;
	
	@Value("${file.upload-dir}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;
	
	public TbIoTBrdFileListDTO TbIoTLibBrdDTO(HttpServletRequest request, ArrayList<String[]> fileList, String fileName) {
		
		TbIoTBrdFileListDTO tbIoTBrdFileListDTO = null;
		
		for (String[] arrStr : fileList) {
			for (String str : arrStr) {
				if(arrStr[1].equals(fileName)) {
					tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
					tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace("C:/Temp/uploads/", ""));
					tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
					tbIoTBrdFileListDTO.setFileName(arrStr[2]);
					tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				}
			}
		}
		return tbIoTBrdFileListDTO;
	}
	
	public void saveFiles(HttpServletRequest request, String ContentType, String contentSeq) {
		
		TbIoTBrdFileListDTO tbIoTBrdFileListDTO = null;
		ArrayList<String[]> fileList = FileUtils.uploadMutilpleFile(request, FILE_UPLOAD_PATH, FILE_MAX_SIZE);
		for (String[] arrStr : fileList) {
			for (String str : arrStr) {				
				tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
				tbIoTBrdFileListDTO.setContentSeq(contentSeq);
				tbIoTBrdFileListDTO.setContentType(ContentType);
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
			}
			brdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
		}
	}
	
	public void saveFiles(HttpServletRequest request, String ContentType, String contentSeq, String uploadPath) {
		
		TbIoTBrdFileListDTO tbIoTBrdFileListDTO = null;
		ArrayList<String[]> fileList = FileUtils.uploadMutilpleFile(request, uploadPath, FILE_MAX_SIZE);
		for (String[] arrStr : fileList) {
			for (String str : arrStr) {				
				tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
				tbIoTBrdFileListDTO.setContentSeq(contentSeq);
				tbIoTBrdFileListDTO.setContentType(ContentType);
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(uploadPath, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
			}
			brdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
		}
	}
	
	public List<TbIoTBrdFileDTO> selectFileList(TbIoTBrdFileDTO tbIoTBrdFileDTO) throws BizException {		
		return brdFileListDAO.retrieveTbIoTBrdFileList(tbIoTBrdFileDTO);
	};
	
	public void deleteDbAndFile(HttpServletRequest request, String uploadPath, TbIoTBrdFileListDTO tbIoTBrdFileListDTO) throws BizException {
		TbIoTBrdFileListDTO findFile = null;
		StringBuilder fullPath = new StringBuilder();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"brdFileListDAO.selectFileName");
		try {
			findFile = brdFileListDAO.selectFileName(tbIoTBrdFileListDTO);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		
		fullPath.append(uploadPath)
		        .append(findFile.getFilePath())
		        .append(File.separator);
		
		
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"brdFileListDAO.deleteTbIoTBrdFile");
		
		try {
			brdFileListDAO.deleteTbIoTBrdFile(tbIoTBrdFileListDTO);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}		
		// 파일 삭제		
		FileUtils.deleteFile(request, fullPath.toString(), findFile.getFileName());
		
	}
	
}