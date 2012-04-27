import com.googlecode.javacv.cpp.opencv_features2d.KeyPoint;

public class ProxyKeyPoint {
	public KeyPoint getKeypoint() {
		return keypoint;
	}

	public ProxyKeyPoint(KeyPoint keypoint) {
		super();
		this.keypoint = keypoint;
	}

	public ProxyKeyPoint() {
		keypoint = new KeyPoint();
	}

	public int capacity() {
		return keypoint.capacity();
	}
	
	private final KeyPoint keypoint;
	
}
