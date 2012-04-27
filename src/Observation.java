
//import com.googlecode.javacv.cpp.opencv_core.CvMat;
//import com.googlecode.javacv.cpp.opencv_core.CvRect;
//import com.googlecode.javacv.cpp.opencv_core.CvPoint;


/**
 * @author stas
 *
 */
public final class Observation {	

	private final float xc;
	private final float yc;
	private final ProxyRect element;
	private final int kpts;
	private final ProxyMat descriptors;
	private ProxyRect mle;
	
	
	public ProxyRect getElement() {
		return element;
	}
	
	public ProxyRect getMle() {
		return mle;
	}
	
	public ProxyPoint getP1() {
		if (mle == null) {
			return null;
		}
		return new ProxyPoint(mle.x(), mle.y());
	}
	
	public ProxyPoint getP2() {
		if (mle == null) {
			return null;
		}
		return new ProxyPoint(mle.x() + mle.width(), mle.y() + mle.height());
	}
	
	public void setMle(ProxyRect mle) {
		this.mle = mle;
	}
	
	public float getX() {
		return xc;
	}
	
	public float getY() {
		return yc;
	}
	
	public float getTransX(Particle s) {
		float a = s.getAngle();
		return (float)(Math.cos(a) * xc - Math.sin(a) * yc + s.getX());
	}
	
	
	public float getTransY(Particle s) {
		float a = s.getAngle();
		return (float)(Math.cos(a) * yc + Math.sin(a) * xc + s.getY());
	}


	public ProxyMat getDescriptors() {
		return descriptors;
	}

	
	public Observation(ProxyRect r, ProxyMat descriptors, int kpts) {
		super();
		this.element = r;
		xc = (float)r.x() + r.width() / 2.0f;
		yc = (float)r.y() + r.height() / 2.0f;
		this.kpts = kpts;
		this.descriptors = descriptors;
	}

	public int getKpts() {
		return kpts;
	}
	
	
}
