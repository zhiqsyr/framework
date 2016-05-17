package org.zhiqsyr.framework.utils.excel.imp.jxl.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import org.zhiqsyr.framework.utils.excel.imp.jxl.service.store.FileStoreService;
import org.zhiqsyr.framework.utils.excel.spring.ApplicationContextHolder;


public class FileSave implements Serializable {
	
	private static final long serialVersionUID = -4262295944260048950L;
	
	private String id;
	
	private String fileName;
	private String fileSavePath;
	
	private String reportType;
	private Boolean isSuccess;
	
	private String userName;
	private Date uploadTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * 获取文件的输入流
	 * 
	 * @return
	 */
	public InputStream getFileInputStream() {
		FileStoreService storeService = ApplicationContextHolder.getBean(FileStoreService.class);
		String dir = storeService.getSaveDir();
		File file = new File(dir, this.getFileSavePath());
		try {
			return new FileInputStream(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
