import java.awt.*;
import javax.swing.*;
public class Sample1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Render Window");
        frame.setVisible(true);
        frame.setSize(900,900);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Scene samp = new Scene();
        frame.add(samp);
        samp.setVisible(true);
        for(int z=10;z<100;z++) {
            for(int y=-10;y<10;y++) {
                for(int x=-10;x<10;x++) {
                    int i =samp.addPoint(new float[]{((float)(x))/10,((float)(y))/10,((float)(z))/10,1});
                    //System.out.println(i);
                    //samp.frame();
                    try {
                        //Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //samp.frame();
            try {
                //Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
               
        }
        samp.frame();
        while(true) {
            for(int i=0;i<20;i++) {
                for(int i2=0;i2<samp.psize;i2++) {
                    samp.translatePoint(i2,0,0,.1);
                }
                samp.frame();
                try {
                    Thread.sleep(17);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for(int i=0;i<20;i++) {
                for(int i2=0;i2<samp.psize;i2++) {
                    samp.translatePoint(i2,0,.1,0);
                }
                samp.frame();
                try {
                    Thread.sleep(17);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for(int i=0;i<20;i++) {
                for(int i2=0;i2<samp.psize;i2++) {
                    samp.translatePoint(i2,.1,0,0);
                }
                samp.frame();
                try {
                    Thread.sleep(17);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for(int i=0;i<20;i++) {
                for(int i2=0;i2<samp.psize;i2++) {
                    samp.translatePoint(i2,-.1,-.1,-.1);
                }
                samp.frame();
                try {
                    Thread.sleep(17);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}