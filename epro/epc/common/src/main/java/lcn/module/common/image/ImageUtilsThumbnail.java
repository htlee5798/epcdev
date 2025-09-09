/**
 * @prjectName  : 롯데정보통신 프로젝트   
 * @since       : 2011. 7. 14. 오전 9:40:43
 * @author      : jmryu 
 * @Copyright(c) 2000 ~ 2011 롯데정보통신(주)
 *  All rights reserved.
 */
package lcn.module.common.image;

/**
 * @Class Name : 
 * @Description : <500x500 원본 이미지>
 *	400x400
 *	300x300
 *	200x200
 *	100x100
 * 	PNG 포멧의 경우에는 자동적으로 내부적으로 gif로 변경된다.
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 7. 14. 오전 9:40:43 jmryu
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

public class ImageUtilsThumbnail {

	public static void resize(String source, String destination, float scale)
			throws IOException {

//		File newFile = new File(destination);
//		RenderedOp renderedOp = JAI.create("fileload", source);
//
//		BufferedImage bufferedImage = renderedOp.getAsBufferedImage();
//		int width = (int) (bufferedImage.getWidth() * scale);
//		int height = (int) (bufferedImage.getHeight() * scale);
//
//		BufferedImage bufferIm = new BufferedImage(width, height,
//				BufferedImage.TYPE_INT_RGB);
//		Graphics2D graphics2D = bufferIm.createGraphics();
//		graphics2D.drawImage(bufferedImage, 0, 0, width, height, null);
//		ImageIO.write(bufferIm, "jpg", newFile);
		
		//----------------------------------------
		// 2012.07.20 임재유 - 리사이징 계산현상 제거
		File newFile = new File(destination);
		RenderedOp renderedOp = JAI.create("fileload", source);
		BufferedImage srcImg = renderedOp.getAsBufferedImage();
		
		int width = (int) (srcImg.getWidth() * scale);
		int height = (int) (srcImg.getHeight() * scale);
		
		Image imgTarget = srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		int pixels[] = new int[width * height];
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, width, height, pixels, 0, width);
		
		try{
			pg.grabPixels();
		}catch(InterruptedException e){
			throw new IOException(e.getMessage());
		}
		
		BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		destImg.setRGB(0, 0, width, height, pixels, 0, width);
		
		ImageIO.write(destImg, "jpg", newFile);
		
	}

	public static void resize(String source, String destination, int width,
			int height) throws IOException {
		
		
//		File newFile = new File(destination);
//		RenderedOp renderedOp = JAI.create("fileload", source);
//
//		BufferedImage bufferedImage = renderedOp.getAsBufferedImage();
//		BufferedImage bufferIm = new BufferedImage(width, height,
//				BufferedImage.TYPE_INT_RGB);
//		Graphics2D graphics2D = bufferIm.createGraphics();
//		graphics2D.drawImage(bufferedImage, 0, 0, width, height, null);
//		ImageIO.write(bufferIm, "jpg", newFile);
		
		//----------------------------------------
		// 2012.07.20 임재유 - 리사이징 계산현상 제거
		File newFile = new File(destination);
		RenderedOp renderedOp = JAI.create("fileload", source);
		BufferedImage srcImg = renderedOp.getAsBufferedImage();
		
		Image imgTarget = srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		int pixels[] = new int[width * height];
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, width, height, pixels, 0, width);
		
		try{
			pg.grabPixels();
		}catch(InterruptedException e){
			throw new IOException(e.getMessage());
		}
		
		BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		destImg.setRGB(0, 0, width, height, pixels, 0, width);
		
		ImageIO.write(destImg, "jpg", newFile);
		
	}

	public static void resizeauto(String source) throws IOException {
		resize(source, source+"_iPhone_Portrait.jpg", 320,320);
		resize(source, source+"_iPhone_Portrait_Retina.jpg", 640,640);
		resize(source, source+"_iPhone_Landscape.jpg", 480,480);
		resize(source, source+"_iPhone_Landscape_Retina.jpg", 960,960);
		resize(source, source+"_iPhone_icon.jpg", 57,57);
		resize(source, source+"_iPhone_icon_Retina.jpg", 114,114);
		resize(source, source+"_iPad_Portrait.jpg", 768,768);
		resize(source, source+"_iPad_Landscape.jpg", 1024,1024);
		resize(source, source+"_iPad_icon.jpg", 72,72);
		resize(source, source+"_GalaxyS_Portrait.jpg", 480,480);
		resize(source, source+"_GalaxyS_Landscape.jpg", 800,800);
		resize(source, source+"_GalaxyTab_Portrait.jpg", 600,600);
		resize(source, source+"_GalaxyTab_Landscape.jpg", 1024,1024);
		resize(source, source+"_Android_icon_hdpi.jpg", 72,72);
		resize(source, source+"_Android_icon_mdpi.jpg", 48,48);
		resize(source, source+"_Android_icon_ldpi.jpg", 36,36);
		resize(source, source+"size100x100.jpg", 100,100);
	}

	public static void resizeAutoForEPC(String source) throws IOException {
		
		resize(source, source+"_500.jpg", 400,400);
		resize(source, source+"_250.jpg", 220,220);
		resize(source, source+"_208.jpg", 208,208);
		resize(source, source+"_120.jpg", 120,120);
		resize(source, source+"_90.jpg", 90,90);
		resize(source, source+"_80.jpg", 80,80);
		
//		resize(source, source+"_160.jpg", 154,154);
//		resize(source, source+"_100.jpg", 100,100);
//		resize(source, source+"_75.jpg", 75,75);
//		resize(source, source+"_60.jpg", 60,60);

	}
}
