package homework1;

import java.lang.Math;

/**
 * A GeoPoint is a point on the earth. GeoPoints are immutable.
 * <p>
 * North latitudes and east longitudes are represented by positive numbers.
 * South latitudes and west longitudes are represented by negative numbers.
 * <p>
 * The code may assume that the represented points are nearby the Technion.
 * <p>
 * <b>Implementation direction</b>:<br>
 * The Ziv square is at approximately 32 deg. 46 min. 59 sec. N
 * latitude and 35 deg. 0 min. 52 sec. E longitude. There are 60 minutes
 * per degree, and 60 seconds per minute. So, in decimal, these correspond
 * to 32.783098 North latitude and 35.014528 East longitude. The 
 * constructor takes integers in millionths of degrees. To create a new
 * GeoPoint located in the the Ziv square, use:
 * <tt>GeoPoint zivCrossroad = new GeoPoint(32783098,35014528);</tt>
 * <p>
 * Near the Technion, there are approximately 110.901 kilometers per degree
 * of latitude and 93.681 kilometers per degree of longitude. An
 * implementation may use these values when determining distances and
 * headings.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   latitude :  real        // latitude measured in degrees
 *   longitude : real        // longitude measured in degrees
 * </pre>
 **/
public class GeoPoint {
	
	private final int latitude_;
	private final int longitude_;

	/** Minimum value the latitude field can have in this class. **/
	public static final int MIN_LATITUDE  =  -90 * 1000000;
	    
	/** Maximum value the latitude field can have in this class. **/
	public static final int MAX_LATITUDE  =   90 * 1000000;
	    
	/** Minimum value the longitude field can have in this class. **/
	public static final int MIN_LONGITUDE = -180 * 1000000;
	    
	/** Maximum value the longitude field can have in this class. **/
	public static final int MAX_LONGITUDE =  180 * 1000000;

  	/**
   	 * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
  	public static final double KM_PER_DEGREE_LATITUDE = 110.901;

  	/**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
  	public static final double KM_PER_DEGREE_LONGITUDE = 93.681;
  	
  	public static final int factor = 11;
  	
	// Implementation hint:
	// Doubles and floating point math can cause some problems. The exact
	// value of a double can not be guaranteed except within some epsilon.
	// Because of this, using doubles for the equals() and hashCode()
	// methods can have erroneous results. Do not use floats or doubles for
	// any computations in hashCode(), equals(), or where any other time 
	// exact values are required. (Exact values are not required for length 
	// and distance computations). Because of this, you should consider 
	// using ints for your internal representation of GeoPoint. 

  	
  	// TODO Write abstraction function and representation invariant
  	
  	
  	// Abstraction Function :
  	// represent the latitude and longitude of a legal geographic point on 
  	// earth.
  	
  	// Representation Invariant :
  	// (MIN_LATITUDE <= latitude <= MAX_LATITUDE) and 
    // (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE) and GeoPoint != null
  	// and latitude and longitude are represented in millionths of a degree
  
  	
  	/**
  	 * Constructs GeoPoint from a latitude and longitude.
     * @requires the point given by (latitude, longitude) in millionths
   	 *           of a degree is valid such that:
   	 *           (MIN_LATITUDE <= latitude <= MAX_LATITUDE) and
     * 	 		 (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE)
   	 * @effects constructs a GeoPoint from a latitude and longitude
     *          given in millionths of degrees.
   	 **/
  	public GeoPoint(int latitude, int longitude) {
  		// TODO Implement this constructor
  		this.latitude_ = latitude;
  		this.longitude_ = longitude; 
  		checkRep();
  	}
  	
  	/**
  	 * Constructs a copy of a given GeoPoint.
  	 * @requires  the geoPoint latitude and longitude are in millionths
   	 *           of a degree and satisfies :
   	 *           (MIN_LATITUDE <= latitude <= MAX_LATITUDE) and
     * 	 		 (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE)
     * @effects constructs a copy of the given GeoPoint such that :
     * 			copy.latitude = geoPoint.latitude ,
     * 			copy.longitude = copy.longitude.
     **/ 
  	public GeoPoint(GeoPoint point) {
  		this.latitude_ = point.latitude_;
  		this.longitude_ = point.longitude_;
  		checkRep();
  	}
  	
  	 
  	/**
  	 * check to see if the representation invariant is being violated
  	 * @throw AssertionError if representation invariant is violated
  	 */
  	private void checkRep() {
  		assert ((MIN_LATITUDE <= this.latitude_) && 
  		(this.latitude_ <= MAX_LATITUDE) && (MIN_LONGITUDE <= this.longitude_)
  		  && (this.longitude_ <= MAX_LONGITUDE));
  	}
  	
