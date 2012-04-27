import com.googlecode.javacv.cpp.opencv_core.CvMat;

public class ProxyMat {
	public CvMat getMat() {
		return mat;
	}
	
	public ProxyMat() {
		mat = new CvMat(null);
	}
	
	private final CvMat mat;
}
