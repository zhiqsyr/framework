package org.zhiqsyr.framework.utils.tdc.zxing;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.zhiqsyr.framework.utils.cache.Constants;
import org.zhiqsyr.framework.utils.i18n.properties.PropertiesUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 采用 ZXing 的二维码生成器  
 * 
 * @author dongbz 2015-11-19
 */
public class ZXingUtils {

	/** 二维码的图片格式 */
	private static final String FORMAT = PropertiesUtils.getValueInSystem("zxing.pic.format");
	/** 二维码内容所有编码 */
	private static final String CHARACTER_SET = PropertiesUtils.getValueInSystem("zxing.character.set");
	
	public static void main(String[] args) {
		File file = generate("c://tmp", "Q: What's your name ?\nA: Xiao Ming", 300, 300);
		System.out.println(parse(file));
		
//		file = generateWithLogo("c://tmp", "c://tmp//test.jpg", "What are you nongshalai", 200, 200);
//		System.out.println(parse(file));
	}
	
	/**
	 * 生成普通二维码（不含logo）
	 *
	 * @param folder 二维码文件夹全路径（绝对路径d:/qr，或者部署容器时相对路径/data/project/qr）
	 * @param contents
	 * @param width
	 * @param height
	 * @return
	 *
	 * @author dongbz, 2015-11-20
	 */
	public static File generate(String folder, String contents, int width, int height) {  
        try {  
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, width, height, getEncodeHintType());
			
			File pic = new File(folder + File.separator + UUID.randomUUID().toString() + Constants.POINT + FORMAT);
            MatrixToImageWriter.writeToFile(bitMatrix, FORMAT, pic);  
            
            return pic;
        } catch (Exception e) {  
            throw new RuntimeException(e.getMessage(), e);
        }  
    }  
	
	/**
	 * 生成带有 logo 的二维码
	 *
	 * @param folder
	 * @param logoPicPath
	 * @param contents
	 * @param width
	 * @param height
	 *
	 * @author dongbz, 2015-11-20
	 */
	public static File generateWithLogo(String folder, String logoPicPath, String contents, int width, int height) {
		try {
			/** 首先校验 **/
			File logoPic = new File(logoPicPath);
			
			/** 生成二维码 BufferedImage 图片 **/
			BufferedImage image = getQRCodeBufferedImage(contents, width, height);			

			/** 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码 **/
			BufferedImage logo = ImageIO.read(logoPic); // 读取 logo 图片
			int logoWidth = logo.getWidth(null) > image.getWidth() * 2 / 10 
					? (image.getWidth() * 2 / 10) 
					: logo.getWidth(null), logoHeight = logo.getHeight(null) > image.getHeight() * 2 / 10 
						? (image.getHeight() * 2 / 10) 
						: logo.getWidth(null);

			/** logo 放在中心 **/
			int x = (image.getWidth() - logoWidth) / 2; 
			int y = (image.getHeight() - logoHeight) / 2;
			
			/** logo 放在右下角 **/
//			int x = (image.getWidth() - widthLogo);
//			int y = (image.getHeight() - heightLogo);
			
			/** 开始绘制图片 **/
			Graphics2D g = image.createGraphics();	// 建绘图对象
			LogoConfig logoConfig = new LogoConfig();
			g.drawImage(logo, x, y, logoWidth, logoHeight, null);
			g.drawRoundRect(x, y, logoWidth, logoHeight, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, logoWidth, logoHeight);

			g.dispose();

			File qrPic = new File(folder + File.separator + UUID.randomUUID().toString() + Constants.POINT + FORMAT);
			ImageIO.write(image, FORMAT, qrPic);
			
			return qrPic;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 生成二维码 BufferedImage 图片
	 * 
	 * @param contents 二维码内容
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @return
	 */
	private static BufferedImage getQRCodeBufferedImage(String contents, int width, int height) {
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			BitMatrix bm = multiFormatWriter.encode(contents, BarcodeFormat.QR_CODE, width,
					height, getEncodeHintType());

			int w = bm.getWidth();
			int h = bm.getHeight();
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			// 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
				}
			}
			
			return image;
		} catch (WriterException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 设置二维码的格式参数
	 * 
	 * @return
	 */
	private static Map<EncodeHintType, Object> getEncodeHintType() {
		// 用于设置QR二维码参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, CHARACTER_SET);
		// 设置边框留白宽度（0-4）
		hints.put(EncodeHintType.MARGIN, 0);

		return hints;
	}	
	
	/**
	 * 解析二维码
	 *
	 * @param file
	 * @return 二维码内容
	 *
	 * @author dongbz, 2015-11-20
	 */
	public static String parse(File file) {
		try {
			MultiFormatReader formatReader = new MultiFormatReader();

			BufferedImage image = ImageIO.read(file);

			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, CHARACTER_SET);

			Result result = formatReader.decode(binaryBitmap, hints);

			return result.getText();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
}
