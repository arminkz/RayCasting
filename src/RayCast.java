import java.util.ArrayList;

public class RayCast {

    public static final double EPSILON = 0.000001;

    public static double crossProduct(Vector a, Vector b) {
        return a.x * b.y - b.x * a.y;
    }

    public static double distance(Point a,Point b){
        return Math.sqrt(Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2));
    }

    // Find intersection of RAY & SEGMENT
    // returns null if no intersection found
    public static Point getIntersection(LineSegment ray, LineSegment segment){
        Vector r = new Vector(ray.B.x - ray.A.x,ray.B.y-ray.A.y);
        Vector s = new Vector(segment.B.x - segment.A.x,segment.B.y-segment.A.y);
        double rxs = crossProduct(r,s);

        Vector qp = new Vector(segment.A.x - ray.A.x,segment.A.y - ray.A.y);
        double qpxr = crossProduct(qp,r);

        // If r x s = 0 and (q - p) x r = 0, then the two lines are collinear.
        if (rxs < EPSILON && qpxr < EPSILON)
        {
            // 1. If either  0 <= (q - p) * r <= r * r or 0 <= (p - q) * s <= * s
            // then the two lines are overlapping,
            /*if (considerCollinearOverlapAsIntersect)
                if ((0 <= (q - p)*r && (q - p)*r <= r*r) || (0 <= (p - q)*s && (p - q)*s <= s*s))
                    return true;*/

            // 2. If neither 0 <= (q - p) * r = r * r nor 0 <= (p - q) * s <= s * s
            // then the two lines are collinear but disjoint.
            // No need to implement this expression, as it follows from the expression above.
            return null;
        }

        // 3. If r x s = 0 and (q - p) x r != 0, then the two lines are parallel and non-intersecting.
        if (rxs < EPSILON && qpxr >= EPSILON)
            return null;

        // t = (q - p) x s / (r x s)
        //var t = (q - p).Cross(s)/rxs;
        double t = crossProduct(qp,s)/rxs;
        // u = (q - p) x r / (r x s)
        //var u = (q - p).Cross(r)/rxs;
        double u = crossProduct(qp,r)/rxs;
        // 4. If r x s != 0 and 0 <= t <= 1 and 0 <= u <= 1
        // the two line segments meet at the point p + t r = q + u s.
        if (rxs >= EPSILON && (0 <= t && t <= 1) && (0 <= u && u <= 1))
        {
            // We can calculate the intersection point using either t or u.
            //intersection = p + t*r;

            return new Point((int)Math.floor(ray.A.x + t*r.x),(int)Math.floor(ray.A.y + t*r.y));

            // An intersection was found.
            //return true;
        }

        // 5. Otherwise, the two line segments are not parallel but do not intersect.
        return null;
    }

    public static Point getClosestIntersection(LineSegment ray,ArrayList<LineSegment> segments){
        Point closestIntersect = null;
        double closestDistance = Double.MAX_VALUE;
        for(LineSegment l : segments){
            Point intersect = getIntersection(ray,l);
            if(intersect == null) continue;
            if(closestIntersect == null || distance(ray.A,intersect) < closestDistance){
                closestIntersect = intersect;
                closestDistance = distance(ray.A,intersect);
            }
        }
        for(LineSegment l : segments){
            Point intersect = getIntersection(l,ray);
            if(intersect == null) continue;
            if(closestIntersect == null || distance(ray.A,intersect) < closestDistance){
                closestIntersect = intersect;
                closestDistance = distance(ray.A,intersect);
            }
        }
        return closestIntersect;
    }

}
