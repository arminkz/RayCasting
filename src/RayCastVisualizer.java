import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * Created by Armin on 9/21/2017.
 */
public class RayCastVisualizer extends JPanel implements MouseListener , MouseMotionListener{

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("RayCast Visualizer");
        window.setSize(640,380);

        RayCastVisualizer rcv = new RayCastVisualizer();

        window.add(rcv);
        //window.addMouseMotionListener(rcv);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public RayCastVisualizer(){
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);

        JButton solveButton = new JButton("Solve !");
        solveButton.setSize(100,30);
        solveButton.setLocation(10,10);
        solveButton.addActionListener((ActionEvent e) -> {startSolving();
        });

        JButton spButton = new JButton("Start Polygon");
        spButton.setSize(100,30);
        spButton.setLocation(120,10);
        spButton.addActionListener((ActionEvent e) -> {startPolygon();
        });

        JButton epButton = new JButton("End Polygon");
        epButton.setSize(100,30);
        epButton.setLocation(230,10);
        epButton.addActionListener((ActionEvent e) -> {endPolygon();
        });

        this.setLayout(null);
        //this.add(solveButton);
        //this.add(spButton);
        //this.add(epButton);

        initPolygons();
        initSegments();
        addMouseMotionListener(this);
    }

    ArrayList<Polygon> activePolygons = new ArrayList<>();
    private void initPolygons(){

        //Border Polygon
        Polygon b = new Polygon();
        b.addPoint(0,0);
        b.addPoint(640,0);
        b.addPoint(640,360);
        b.addPoint(0,360);
        activePolygons.add(b);


        Polygon p1 = new Polygon();
        p1.addPoint(100,150);
        p1.addPoint(120,50);
        p1.addPoint(200,80);
        p1.addPoint(140,210);
        activePolygons.add(p1);

        Polygon p2 = new Polygon();
        p2.addPoint(100,200);
        p2.addPoint(120,250);
        p2.addPoint(60,300);
        activePolygons.add(p2);

        Polygon p3 = new Polygon();
        p3.addPoint(200,260);
        p3.addPoint(220,150);
        p3.addPoint(300,200);
        p3.addPoint(350,320);
        activePolygons.add(p3);

        Polygon p4 = new Polygon();
        p4.addPoint(340,60);
        p4.addPoint(360,40);
        p4.addPoint(370,70);
        activePolygons.add(p4);

        Polygon p5 = new Polygon();
        p5.addPoint(450,190);
        p5.addPoint(560,170);
        p5.addPoint(540,270);
        p5.addPoint(430,290);
        activePolygons.add(p5);

        Polygon p6 = new Polygon();
        p6.addPoint(400,95);
        p6.addPoint(580,50);
        p6.addPoint(480,150);
        p6.addPoint(400,95);
        activePolygons.add(p6);
    }

    ArrayList<LineSegment> activeSegments = new ArrayList<>();
    private void initSegments(){
        for(Polygon p : activePolygons){
            for(int i=0;i<p.npoints;i++){

                Point start = new Point(p.xpoints[i],p.ypoints[i]);
                Point end;
                if(i==p.npoints-1){
                    end = new Point(p.xpoints[0],p.ypoints[0]);
                }else{
                    end = new Point(p.xpoints[i+1],p.ypoints[i+1]);
                }
                activeSegments.add(new LineSegment(start,end));
                System.out.println("new segment : " + start + " -> " + end);
            }
        }
    }

    Point mousePos = new Point(1,1);
    Point rayCenter = new Point(320,180);

    ArrayList<Point> currentRays = new ArrayList<>();

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = new Point(e.getX(),e.getY());
        currentRays = castRays(mousePos,50,800);
        repaint();
    }

    public ArrayList<Point> castRays(Point src,int n,int dist){
        ArrayList<Point> result = new ArrayList<>();
        double angle_div = 2 * Math.PI / n;
        for (int i = 0; i < n; i++) {
            Point target = new Point((int)(src.x+Math.cos(angle_div*i)*dist),(int)(src.y+Math.sin(angle_div*i)*dist));
            LineSegment ray = new LineSegment(src,target);
            Point ci = RayCast.getClosestIntersection(ray,activeSegments);
            if(ci != null) result.add(ci);
            else result.add(target);
        }
        return result;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        for(Polygon p : activePolygons){
            g.drawPolygon(p);
        }

        g.setColor(Color.RED);
        for(Point p : currentRays){
            g.drawLine(mousePos.x,mousePos.y,p.x,p.y);
            g.fillOval(p.x-5,p.y-5,10,10);
        }

    }


    Polygon activePoly;
    ArrayList<Polygon> donePoly = new ArrayList<>();

    Point p1 = new Point(500,600);
    Point q1 = new Point(600,600);

    Point p2 = new Point(500,550);
    Point q2 = new Point(650,650);

    boolean isDrawingPoly = false;

    private void startPolygon(){
        isDrawingPoly = true;
        activePoly = new Polygon();
    }

    private void endPolygon(){
        donePoly.add(activePoly);
        isDrawingPoly = false;
        repaint();
    }

    private void startSolving(){

    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if(isDrawingPoly){
            activePoly.addPoint(e.getX(),e.getY());
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }


}
