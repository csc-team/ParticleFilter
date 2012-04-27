
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;

public class ProxyGrabber {
	private final OpenCVFrameGrabber grabber;

	public ProxyGrabber(String path) {
		grabber = new OpenCVFrameGrabber(path);
	}

	public void start() {
		try {
			grabber.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProxyImage grab() {
		try {
			return new ProxyImage(grabber.grab());
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
