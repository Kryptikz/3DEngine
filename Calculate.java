public class Calculate {
    private static double[] vecmatmult(double[] v, double[][] m) {
        int vlen=v.length;
        double[] r = new double[]{(m[0][0]*v[0])+(m[0][1]*v[1])+(m[0][2]*v[2]),(m[1][0]*v[0])+(m[1][1]*v[1])+(m[1][2]*v[2]),(m[2][0]*v[0])+(m[2][1]*v[1])+(m[2][2]*v[2])};
        return r;
    }
    private static double[] slowvecmath(double[] v, double[][] m) {
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
    public static double[] translate(double[] vector, double x, double y, double z) {
        //double[] vector = new double[]{v.getWX(), v.getWY(), v.getWZ(), 1};
        double[][] matrix = new double[][]{{1,0,0,x},
                                           {0,1,0,y},
                                           {0,0,1,z}};
        return vecmatmult(vector,matrix);   
    }
    public static double[] scale(double[] vector, double x, double y, double z) {
        double[][] matrix = new double[][]{{x,0,0,0},
                                           {0,y,0,0},
                                           {0,0,z,0}};
        return vecmatmult(vector,matrix);  
    }
    public static double[] rotate(double[] vector, char c, double angle) {
        double[][] matrix = {};
        //vector = new double[]{vector[0],vector[1],vector[2]};
        if (c=='x') {
            matrix = new double[][]{{1,0,0,0},
                                   {0,Math.cos(angle),-(Math.sin(angle)),0},
                                   {0,Math.sin(angle),Math.cos(angle),0}};     
        }
        if (c=='y') {
            matrix = new double[][]{{Math.cos(angle),0,Math.sin(angle),0},
                                   {0,1,0,0},
                                   {-(Math.sin(angle)),0,Math.cos(angle),0}};              
            
        }
        if (c=='z') {
            matrix = new double[][]{{Math.cos(angle),-(Math.sin(angle)),0,0},
                                   {Math.sin(angle),Math.cos(angle),0,0},
                                   {0,0,1,0}};              
            
        }
        return vecmatmult(vector,matrix);  
    }  
    public static double[] project2D(double[] vector, double fov, double aspect, double near, double far) {
        //sides calculation
        double top = near*(Math.tan((Math.PI/180)*(fov/2)));
        double bottom = -1*top;
        double right = top*aspect;
        double left = -1*right;
        //Projection matrix calculation 
        double[][] matrix = new double[][]{   {Math.atan((fov/2)),0,0,0},
                                              {0,Math.atan((fov)/2),0,0},
                                              {0,0,-((far+near)/(far-near)),-((2*(far*near))/(far-near))}};        
        double[] result = vecmatmult(vector,matrix);
        //negating the results for some reason?
        double x = -1*(result[0]/1);
        double y = -1*(result[1]/1);
        //transform values into 0-1 range
        x = (x+1)/2;
        y = (y+1)/2;
        //System.out.println("(" + x + "," + y + ")");
        return(new double[]{x,y});
    }
    public static void vecmatmulttest(int runs) {
        double[][] matrix = new double[][]{{Math.random(),Math.random(),Math.random(),Math.random()},
                                               {Math.random(),Math.random(),Math.random(),Math.random()},
                                               {Math.random(),Math.random(),Math.random(),Math.random()}};
        double[] vector = new double[]{Math.random(),Math.random(),Math.random()};
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