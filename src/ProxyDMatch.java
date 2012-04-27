
import com.googlecode.javacv.cpp.opencv_features2d.DMatch;

public class ProxyDMatch {
	private final DMatch match;

	public ProxyDMatch(ProxyDMatch match) {
		this.match = match.getDMatch();
	}

	public ProxyDMatch() {
		match = new DMatch();
	}
	
	private ProxyDMatch(DMatch match) {
		this.match = match;
	}

	public DMatch getDMatch() {
		return match;
	}

	public int capacity() {
		return match.capacity();
	}

	public ProxyDMatch position(int i) {
		return new ProxyDMatch(match.position(i));
	}

	public float distance() {
		return match.distance();
	}
}
