package org.zhiqsyr.framework.utils.io.pdf.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.zhiqsyr.framework.utils.io.file.FileUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtils {

	public static boolean writeFileContentToPDF(File inputFile, String outPdfFilePath){
        if (inputFile.exists() && !inputFile.isDirectory()){
            String contentStr = FileUtils.readFileContent(inputFile.getPath());
            return writeStrToPDF(outPdfFilePath,contentStr);
        }
        return false;
    }
	
	public static boolean writeStrToPDF(String outPDFFilePath, String contentStr){
        boolean flag = false;
        try{
            OutputStream file = new FileOutputStream(new File(outPDFFilePath));
            Document document = new Document(PageSize.A2);
            PdfWriter.getInstance(document,file);
            document.open();

            if (StringUtils.isBlank(contentStr))
                contentStr = "";
            else
                flag = true;
            
            // 改用FontSelector后导致字体间距有问题，需调整放行单签字位置
            FontSelector selector = new FontSelector();
            selector.addFont(FontFactory.getFont("C:/WINDOWS/Fonts/COUR.TTF",18,Font.BOLD));
            selector.addFont(FontFactory.getFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED,16,Font.BOLD));
            Phrase ph = selector.process(contentStr);
            document.add(new Paragraph(ph));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
        return flag;
    }
	
}
