import static com.googlecode.javacv.cpp.opencv_core.CV_RGB;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCircle;
import static com.googlecode.javacv.cpp.opencv_core.cvLine;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvRectangle;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
//import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_core.cvResetImageROI;
import static com.googlecode.javacv.cpp.opencv_core.cvSetImageROI;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_RGB2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

import java.awt.Image;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class ProxyImage {
    public IplImage getImage() {
		return image;
	}

	public void setImage(IplImage image) {
		this.image = image;
	}

	public ProxyImage(IplImage image) {
		super();
		this.image = image;
	}

	public Image getBufferedImage() {
		return image.getBufferedImage();
	}

	public CvSize cvSize() {
		return image.cvSize();
	}

	public int width() {
		return image.width();
	}

	public int height() {
		return image.height();
	}

	public static void setImageROI(ProxyImage grey, ProxyRect r) {
		cvSetImageROI(grey.getImage(), r.getRect());		
	}

	public static void cvtColorToGrey(ProxyImage image, ProxyImage grey) {
		cvCvtColor(image.getImage(), grey.getImage(), CV_RGB2GRAY);
	}

	public static void resetImageROI(ProxyImage grey) {
		cvResetImageROI(grey.getImage());		
	}

	public static void circle(ProxyImage image, int x, int y, int radius) {
		cvCircle(image.getImage(), cvPoint(x, y), radius, CV_RGB(250,45,70), 1 , 8, 0);
	}

	public static void line(ProxyImage image, int x1, int y1, int x2, int y2) {
		cvLine(image.getImage(), cvPoint(x1, y1), cvPoint(x2, y2), CV_RGB(250,45,70), 1, 8, 0);
		
	}

	public static void rectangle(ProxyImage image, int x1, int y1, int x2, int y2) {
		cvRectangle(image.getImage(), cvPoint(x1, y1), cvPoint(x2, y2), CV_RGB(250,45,70), 1, 8, 0);		
	}
	
	public static void rectangle(ProxyImage image, ProxyPoint p1, ProxyPoint p2) {
		cvRectangle(image.getImage(), p1.getPoint(), p2.getPoint(), CV_RGB(250,45,70), 1, 8, 0);		
	}

	private IplImage image;

	public static ProxyImage createGreyImage(ProxyImage image) {
		IplImage grey = cvCreateImage(image.getImage().cvSize(), IPL_DEPTH_8U, 1);
		return new ProxyImage(grey);
	}
    
    
}
