package org.zhiqsyr.framework.utils.io.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

/**
 * 文件工具类
 * 
 * @author dongbz 2015-11-20
 */
public class FileUtils {

	/**
	 * 创建文件夹
	 * 
	 * @param folderPath
	 *            文件夹路径 如 c:/fqf/xqw
	 */
	public static boolean createFolder(String folderPath) {
		try {
			File folder = new File(folderPath);
			// 判断文件夹是否存在
			if (!folder.exists()) {
				folder.mkdirs(); // 创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
			}

			return true;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 删除文件（夹）
	 * 
	 * @param filePath
	 *            需要删除的文件（夹）路径 如 c:/fqf.txt
	 */
	public static void deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				if (file.isDirectory()) { // 文件夹
					String[] allfiles = file.list(); // 当前文件夹下的所有文件
					if (null != allfiles && allfiles.length > 0) {
						File childFile = null;
						for (int i = 0; i < allfiles.length; i++) {
							childFile = new File(file.getAbsolutePath() + File.separator + allfiles[i]);
							deleteFile(childFile.getAbsolutePath());
						}
					}
				}

				if (!file.delete()) {
					for (int i = 0; i < 10; i++) { // 最多尝试删除10次
						System.gc();
						if (file.delete()) return;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 读取指定的 inputFilePath 文件内容
	 * 
	 * @param filePath 读取的文件路径
	 * @return 文件内容
	 */
	public static String readFileContent(String filePath) {
		StringBuffer content = new StringBuffer(); // 文件内容
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader isReader = new InputStreamReader(fis, "GBK");
			BufferedReader bReader = new BufferedReader(isReader);

			String line = bReader.readLine();
			while (line != null) {
				content.append(line);
				content.append("\n");
				
				line = bReader.readLine();
			}

			bReader.close();
			isReader.close();
			fis.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return content.toString();
	}

	/**
	 * 复制单个文件
	 * 
	 * @param outFolderFilePath
	 * @param filePath
	 * @param fileName
	 */
	public static void copyFile2NewFolder(String outFolderFilePath,
			String filePath, String fileName) {
		try {
			URL url = new URL(filePath);
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			httpConnection.connect();
			BufferedInputStream bis = new BufferedInputStream(httpConnection.getInputStream());
			
			File file = new File(outFolderFilePath + File.separator + fileName + filePath.substring(filePath.lastIndexOf(".")));
			FileOutputStream fos = new FileOutputStream(file);
			
			int BUFFER_SIZE = 4096;
			int size = 0;
			byte[] buf = new byte[BUFFER_SIZE];
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			
			fos.close();
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取文件大小
	 * 
	 * @param file
	 * @return
	 */
	public static Long getFileSize(File file) {
		return file.length() / 1024;
	}

	public static byte[] getFileBytes(File file) {
		try {
			FileInputStream stream = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			
			out.close();
			stream.close();
			return out.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取文件md5
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
		
		byte[] md5Bytes = digest.digest();
		StringBuilder hexValue = new StringBuilder();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Long.toHexString(val));
		}
		return hexValue.toString();
	}

}
