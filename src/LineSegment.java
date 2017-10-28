public class LineSegment {

    public Point A;
    public Point B;
    public Vector dir;

    public LineSegment(Point A,Point B){
        this.A = A;
        this.B = B;
        dir = new Vector(B.x-A.x,B.y-A.y);
    }

    public LineSegment(Point A,Vector dir){
        this.A = A;
        this.dir = dir;
        this.B = new Point(A.x + dir.x,A.y + dir.y);
    }

    @Override
    public String toString() {
        return "(" + A + " -> " + B + ")";
    }



}
