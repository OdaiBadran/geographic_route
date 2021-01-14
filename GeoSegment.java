package homework1;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments 
 * are immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street,
 * boundary, or other feature.
 * As an example usage, this map
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Hanita
 *               |
 *               z
 * </pre>
 * could be represented by the following GeoSegments:
 * ("Trumpeldor Avenue", a, i), ("Trumpeldor Avenue", z, i),
 * ("Hanita", i, j), and ("Hanita", j, k).
 * </p>
 * 
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical
 * GeoPoint endpoints. Equality between GeoSegment objects requires
 * that the names be equal String objects and the end points be equal
 * GeoPoint objects.
 * </p>
 *
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   name : String       // name of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment  {
	
	private final String name_;
	private final GeoPoint p1_;
	private final GeoPoint p2_;

	
  	// TODO Write abstraction function and representation invariant
	
	// Abstraction Function :
	// represent geographic segment on earth connects geographic point1 to 
	// another geographic point2 
	
	// Representation Invariant :
	// p1 != null and p2 != null and name != null 
	
	
  	/**
     * Constructs a new GeoSegment with the specified name and endpoints.
     * @requires name != null && p1 != null && p2 != null
     * @effects constructs a new GeoSegment with the specified name and endpoints.
     **/
  	public GeoSegment(String name, GeoPoint p1, GeoPoint p2) {
  		
  		this.name_ = name;
  		this.p1_ = p1;
  		this.p2_ = p2;
  		checkRep();
  	}
  	
  	/**
     * Constructs a copy of the given GeoSegment which is final(read only).
     * @requires name != null && p1 != null && p2 != null
     * @effects constructs a new copy of the given GeoSegment, this copy is 
     * final.
	 **/
  	public GeoSegment(GeoSegment gs) {
  		this.name_ = new String(gs.name_);
  		this.p1_ = new GeoPoint(gs.p1_);
  		this.p2_ = new GeoPoint(gs.p2_);
  		
  	}
  	
  	/**
  	 * check to see if the representation invariant is being violated
  	 * @throw AssertionError if representation invariant is violated
  	 */
  	private void checkRep() {
  		assert (this.name_ != null) && (this.p1_ != null) && (this.p2_ != null);
  		
  	}
  	/**
     * Returns a new GeoSegment like this one, but with its endpoints reversed.
     * @return a new GeoSegment gs such that gs.name = this.name
     *         && gs.p1 = this.p2 && gs.p2 = this.p1
     **/
  	public GeoSegment reverse() {
  		checkRep();
  		return new GeoSegment(this.name_, this.p2_, this.p1_);

  	}


  	/**
  	 * Returns the name of this GeoSegment.
     * @return the name of this GeoSegment.
     */
  	public String getName() {
  		checkRep();
  		return new String(this.name_);
  	}


  	/**
  	 * Returns first endpoint of the segment.
     * @return first endpoint of the segment.
     */
  	public GeoPoint getP1() {
  		checkRep();
  		return this.p1_;
  	}


  	/**
  	 * Returns second endpoint of the segment.
     * @return second endpoint of the segment.
     */
  	public GeoPoint getP2() {
  		checkRep();
  		return this.p2_;
  	}


  	/**
  	 * Returns the length of the segment.
     * @return the length of the segment, using the flat-surface, near the
     *         Technion approximation.
     */
  	public double getLength() {
  		checkRep();
  		return this.p1_.distanceTo(this.p2_);
  		
  	}


  	/**
  	 * Returns the compass heading from p1 to p2.
     * @requires this.length != 0 and p1 != p2
     * @return the compass heading from p1 to p2, in degrees, using the
     *         flat-surface, near the Technion approximation.
     **/
  	public double getHeading() {
  		checkRep();
  		return this.p1_.headingTo(this.p2_);
  	}


  	/**
     * Compares the specified Object with this GeoSegment for equality.
     * @return gs != null && (gs instanceof GeoSegment)
     *         && gs.name = this.name && gs.p1 = this.p1 && gs.p2 = this.p2
   	 **/
  	public boolean equals(Object gs) {
  		checkRep();
  		GeoSegment gs_t = (gs instanceof GeoSegment) ? (GeoSegment)gs : null;
  		return (gs != null) && (gs instanceof GeoSegment) && 
  				(this.name_.equals(gs_t.name_)) && (this.p1_.equals(gs_t.p1_))
  				 && (this.p2_.equals(gs_t.p2_));
  	}


  	/**
  	 * Returns a hash code value for this.
     * @return a hash code value for this.
     **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it 
    	// for improved performance. 
  		checkRep();

    	return GeoPoint.factor * this.p1_.getLatitude() + 
    			this.p2_.getLongitude();
  	}


  	/**
  	 * Returns a string representation of this.
     * @return a string representation of this.
     **/
  	public String toString() {
  		// TODO Implement this method
  		checkRep();
  		return  "GeoSegment named : " + this.name_ + " connects two endpoints, "
  			+ "p1 : " + this.p1_.toString() + " and p2 : " + this.p2_.toString()
  				 + ".";
  	}

}

