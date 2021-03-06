package homework1;

import java.util.Iterator;
import java.util.Collections;
import java.util.ArrayList;

/**
 * A GeoFeature represents a route from one location to another along a
 * single geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have
 * the same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the
 * course of a winding river, or travel along a road through intersections
 * but remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding
 * a segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the
 * distance traveled by following the path from start to end - is not
 * necessarily the same as the distance along a straight line between
 * its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {
	private final  String name_;
	private final  ArrayList<GeoSegment> geoSegments_;
	

	// Implementation hint:
	// When asked to return an Iterator, consider using the iterator() method
	// in the List interface. Two nice classes that implement the List
	// interface are ArrayList and LinkedList. If comparing two Lists for
	// equality is needed, consider using the equals() method of List. More
	// info can be found at:
	//   http://docs.oracle.com/javase/8/docs/api/java/util/List.html
	
	
  	// TODO Write abstraction function and representation invariant
	
	// Abstraction Function :
	// represents a nonlinear or non atomic geographic road which starts
	// and ends with gs endpoints and named the same name as gs.
	// this road consists of geographic semgents connected with each other 
	// connecting the two endpoints the start and the end.
	
	// Representation Invariant :
	// geoSegments != null and name != null and start != null and end != null
	// and {geoSegment != null | geoSement contained in geoSegments list} 
	

	
	/**
     * Constructs a new GeoFeature.
     * @requires gs != null 
     * @effects Constructs a new GeoFeature, r, such that
     *	        r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public GeoFeature(GeoSegment gs) {
  		
  		assert gs != null;
  		this.name_ = gs.getName();
  		this.geoSegments_ = new ArrayList<GeoSegment>();
  		this.geoSegments_.add(new GeoSegment(gs));
  	}
  	/** Constructs a copy constructor.
  	 * @requires gf != null
  	 * @param gf
  	 * @effects Constructs a new copy of the given geographic feature,
  	 * 			such that :
  	 * 			copy.name = gf.name &&
  	 * 			copy contains all the goeSegment in gf and in the same order.
  	 */
  	public GeoFeature(GeoFeature gf) {
  		assert gf != null;
  		gf.checkRep();
		this.name_ = gf.getName();
		this.geoSegments_ = new ArrayList<GeoSegment>();
		for(GeoSegment gs : gf.geoSegments_) 
			this.geoSegments_.add(new GeoSegment(gs));
  	}
  	
	/**
  	 * check to see if the representation invariant is being violated
  	 * @throw AssertionError if representation invariant is violated
  	 */
  	private void checkRep() {
  		assert (this.name_ != null) && (this.geoSegments_ != null);
  		
  		for(GeoSegment gs : this.geoSegments_) {
  			assert gs != null;
  			
  			// check that names of the Segment same as the geo feature name
  			assert gs.getName().equals(this.name_);
  		}
  	}
  	
 	/**
 	  * Returns name of geographic feature.
      * @return name of geographic feature
      */
  	public String getName() {
  		checkRep();
  		return this.name_;
  	}


  	/**
  	 * Returns location of the start of the geographic feature.
     * @return location of the start of the geographic feature.
     */
  	public GeoPoint getStart() {
  		checkRep();
  		return this.geoSegments_.get(0).getP1();
  	}


  	/**
  	 * Returns location of the end of the geographic feature.
     * @return location of the end of the geographic feature.
     */
  	public GeoPoint getEnd() {
  		checkRep();
  		return this.geoSegments_.get(this.geoSegments_.size() - 1).getP2();
  	}

  	/**
  	 * Returns direction of travel at the start of the geographic feature.
  	 * @requires start != the other endPoint on the same geoSegment 
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in degrees.
     */
  	public double getStartHeading() {
  		checkRep();
  		return this.geoSegments_.get(0).getHeading();
  	}


  	/**
  	 * Returns direction of travel at the end of the geographic feature.
  	 * @requires end != the other endPoint on the same geoSegment
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in degrees.
     */
  	public double getEndHeading() {
  		checkRep();
  		return this.geoSegments_.get(this.geoSegments_.size() - 1).getHeading();
  	}


  	/**
  	 * Returns total length of the geographic feature, in kilometers.
     * @return total length of the geographic feature, in kilometers.
     *         NOTE: this is NOT as-the-crow-flies, but rather the total
     *         distance required to traverse the geographic feature. These
     *         values are not necessarily equal.
     */
  	public double getLength() {
  		checkRep();
  		double sumOfLengths = 0;
  		for(GeoSegment gs : this.geoSegments_)
  			sumOfLengths += gs.getLength();
  		checkRep();
  		return sumOfLengths;
  	}

  	/**
   	 * Creates a new GeoFeature that is equal to this GeoFeature with gs
   	 * appended to its end.
     * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
     * @return a new GeoFeature r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *    	   r.length = this.length + gs.length
     **/
  	public GeoFeature addSegment(GeoSegment gs) {
  		checkRep();
  		assert gs != null;
  		int lastIndex = this.geoSegments_.size() - 1;
  		assert gs.getP1().equals(this.geoSegments_.get(lastIndex).getP2()); 
  		assert gs.getName().equals(this.name_);
  		
  		GeoSegment firstGeoSegment = this.geoSegments_.get(0);
  		
  		// construct a new GeoFeature (which adds the first geoSegment)
  		GeoFeature newGeoFeature = new GeoFeature(firstGeoSegment);
  		
  		// add the rest of the geoSegments to the new geographic feature
  		for(int i = 1; i <= lastIndex; i++)
  			newGeoFeature.geoSegments_.add(this.geoSegments_.get(i));
  		
  		// add the last geographic segment to the new geographic feature
  		newGeoFeature.geoSegments_.add(new GeoSegment(gs));
  		
  		checkRep();
  		return newGeoFeature;
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this GeoFeature. All the
     * GeoSegments have the same name.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2d  == a[i+1].p1))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
  	public Iterator<GeoSegment> getGeoSegments() {
  		checkRep();
  		int size = this.geoSegments_.size(); 
  		
  		// the iterator iterates over an unmodifiable list in order 
  		// to be safe from representation exposure(like removing element)
  		Iterator<GeoSegment> it = Collections.unmodifiableList(
  				this.geoSegments_).iterator();	
  		
  		for(int i = 0; i < size - 1 ; i++) {
  			GeoSegment gs = this.geoSegments_.get(i);
  			GeoSegment gsNext = this.geoSegments_.get(i + 1);
  			assert gs.getName().equals(gsNext.getName());
  			assert gs.getP2().equals(gsNext.getP1());	
  		}
  		checkRep();
  		return it;
  	}


  	/**
     * Compares the argument with this GeoFeature for equality.
     * @return o != null && (o instanceof GeoFeature) &&
     *         (o.geoSegments and this.geoSegments contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		checkRep();
  		GeoFeature gf = (o instanceof GeoFeature) ? (GeoFeature)o : null;
  		if(gf == null || gf.geoSegments_.size() != this.geoSegments_.size())
  			return false;
  		for(int i = 0; i < this.geoSegments_.size() - 1 ; i++) {
  			GeoSegment gsi = this.geoSegments_.get(i);
  			GeoSegment gfsi = gf.geoSegments_.get(i);
  			if(!gsi.equals(gfsi)) return false;		
  		}
  		// otherwise the objects are identical according to the conditions above
  		checkRep();
  		return true;
  	}


  	/**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {
  		checkRep();
    	return GeoPoint.factor * this.getStart().getLatitude() + 
    			this.getEnd().getLatitude();
  	}


  	/**
  	 * Returns a string representation of this.
   	 * @return a string representation of this.
     **/
  	public String toString() {
  		checkRep();
  		return "GeoFeature named : " + this.name_ + ", start is : " + 
  				this.getStart().toString() + ", end is : " + this.getEnd().toString()
  				+ ", and contains " + String.valueOf(this.geoSegments_.size()) +
  				" geographic segments, the length of this GeoFeature is " +
  				String.valueOf(this.getLength()) + ".";
  	}
}
