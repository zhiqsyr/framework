package org.zhiqsyr.framework.utils.io.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.zhiqsyr.framework.utils.i18n.properties.PropertiesUtils;
import org.zhiqsyr.framework.utils.io.file.FileUtils;


public class ZipUtils {

	/**
     * 将指定的文件列表，存入ZIP中
     * 
     * @param outputFile	生成的ZIP存放的位置
     * @param fileList		存入ZIP中的文件列表
     * ant下的zip工具默认压缩编码为UTF-8编码，而winRAR软件压缩是用的windows默认的GBK或者GB2312编码
     * 用ant压缩后放到windows上面会出现中文文件名乱码，用winRAR压缩的文件，用ant解压也会出现乱码，
     * 所以，在用ant处理winRAR压缩的文件时，要设置压缩编码
     */
   public static File putFilesToZIP(String outputFile,List<String> fileList){
         try{
             File zipFile = new File(outputFile);
             ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));  //将zip与一个输出流关联
             //设置压缩编码
             zipOutputStream.setEncoding("GBK");	//设置为GBK后在windows下就不会乱码了，如果要放到Linux或者Unix下就不要设置了

             for(int i = 0;i < fileList.size(); i ++){
            	 File eachFile = new File(fileList.get(i).toString());
                 if(eachFile.exists()){
                     createZIP(zipOutputStream,eachFile,"");
                 }
             }
             zipOutputStream.close();
             
             return zipFile;
         }catch (Exception ex) {
             throw new RuntimeException(ex.getMessage(), ex);
         }
    }

    /**
     * 创建ZIP文件，并添加文件到zip中（如果souceFile是文件夹，只存储文件夹中的内容）
     * 
     * @param out
     * @param souceFile
     * @param baseDirectory	zip文件目录结构,顶层为null
     * @return
     */
	public static void createZIP(ZipOutputStream out,File souceFile,String baseDirectory){
        try{
            if(souceFile.isDirectory()){
                File[] files = souceFile.listFiles();
                out.putNextEntry(new ZipEntry(baseDirectory + "/"));
                baseDirectory = baseDirectory.length() == 0 ? "" : baseDirectory + "/";
                for(File file : files){
                    createZIP(out,file,baseDirectory + file.getName());
                }
            
            }else{
                if(baseDirectory.length() > 0){
                    out.putNextEntry(new ZipEntry(baseDirectory));
                }else{
                    out.putNextEntry(new ZipEntry(souceFile.getName()));
                }
                FileInputStream fileInputStream = new FileInputStream(souceFile);
                byte[] bytes = new byte[1024];      //将要写入zip文件的信息变换为byte数组
                int b = 0;
                while((b = fileInputStream.read(bytes)) != -1){
                    out.write(bytes,0,b);
                }
                fileInputStream.close();
            }
        }catch (Exception ex) {
        	ex.printStackTrace();
        	throw new RuntimeException(ex.getMessage(), ex);
        }
    }
	
	/**
	 * 移动tempZipFile到配置位置，返回格式化路径，如'/201403/20140310MU5353ZSPDZJSY.zip'
	 *
	 * @param tempZipFile
	 * @return
	 */
	public static String formatZipFilePath(File tempZipFile){
        if(tempZipFile.exists()){
            String configZipFileUrl = PropertiesUtils.getValueInSystem("baggage.folder.path");
            if(!configZipFileUrl.endsWith("/")) configZipFileUrl += "/";
            configZipFileUrl = configZipFileUrl.replace("\\","/");
            
            String targetZipFileDir = configZipFileUrl + "201505/";
            File targetZipFolder = new File(targetZipFileDir);
            if((!targetZipFolder.exists()) || (!targetZipFolder.isDirectory())){//文件夹不存在
            	targetZipFolder.mkdirs();
            }

            String targetZipFilePath = targetZipFileDir + tempZipFile.getName();
            File targetZipFile = new File(targetZipFilePath);
            // 目标文件已经存在，需要先删除，防止renameTo方法报错
            if(targetZipFile.exists()) {
            	FileUtils.deleteFile(targetZipFile.getPath());
            }
            
            // 临时zip移动到targetZipFile位置，删除临时zip
            tempZipFile.renameTo(targetZipFile);
            
            return targetZipFilePath.replace(configZipFileUrl, "/");
        }
        
        return null;
    }
	
}
