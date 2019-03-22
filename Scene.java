import java.util.*;
import java.awt.*;
import javax.swing.*;
public class Scene extends JComponent {
    public final int ASPECT = 1;
    public final int FOV = 90;
    public final int NEAR = 1;
    public final int FAR = 100;
    public final int WIDTH = 900;
    public final int HEIGHT = 900;
    private ArrayList<float[]> points; //each point is referenced by index in this specific array, starts at 0
    private ArrayList<int[]> polygons;
    public int psize;
    public Scene() {
        points = new ArrayList<float[]>();
        polygons = new ArrayList<int[]>();
        psize=0;
    }
    public int addPoint(float[] p) {
        try {
            points.add(p);
            psize++;
        } catch (Exception e) {
            System.out.println("A fatal error ocurred");
            System.exit(1);
        }
        return psize-1;
    }
    public void addPolygon(int[] p) {
        try {
            polygons.add(p);
        } catch (Exception e) {
            System.out.println("A fatal error ocurred");
            System.exit(1);
        }
    }
    public void translatePoint(int p, double x,double y, double z) {
        points.set(p,(Calculate.translate(points.get(p),x,y,z)));
    }
    public void frame() {
        this.repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        for(float[] pv : points) {
            if (pv[2]<FAR&&pv[2]>NEAR) {
                double[] render = Calculate.project2D(pv,FOV,ASPECT,NEAR,FAR);
                g.fillRect((int)(render[0]*WIDTH),(int)(render[1]*HEIGHT),4,4);
            }
            //System.out.println("x: " + render[0]*WIDTH + "  y: " + render[1]*HEIGHT);
        }
    }
}