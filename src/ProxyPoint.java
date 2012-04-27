import com.googlecode.javacv.cpp.opencv_core.CvPoint;


public class ProxyPoint {
	private final CvPoint point;

	public ProxyPoint(CvPoint point) {
		super();
		this.point = point;
	}

	public ProxyPoint(int x, int y) {
		point = new CvPoint(x, y);
	}

	public int x() {
		return point.x();
	}

	public int y() {
		return point.y();
	}

	public CvPoint getPoint() {
		return point;
	}
}