  	/**
     * Returns the latitude of this.
     * @return the latitude of this in millionths of degrees.
     */
  	public int getLatitude() {
  		checkRep();
  		return this.latitude_;
  		// TODO Implement this method
  	}


  	/**
     * Returns the longitude of this.
     * @return the latitude of this in millionths of degrees.
     */
  	public int getLongitude() {
  		checkRep();
  		return this.longitude_;
  		// TODO Implement this method
  	}


  	/**
     * Computes the distance between GeoPoints.
     * @requires gp != null
     * @return the distance from this to gp, using the flat-surface, near
     * the Technion approximation.
     **/
  	public double distanceTo(GeoPoint gp) {
  		checkRep();
  		assert gp != null; 
  		double latDiff = (double)(gp.latitude_ - this.latitude_);
  		double lngDiff = (double)(gp.longitude_- this.longitude_);
  		double latDiffInDegree = Math.abs(latDiff) / 1000000;
  		double lngDiffInDegree = Math.abs(lngDiff) / 1000000;
  		checkRep();
  		return Math.sqrt(Math.pow(lngDiffInDegree * this.KM_PER_DEGREE_LONGITUDE
  			, 2) + Math.pow(latDiffInDegree * this.KM_PER_DEGREE_LATITUDE, 2));
  	}


  	/**
     * Computes the compass heading between GeoPoints.
     * @requires gp != null && !this.equals(gp)
     * @return the compass heading h from this to gp, in degrees, using the
     *         flat-surface, near the Technion approximation, such that
     *         0 <= h < 360. In compass headings, north = 0, east = 90,
     *         south = 180, and west = 270.
     **/
  	public double headingTo(GeoPoint gp) {
		 //	Implementation hints:
		 // 1. You may find the mehtod Math.atan2() useful when
		 // implementing this method. More info can be found at:
		 // http://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
		 //
		 // 2. Keep in mind that in our coordinate system, north is 0
		 // degrees and degrees increase in the clockwise direction. By
		 // mathematical convention, "east" is 0 degrees, and degrees
		 // increase in the counterclockwise direction. 
		 
  		// TODO Implement this method
  		checkRep();
  		assert (gp != null);
  		double latDiff = (double)(gp.latitude_ - this.latitude_);
  		double lngDiff = (double)(gp.longitude_- this.longitude_);
  		double latDiffInDegree = latDiff / 1000000;
  		double lngDiffInDegree = lngDiff / 1000000;
  		double heading = Math.atan2(latDiffInDegree, lngDiffInDegree);	
  		
  		 // convert to degrees 0 to 180/-180
  		heading *= (180 / 3.14159);

  		// convert to 0-360 compass degrees, note that North = 0
  		heading = (450 - heading) % 360;
  		checkRep();
  		return heading;
  	}


  	/**
     * Compares the specified Object with this GeoPoint for equality.
     * @return gp != null && (gp instanceof GeoPoint) &&
     * 		   gp.latitude = this.latitude && gp.longitude = this.longitude
     **/
  	public boolean equals(Object gp) {
  		checkRep();
  		GeoPoint gpPointer = (gp instanceof GeoPoint) ? (GeoPoint)gp : null;
  		return (gp != null) && (gp instanceof GeoPoint) && 
  				(this.longitude_ == gpPointer.longitude_) && 
  				(this.latitude_ == gpPointer.latitude_);
  	}

  	/**
     * Returns a hash code value for this GeoPoint.
     * @return a hash code value for this GeoPoint.
   	 **/
  	public int hashCode() {
  		checkRep();
    	return factor * this.latitude_ + this.longitude_;
  	}


  	/**
     * Returns a string representation of this GeoPoint.
     * @return a string representation of this GeoPoint.
     **/
  	public String toString() {
  		checkRep();
  		return "GeoPoint with (latitude = " + latitude_ + 
  				", longitude = " + this.longitude_ + ")";
  	}

}
