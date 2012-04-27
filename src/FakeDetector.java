public class FakeDetector {
    /**
     * Observe variables
     */
    public static ProxyRect myROI;
    public static ProxyPoint p1;
    public static ProxyPoint p2;
	
	public static ProxyRect getRegion(ProxyRect[] symb) {
		return myROI;
	}

	public static ProxyRect[] getSymbRect() {
		ProxyRect[] rects = new ProxyRect[MyParticleFilter.FEATURE_NUM];
		int diff =(int)Math.round(((double) myROI.width()) / MyParticleFilter.FEATURE_NUM);
		for(int i = 0; i < MyParticleFilter.FEATURE_NUM; i++) {
			ProxyRect roi = new ProxyRect(myROI.x() + diff * i, myROI.y(), diff, myROI.height());
			rects[i] = roi;
		}
		return rects;
	}
	
	public static ProxyRect[] getSymbRectFast() {
		return getSymbRect();
	}
	
	public static ProxyRect buildRect() {
    	int w = Math.abs(p1.x() - p2.x());
    	int h = Math.abs(p1.y() - p2.y());
    	int x = Math.min(p1.x(), p2.x());
    	int y = Math.min(p1.y(), p2.y());
		return new ProxyRect(x, y, w, h);
	}
}
