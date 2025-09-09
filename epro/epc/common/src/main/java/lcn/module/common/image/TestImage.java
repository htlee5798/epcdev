package lcn.module.common.image;

public class TestImage {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		//System.out.println("start");
		
		ImageUtils iu = new ImageUtils();
		//param정보(원본파일명,리사이징파일명,가로사이즈,높이사이즈)
		iu.setImgScale("C:\\Temp\\image\\org\\01_800_533.jpg", "C:\\Temp\\image\\new\\01_300_300.jpg", 300, 300);
		
		//System.out.println("close");
	}

}
