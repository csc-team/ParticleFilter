//import static com.googlecode.javacv.cpp.opencv_core.CV_RGB;
//import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
//import static com.googlecode.javacv.cpp.opencv_core.cvCircle;
//import static com.googlecode.javacv.cpp.opencv_core.cvLine;
//import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
//import static com.googlecode.javacv.cpp.opencv_core.cvRectangle;
//import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
////import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
//import static com.googlecode.javacv.cpp.opencv_core.cvResetImageROI;
//import static com.googlecode.javacv.cpp.opencv_core.cvSetImageROI;
//import static com.googlecode.javacv.cpp.opencv_imgproc.CV_RGB2GRAY;
//import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;

//import com.googlecode.javacv.FrameGrabber.Exception;
//import com.googlecode.javacv.OpenCVFrameGrabber;
//import com.googlecode.javacv.cpp.opencv_core.CvMat;
//import com.googlecode.javacv.cpp.opencv_core.CvPoint;
//import com.googlecode.javacv.cpp.opencv_core.CvRect;
//import com.googlecode.javacv.cpp.opencv_core.IplImage;
//import com.googlecode.javacv.cpp.opencv_features2d.DMatch;
//import com.googlecode.javacv.cpp.opencv_features2d.FlannBasedMatcher;
//import com.googlecode.javacv.cpp.opencv_features2d.KeyPoint;
//import com.googlecode.javacv.cpp.opencv_features2d.SiftDescriptorExtractor;
//import com.googlecode.javacv.cpp.opencv_features2d.SiftFeatureDetector;

/**
 * @author stas
 *
 */
public final class MyParticleFilter {
	/**
     * System Constants
     */
	public final static int radius = 5;
	public final static int PART_NUM = 200;
	public final static int FEATURE_NUM = 2;
	public final static double THRESHOLD = 5.0;
	
	public MyParticleFilter(String path) {
		grabber = new ProxyGrabber(path);
		states = new ArrayList<Particle>(PART_NUM);
		features = new ArrayList<Observation>(FEATURE_NUM);
	}

	
	public ProxyImage getImage() {
		return image;
	}
	
	public Image getBufferedImage() {
		return image.getBufferedImage();
	}

	
	public void start() {
		try {
			grabber.start();
            image = grabber.grab();
            grey = ProxyImage.createGreyImage(image);
    		ProxyImage.cvtColorToGrey(image, grey);
		} catch (Exception e) {
			System.out.println("Inside tracker start!");
			e.printStackTrace();
		}
	}
	public void nextFrame() {
        try {
			image = grabber.grab();
	        grey = ProxyImage.createGreyImage(image);
			ProxyImage.cvtColorToGrey(image, grey);
		} catch (Exception e) {
			System.out.println("Inside tracker next frame!");
			e.printStackTrace();
		}
	}

	public void initDistribution() {
		ProxyRect[] symb = FakeDetector.getSymbRect();
		ProxyRect area = FakeDetector.getRegion(symb);
		
		for(int i = 0; i < symb.length; i++) {
			ProxyRect roi = new ProxyRect(symb[i].x() - area.x(), symb[i].y() - area.y(), symb[i].width(), symb[i].height());
			ProxyMat descr = new ProxyMat();
			ProxyKeyPoint kp = findKeyPoints(roi);
			ProxyFeatures2D.compute(grey, kp, descr);
			features.add(new Observation(roi, descr, kp.capacity()));
		}
		
		for(int i = 0; i < PART_NUM; i++) {
			states.add(new Particle(area.x(), area.y(), 0));
		}
	}
	
	public void drawCurrentState() {
		transition();
		likelihood();
		normalize();
		resample();
		drawMostLikelyParticle();
		// TODO Auto-generated method stub		
	}
	
    
	/**
     * Variable for holding current loaded image and video
     */
    private ProxyGrabber grabber;
    private ProxyImage image;
    private ProxyImage grey;
    
