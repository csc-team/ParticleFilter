
import com.googlecode.javacv.cpp.opencv_features2d.FlannBasedMatcher;
import com.googlecode.javacv.cpp.opencv_features2d.SiftDescriptorExtractor;
import com.googlecode.javacv.cpp.opencv_features2d.SiftFeatureDetector;

public class ProxyFeatures2D {
    /**
     * Tools for SIFT matching
     */    
	private static final SiftFeatureDetector detector = new SiftFeatureDetector(0.05, 10, 4, 3, -1, 0);
    private static final SiftDescriptorExtractor extractor = new SiftDescriptorExtractor();
    private static final FlannBasedMatcher matcher = new FlannBasedMatcher();
	
    public static void compute(ProxyImage grey, ProxyKeyPoint kp, ProxyMat descr) {
		extractor.compute(grey.getImage(), kp.getKeypoint(), descr.getMat());
		
	}
	public static void detect(ProxyImage grey, ProxyKeyPoint kpts) {
		detector.detect(grey.getImage(), kpts.getKeypoint(), null);
		
	}
	public static void match(ProxyMat d1, ProxyMat d2, ProxyDMatch dm) {
		matcher.match(d1.getMat(), d2.getMat(), dm.getDMatch(), null);
	}
    
    
}
