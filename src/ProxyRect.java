import com.googlecode.javacv.cpp.opencv_core.CvRect;

public class ProxyRect {
	public CvRect getRect() {
		return rect;
	}

	public ProxyRect(CvRect rect) {
		super();
		this.rect = rect;
	}

	public ProxyRect(int x, int y, int width, int height) {
		rect = new CvRect(x, y, width, height);
	}

	public int width() {
		return rect.width();
	}

	public int height() {
		return rect.height();
	}

	public int x() {
		return rect.x();
	}

	public int y() {
		return rect.y();
	}


	private final CvRect rect;
}
