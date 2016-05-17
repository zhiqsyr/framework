package org.zhiqsyr.framework.utils.net.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.zhiqsyr.framework.utils.io.file.FileUtils;


public class FtpUtils {

	/**
	 * 登录FTP服务器
	 *
	 * @param ftpHost
	 * @param ftpUserName
	 * @param ftpUserPwd
	 * @return
	 */
	public static FTPClient loginFtp(String ftpHost,String ftpUserName,
			String ftpUserPwd){
		FTPClient ftpClient = new FTPClient();
		
		try {
			ftpClient.connect(ftpHost);
			ftpClient.setConnectTimeout(300000);  //连接超时为5分钟
			ftpClient.setDataTimeout(300000);     //设置传输超时时间为5分钟
			
			if(ftpClient.login(ftpUserName, ftpUserPwd)) {	// 登录
				int reply = ftpClient.getReplyCode();
				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return ftpClient;
	}
	
	/**
	 * 获取从上次文件获取后到现在时间点远程ftp中新增或修改的文件
	 *
	 * @param ftpHost
	 * @param ftpUserName
	 * @param ftpUserPwd
	 * @param remoteDir		ftp远程目录
	 * @param lastRecvTime	上次获取文件的时刻
	 * @return
	 */
    public static FTPFile[] getFtpFile(String ftpHost,String ftpUserName,
			String ftpUserPwd,String remoteDir,Date lastRecvTime){
    	FTPClient ftpClient = loginFtp(ftpHost, ftpUserName, ftpUserPwd);
    	if (ftpClient == null) {
			return null;
		}
    	
    	try {
    		ftpClient.enterLocalPassiveMode();
        	ftpClient.changeWorkingDirectory(remoteDir);
        	
        	final Date recvTime = lastRecvTime;
        	FTPFile[] ftpFiles = ftpClient.listFiles(remoteDir, new FTPFileFilter() {
				
				@Override
				public boolean accept(FTPFile file) {
					if (file.getTimestamp().getTime().after(recvTime)) {
						return true;
					}
					return false;
				}
			});
        	
            return ftpFiles;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } finally {
        	logoutFTPClient(ftpClient);
        }
    }
    
    /**
     * 将FTP上面的文件获取到本地，并返回本地路径releasePath
     *
     * @param ftpHost
     * @param ftpUserName
     * @param ftpUserPwd
     * @param remoteDir	ftp远程目录
     * @param fileName	需要下载的文件名称
     * @param localDir	下载到本地的目录
     * @return
     */
	public static String downloadFtpFile(String ftpHost,String ftpUserName,String ftpUserPwd,
			String remoteDir,String fileName,String localDir) {
		FTPClient ftpClient = loginFtp(ftpHost, ftpUserName, ftpUserPwd);
		if (ftpClient == null) {
			return "";
		}
		
		String releasePath = "";
		if(!localDir.endsWith("/")) localDir += "/";
		if(!remoteDir.endsWith("/")) remoteDir += "/";
		
		File localFile = null;
		FileOutputStream outputStream = null;
		try {
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(remoteDir);
			
			FTPFile[] remoteFiles = ftpClient.listFiles(fileName);
			if(remoteFiles.length > 0){
				FileUtils.createFolder(localDir);
				localFile = new File(localDir + remoteFiles[0].getName());
				outputStream = new FileOutputStream(localFile);
				
				ftpClient.retrieveFile(remoteDir + remoteFiles[0].getName(), outputStream);
				releasePath = localFile.getPath();
			}
			
			return releasePath;
        } catch (Exception ex) {
        	ex.printStackTrace();
        	throw new RuntimeException(ex.getMessage(), ex);
        } finally {
        	try {
        		if(outputStream != null){
        			outputStream.close();
        		}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(), e);
			}
        	
        	logoutFTPClient(ftpClient);
        }
	}
	
    /**
     * 登出
     *
     * @param ftpClient
     */
    public static void logoutFTPClient(FTPClient ftpClient){
		try {
		    ftpClient.logout();
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException(e.getMessage(), e);
		} finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    throw new RuntimeException(ioe.getMessage(), ioe);
                }
            }
		}
	}
	
}
