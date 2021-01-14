package homework1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless
 * of their names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment 
 * to the end of a Route. An added segment must be properly oriented; that 
 * is, its p1 field must correspond to the end of the original Route, and
 * its p2 field corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily
 * the same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features,
 * using the <tt>getGeoFeatures()</tt> method which returns an Iterator of
 * GeoFeature objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {
	
	
	private final  ArrayList<GeoFeature> geoFeatures_;

	
 	// TODO Write abstraction function and representation invariant
	
	// Abstraction Function :
	// represents  geographic route which starts and ends with gs endpoints.
	// this route consists of geographic features connected with each other 
	// connecting the two endpoints the start and the end.
	
	// Representation Invariant :
	// geoFeqtures != null and start != null and end != null
	// and {geoFeature != null | geoFeature contained in geoFetures list} 


  	/**
  	 * Constructs a new Route.
     * @requires gs != null
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public Route(GeoSegment gs) {
  		
  		assert gs != null;
  		
  		this.geoFeatures_ = new ArrayList<GeoFeature>();
  		this.geoFeatures_.add(new GeoFeature(new GeoSegment(gs)));
  		checkRep();
  	}
  	
	/** Constructs a copy constructor.
  	 * @requires r != null
  	 * @param gf
  	 * @effects Constructs a new copy of the given route,
  	 * 			such that :
  	 * 			copy contains all the geoFeatures in r and in the same order.
  	 **/ 
  	public Route(Route r) {
  		assert r != null;
  		r.checkRep();
  		this.geoFeatures_ = new ArrayList<GeoFeature>();
  		for(GeoFeature gf : r.geoFeatures_) 
  			this.geoFeatures_.add(new GeoFeature(gf));
  	}
  	
	/**
  	 * check to see if the representation invariant is being violated
  	 * @throw AssertionError if representation invariant is violated
  	 */
  	private void checkRep() {
  		assert (this.geoFeatures_ != null);
  		
  		// check that for each geoFeature no null element exists
  		for(GeoFeature gf : this.geoFeatures_) {
  			assert gf != null;
  			Iterator<GeoSegment> it = gf.getGeoSegments();
  			while(it.hasNext()) {
  				assert(it != null);
  				it.next();
  			}
  		}	
  	}

    /**
     * Returns location of the start of the route.
     * @return location of the start of the route.
     **/
  	public GeoPoint getStart() {
  		checkRep();
  		return this.geoFeatures_.get(0).getStart();
  	}


  	/**
  	 * Returns location of the end of the route.
     * @return location of the end of the route.
     **/
  	public GeoPoint getEnd() {
  		checkRep();
  		return this.geoFeatures_.get(this.geoFeatures_.size() - 1).getEnd();
  	}

  	/**
  	 * Returns direction of travel at the start of the route, in degrees.
  	 * @requires start != the other endPoint on the same geoSegment 
   	 * @return direction (in compass heading) of travel at the start of the
   	 *         route, in degrees.
   	 **/
  	public double getStartHeading() {
  		checkRep();
  		return this.geoFeatures_.get(0).getStartHeading();
  	}


  	/**
  	 * Returns direction of travel at the end of the route, in degrees.
  	 * @requires end != the other endPoint on the same geoSegment 
     * @return direction (in compass heading) of travel at the end of the
     *         route, in degrees.
     **/
  	public double getEndHeading() {
  		checkRep();
  		return this.geoFeatures_.get(this.geoFeatures_.size() - 1).getEndHeading();
  	}


  	/**
  	 * Returns total length of the route.
     * @return total length of the route, in kilometers.  NOTE: this is NOT
     *         as-the-crow-flies, but rather the total distance required to
     *         traverse the route. These values are not necessarily equal.
   	 **/
  	public double getLength() {
  		checkRep();
  		double sumOfLengths = 0;
  		for(GeoFeature gf: this.geoFeatures_)
  			sumOfLengths += gf.getLength();
  		checkRep();
  		return sumOfLengths;
  	}

  	/**
     * Creates a new route that is equal to this route with gs appended to
     * its end.
   	 * @requires gs != null && gs.p1 == this.end
     * @return a new Route r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *         r.length = this.length + gs.length
     **/
  	public Route addSegment(GeoSegment gs) {
  		checkRep();
  		assert gs != null;
  		int lastIndex = this.geoFeatures_.size() - 1;
  		GeoFeature lastGeoFeature= this.geoFeatures_.get(lastIndex);
  		
  		assert gs.getP1().equals(lastGeoFeature.getEnd());
  		
  		// create a new route such that :
  		// start still the same, end changes to be p2 of gs.
  		Route r = new Route(this);
  		
  		
  		if(lastGeoFeature.getName().equals(gs.getName())) {
  			GeoFeature gf  = lastGeoFeature.addSegment(new GeoSegment(gs));
  			r.geoFeatures_.remove(lastIndex);
  			r.geoFeatures_.add(gf);
  			checkRep();
  			return r;
  		}
  		
  		// otherwise the given goeSegment (gs) is not part of the 
  		// last geographic feature, so make a new geographic feature
  		// which includes gs as the first Segment in it.
  		GeoFeature newGf = new GeoFeature(new GeoSegment(gs));
  		r.geoFeatures_.add(newGf);
  		checkRep();
  		return r;
  	}


    /**
     * Returns an Iterator of GeoFeature objects. The concatenation
     * of the GeoFeatures, in order, is equivalent to this route. No two
     * consecutive GeoFeature objects have the same name.
     * @return an Iterator of GeoFeatures such that
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
  	public Iterator<GeoFeature> getGeoFeatures() {
  		checkRep();
  		int size = this.geoFeatures_.size(); 
  		Iterator<GeoFeature> it = Collections.unmodifiableList(
  								 	this.geoFeatures_).iterator();
  		for(int i = 0; i < size - 1 ; i++) {
  			GeoFeature gf = this.geoFeatures_.get(i);
  			GeoFeature gfNext = this.geoFeatures_.get(i + 1);
  			assert !(gf.getName().equals(gfNext.getName()));
  			assert gf.getEnd().equals(gfNext.getStart());	
  		}
  		checkRep();
  		return it;
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
  	public Iterator<GeoSegment> getGeoSegments() {
  		checkRep();
  		
  		// add all route segment to global list :
  		ArrayList<GeoSegment> globalList = new ArrayList<GeoSegment>(); 
  		for(GeoFeature gf : this.geoFeatures_) {
  			Iterator<GeoSegment> geoSegmentIt = gf.getGeoSegments();
  			while(geoSegmentIt.hasNext())
  				globalList.add(geoSegmentIt.next());
  		}
  		
  		// iterates over unmodifiable list :
  		Iterator<GeoSegment> it = Collections.unmodifiableList(globalList).iterator();
  		// check if global list satisfies basic conditions for start and end points.
  		assert (globalList.get(0).getP1().equals(this.getStart()));
  		assert (globalList.get(0).getHeading() == this.getStartHeading());
  		assert (globalList.get(globalList.size() - 1).getP2().equals(this.getEnd()));
  		assert (globalList.get(globalList.size() - 1).getHeading() == this.getEndHeading()); 
  		// check that the total length of the Segments in the global list 
  		// is equivalent to the route length.
  		double len = 0;
  		for(GeoSegment gs : globalList)
  			len += gs.getLength();
  		assert len == this.getLength();
  	
  		checkRep();
  		return it;
  		
  	}


  	/**
     * Compares the specified Object with this Route for equality.
     * @return true iff (o instanceof Route) &&
     *         (o.geoFeatures and this.geoFeatures contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		checkRep();
  		Route r = (o instanceof Route) ? (Route)o : null;
  		if(r == null || this.geoFeatures_.size() != r.geoFeatures_.size())
  			return false;
  		for(int i = 0; i < this.geoFeatures_.size(); i++) {
  			GeoFeature gfi = this.geoFeatures_.get(i);
  			GeoFeature ri = r.geoFeatures_.get(i);
  			if(!gfi.equals(ri)) return false;		
  		}
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
  				this.getEnd().getLongitude();
  	}


    /**
     * Returns a string representation of this.
     * @return a string representation of this.
     **/
  	public String toString() {
  		checkRep();
  		return "Route start is : " + this.getStart().toString() + ", end is : " + 
  		this.getEnd().toString()+ ", with total length : " + 
  		String.valueOf(this.getLength()) + ".";
  				
  	}

}
