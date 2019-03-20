public class Calculate {
    private static double[] vecmatmult(float[] v, float[][] m) {
        double[] r = new double[]{(m[0][0]*v[0])+(m[0][1]*v[1])+(m[0][2]*v[2]),(m[1][0]*v[0])+(m[1][1]*v[1])+(m[1][2]*v[2]),(m[2][0]*v[0])+(m[2][1]*v[1])+(m[2][2]*v[2])};
        return r;
    }
    private static double[] slowvecmath(float[] v, float[][] m) {
        double tempadd = 0;
        double[] result = new double[4];
        for (int r1=0; r1<v.length; r1++) {
            for (int c1=0; c1<v.length; c1++) {
                tempadd += m[r1][c1]*v[c1];
            }
            result[r1]=tempadd;
            tempadd = 0;
        }
        return result;
    }
    public static double[] translate(float[] vector, double x, double y, double z) {
        //double[] vector = new double[]{v.getWX(), v.getWY(), v.getWZ(), 1};
        float[][] matrix = new float[][]{{1,0,0,(float)x},
                                           {0,1,0,(float)y},
                                           {0,0,1,(float)z}};
        return vecmatmult(vector,matrix);   
    }
    public static double[] scale(float[] vector, double x, double y, double z) {
        float[][] matrix = new float[][]{{(float)x,0,0,0},
                                           {0,(float)y,0,0},
                                           {0,0,(float)z,0}};
        return vecmatmult(vector,matrix);  
    }
    public static double[] rotate(float[] vector, char c, double angle) {
        float[][] matrix = {};
        //vector = new double[]{vector[0],vector[1],vector[2]};
        if (c=='x') {
            matrix = new float[][]{{1,0,0,0},
                                   {0,(float)Math.cos(angle),-(float)(Math.sin(angle)),0},
                                   {0,(float)Math.sin(angle),(float)Math.cos(angle),0}};     
        }
        if (c=='y') {
            matrix = new float[][]{{(float)Math.cos(angle),0,(float)Math.sin(angle),0},
                                   {0,1,0,0},
                                   {(float)-(Math.sin(angle)),0,(float)Math.cos(angle),0}};              
            
        }
        if (c=='z') {
            matrix = new float[][]{{(float)Math.cos(angle),-(float)(Math.sin(angle)),0,0},
                                   {(float)Math.sin(angle),(float)Math.cos(angle),0,0},
                                   {0,0,1,0}};              
            
        }
        return vecmatmult(vector,matrix);  
    }  
    public static double[] project2D(float[] vector, double fov, double aspect, double near, double far) {
        //sides calculation

        double top = near*(Math.tan((Math.PI/180)*(fov/2)));
        double bottom = -1*top;
        double right = top*aspect;
        double left = -1*right;
        //Projection matrix calculation 
        float fn=(float)(far-near);
        float mathtf=(float)(Math.atan(fov/2));
        float[][] matrix = new float[][]{   {mathtf,0,0,0},
                                              {0,mathtf,0,0},
                                              {0,0,-1*(float)((far+near)/fn),-1*(float)((2*(far*near))/fn)}};        
        double[] result = vecmatmult(vector,matrix);
        //negating the results for some reason?
        double x = -1*(result[0]);
        double y = -1*(result[1]);
        //transform values into 0-1 range
        x = (x+1)/2;
        y = (y+1)/2;
        //System.out.println("(" + x + "," + y + ")");
        return(new double[]{x,y});
    }
    public static void vecmatmulttest(int runs) {
        float[][] matrix = new float[][]{{(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()},
                                               {(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()},
                                               {(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()}};
        float[] vector = new float[]{(float)Math.random(), (float)Math.random(), (float)Math.random()};
        long fastsum=0;
        long slowsum=0;
        long projsum=0;
        for(int i=0;i<runs;i++) {
            long startf = System.currentTimeMillis();
            int calcf=0;
            while(System.currentTimeMillis()-1000<startf) {
                vecmatmult(vector,matrix);
                calcf++;
            }
            long starts = System.currentTimeMillis();
            int calcs=0;
            while(System.currentTimeMillis()-1000<starts) {
                slowvecmath(vector,matrix);
                calcs++;
            }
            long startp = System.currentTimeMillis();
            int calcp=0;
            while(System.currentTimeMillis()-1000<startp) {
                project2D(vector,90,1,0,100);
                calcp++;
            }
            fastsum+=calcf;
            slowsum+=calcs;
            projsum+=calcp;
            System.out.println("fastmath calculations per second: " + calcf);
            System.out.println("slowmath calculations per second: " + calcs);
            System.out.println("projections per second: " + calcp);
        }
        System.out.println("\n\nAverage fastmath cps: " + fastsum/runs + "\nAverage slowmath cps: " + slowsum/runs + "\nAverage projection cps: " + projsum/runs);
    }
}