    /**
     * Tracker parameters
     */
    private ArrayList<Particle> states;
    private ArrayList<Observation> features;
    private ArrayList<Observation> observations;
    
    


	
	private ProxyKeyPoint findKeyPoints(ProxyRect r) {
		ProxyImage.setImageROI(grey, r);
		ProxyKeyPoint kpts = new ProxyKeyPoint();
		ProxyFeatures2D.detect(grey, kpts);
		ProxyImage.resetImageROI(grey);
		return kpts;
	}


	
	private void drawMostLikelyParticle() {
		doMostLikelyEstimate();
		for (Observation f : features) {
			target(f);
			targetCenter(Math.round(f.getTransX(states.get(0))), Math.round(f.getTransY(states.get(0))));
		}
	}

    private void targetCenter(int x, int y) {
    	if(image != null) {
            ProxyImage.circle(image, x, y, radius/* CV_RGB(250,45,70),1 ,8, 0 */);
            ProxyImage.line(image, x-radius/2, y-radius/2, x+radius/2, y+radius/2);
            ProxyImage.line(image, x-radius/2, y+radius/2, x+radius/2, y-radius/2);
    	}
    }
    
    private void target(Observation ob) {
    	if(image != null && ob.getMle() != null) {
            ProxyImage.rectangle(image, ob.getP1(), ob.getP2());
    	}
    }
	private void doMostLikelyEstimate() {
		Particle s = states.get(0);
		double dist = 0.0;
		double min = 9000.0;
		for(Observation f : features) {
			f.setMle(null);
			min = 9000.0f;
			for(Observation o : observations) {
				dist = Math.hypot(f.getTransX(s) - o.getX(), f.getTransY(s) - o.getY());
				if(dist < THRESHOLD) {
					if (dist < min) {
						f.setMle(o.getElement());
						min = dist;
					}
				}
				
			}
		}
	}

	private void resample() {
		ArrayList<Particle> newStates = new ArrayList<Particle>(PART_NUM);
		Collections.sort(states);
		int k = 0;
		for(int i = 0; i < PART_NUM; i++) {
			int np = (int) Math.round(states.get(i).getWeight() * PART_NUM);
			for(int j = 0; j < np; j++) {
				newStates.add(states.get(i));
				k++;
			}
			if(k == PART_NUM) {
				break;
			}
		}
		while(k < PART_NUM) {
			newStates.add(states.get(0));
			k++;
		}
		states = newStates;
	}
	
	
	private void normalize() {
		float sum = 0.0f;
		for (Particle s : states) {
			sum += s.getWeight();
		}
		for (Particle s : states) {
			s.normalizeWeight(sum);
		}
	}
	
	
	private void likelihood() {
		ProxyRect[] symbols = FakeDetector.getSymbRectFast();
		observations = new ArrayList<Observation>(symbols.length);
		for(ProxyRect r : symbols) {
			ProxyKeyPoint kp = findKeyPoints(r);
			ProxyMat descr = new ProxyMat();
			ProxyFeatures2D.compute(grey, kp, descr);
			observations.add(new Observation(r, descr, kp.capacity()));
		}
		int count = 0;
		double dist = 0.0f;
		for(Particle s : states) {
			count = 0;
			for(Observation f : features) {
				for(Observation o : observations) {
					dist = Math.hypot(f.getTransX(s) - o.getX(), f.getTransY(s) - o.getY());
					if(dist < THRESHOLD && match(f, o)) {
						count++;
					}
				}
			}
			s.setWeight((float)Math.exp(count));
		}
	}
	

	private boolean match(Observation f, Observation o) {
		ProxyDMatch dm = new ProxyDMatch();
		ProxyFeatures2D.match(f.getDescriptors(), o.getDescriptors(), dm);
	    int n = dm.capacity();
	    ProxyDMatch[] matches = new ProxyDMatch[n];
	    double min_dist = 1000;
	    double max_dist = 0;
	    for (int i = 0; i < n; i++) {
	        matches[i] = new ProxyDMatch(dm.position(i));
	        double dist = matches[i].distance();
	        if( dist < min_dist ) {
	        	min_dist = dist;
	        }
	        if( dist > max_dist ) {
	        	max_dist = dist;
	        }
	    }
	    return min_dist < 100.0;
	}

	private void transition() {
		for(Particle p : states) {
			p.move(image.width(), image.height());
		}
	}


}
