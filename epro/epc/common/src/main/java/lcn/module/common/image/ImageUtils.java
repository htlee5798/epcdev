/**
 * @prjectName  : 롯데정보통신 프로젝트   
 * @since       : 2011. 4. 20. 오후 2:47:21
 * @author      : jmryu 
 * @Copyright(c) 2000 ~ 2010 롯데정보통신(주)
 *  All rights reserved.
 */
package lcn.module.common.image;

/**
 * @Class Name : 
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 4. 20. 오후 2:47:21 jmryu
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */

	
//  @4UP 삭제 비표준 클래스
//	import com.sun.image.codec.jpeg.JPEGCodec;
//	import com.sun.image.codec.jpeg.JPEGImageEncoder;
	import com.sun.jimi.core.Jimi;
	import com.sun.jimi.core.JimiException;
	import com.sun.jimi.core.raster.JimiRasterImage;
	import java.awt.Color;
	import java.awt.Graphics2D;
	import java.awt.Image;
	import java.awt.image.BufferedImage;
	import java.io.File;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.OutputStream;
	import javax.imageio.ImageIO;

	public class ImageUtils
	{
	  public static boolean isImage(String srcFile)
	  {
	    try
	    {
	      File inImgfle = new File(srcFile);
	      Image inImage = ImageIO.read(inImgfle);

	      inImage.getSource();
	    } catch (Exception e) {
	      return false;
	    }

	    return true;
	  }

	  public static boolean setImgScale(String sourceFile, String outFile, int targetWidth, int targetHeight)
	    throws JimiException, IOException
	  {
	    File fsrc = new File(sourceFile);

	    if (!fsrc.isFile())
	    {
	      return false;
	    }

	    File outputfile = new File(outFile);
	    if (outputfile.exists()) {
	      outputfile.delete();
	    }

	    if (!directoryCheck(outFile))
	      createDirectory(outFile);
	    File inImgfle = new File(sourceFile);
	    Image inImage = ImageIO.read(inImgfle);

	    int modiWidth = 0;
	    int modiHeight = 0;

	    int srcHeight = inImage.getHeight(null);
	    int srcWidth = inImage.getWidth(null);

	    double srcScale = srcWidth / srcHeight;
	    double targetScale = targetWidth / targetHeight;

	    if ((srcWidth < targetWidth) && (srcHeight < targetHeight)) {
	      modiWidth = srcWidth;
	      modiHeight = srcHeight;
	    }
	    else if (srcScale < targetScale) {
	      modiWidth = srcWidth * targetHeight / srcHeight;
	      modiHeight = targetHeight;
	    } else if (srcScale == targetScale) {
	      modiWidth = srcWidth * targetWidth / srcWidth;
	      modiHeight = srcHeight * targetWidth / srcWidth;
	    }
	    else {
	      modiWidth = targetWidth;
	      modiHeight = srcHeight * targetWidth / srcWidth;
	    }

	    inImage = inImage.getScaledInstance(modiWidth, modiHeight, 16);

	    File outputFile = new File(outFile);
	    if (outputFile.isFile())
	      outputFile.delete();
	    JimiRasterImage raster = Jimi.createRasterImage(inImage.getSource());
	    FileOutputStream fos = new FileOutputStream(outputFile);

	    Jimi.putImage("image/jpeg", raster, fos);

	    fos.flush();
	    fos.close();

	    File outImgfle = new File(outFile);
	    inImage = ImageIO.read(outImgfle);
	    BufferedImage outImage = new BufferedImage(modiWidth, modiHeight, 1);
	    Graphics2D g2d = outImage.createGraphics();
	    g2d.setColor(new Color(255, 255, 255));

	    g2d.fillRect(0, 0, modiWidth, modiHeight);
	    g2d.drawImage(inImage, 0, 0, null);
	    g2d.dispose();

	    
	    // @4UP JPEG 이미지 파일 작성 비표준 클래스에서 표준 클래스로 수정
//	    OutputStream os = new FileOutputStream(outFile);
//	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
//	    encoder.encode(outImage);
//	    os.close();
	    ImageIO.write(outImage, "jpeg", outImgfle);

	    return true;
	  }

	  public static boolean setImgScale(File inImgfle, String outFile, int targetWidth, int targetHeight)
	    throws JimiException, IOException
	  {
	    if (!inImgfle.isFile())
	    {
	      return false;
	    }

	    File outputfile = new File(outFile);
	    if (outputfile.exists()) {
	      outputfile.delete();
	    }

	    if (!directoryCheck(outFile)) {
	      createDirectory(outputfile.getPath());
	    }

	    Image inImage = ImageIO.read(inImgfle);

	    int modiWidth = 0;
	    int modiHeight = 0;

	    int srcHeight = inImage.getHeight(null);
	    int srcWidth = inImage.getWidth(null);

	    double srcScale = srcWidth / srcHeight;
	    double targetScale = targetWidth / targetHeight;

	    if ((srcWidth < targetWidth) && (srcHeight < targetHeight)) {
	      modiWidth = srcWidth;
	      modiHeight = srcHeight;
	    }
	    else if (srcScale < targetScale) {
	      modiWidth = srcWidth * targetHeight / srcHeight;
	      modiHeight = targetHeight;
	    }
	    else if (srcScale == targetScale) {
	      modiWidth = srcWidth * targetWidth / srcWidth;
	      modiHeight = srcHeight * targetWidth / srcWidth;
	    }
	    else
	    {
	      modiWidth = targetWidth;
	      modiHeight = srcHeight * targetWidth / srcWidth;
	    }

	    inImage = inImage.getScaledInstance(modiWidth, modiHeight, 16);

	    File outputFile = new File(outFile);
	    if (outputFile.isFile())
	      outputFile.delete();
	    JimiRasterImage raster = Jimi.createRasterImage(inImage.getSource());
	    FileOutputStream fos = new FileOutputStream(outputFile);

	    Jimi.putImage("image/jpeg", raster, fos);

	    fos.flush();
	    fos.close();

	    File outImgfle = new File(outFile);
	    inImage = ImageIO.read(outImgfle);
	    BufferedImage outImage = new BufferedImage(modiWidth, modiHeight, 1);
	    Graphics2D g2d = outImage.createGraphics();
	    g2d.setColor(new Color(255, 255, 255));

	    g2d.fillRect(0, 0, modiWidth, modiHeight);
	    g2d.drawImage(inImage, 0, 0, null);
	    g2d.dispose();

	    // @4UP JPEG 이미지 파일 작성 비표준 클래스에서 표준 클래스로 수정
//	    OutputStream os = new FileOutputStream(outFile);
//	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
//	    encoder.encode(outImage);
//	    os.close();
	    ImageIO.write(outImage, "jpeg", outputFile);

	    return true;
	  }

	  private static boolean directoryCheck(String fileName)
	  {
	    String directoryName = "";
	    File file = null;
	    if (fileName.lastIndexOf(".") > 0) {
	      directoryName = fileName.substring(0, fileName.lastIndexOf(File.separator));
	    }
	    else {
	      directoryName = fileName;
	    }
	    file = new File(directoryName);

	    return file.isDirectory();
	  }

	  private static boolean createDirectory(String fileName)
	  {
	    String directoryName = "";
	    File file = null;
	    if (fileName.lastIndexOf(".") > 0) {
	      directoryName = fileName.substring(0, fileName.lastIndexOf(File.separator));
	    }
	    else {
	      directoryName = fileName;
	    }

	    file = new File(directoryName);

	    return file.mkdirs();
	  }
	}

