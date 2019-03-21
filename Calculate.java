public class Calculate {
    private static double[] vecmatmult(float[] v, float[][] m) {
        double[] r = new double[]{(m[0][0]*v[0])+(m[0][1]*v[1])+(m[0][2]*v[2]),(m[1][0]*v[0])+(m[1][1]*v[1])+(m[1][2]*v[2]),(m[2][0]*v[0])+(m[2][1]*v[1])+(m[2][2]*v[2])};
        return r;
    }
    public static double[] translate(float[] vector, double x, double y, double z) {
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
        float asin=(float)(Math.sin(angle));
        float acos=(float)(Math.cos(angle));
        if (c=='x') {
            matrix = new float[][]{{1,0,0,0},
                                   {0,acos,-1*asin,0},
                                   {0,asin,acos,0}};     
        }
        if (c=='y') {
            matrix = new float[][]{{acos,0,asin,0},
                                   {0,1,0,0},
                                   {-1*asin,0,acos,0}};              
            
        }
        if (c=='z') {
            matrix = new float[][]{{acos,-1*asin,0,0},
                                   {asin,acos,0,0},
                                   {0,0,1,0}};              
            
        }
        return vecmatmult(vector,matrix);  
    }  
    public static double[] project2D(float[] vector, double fov, double aspect, double near, double far) {
        float fn=(float)(far-near);
        float mathtf=(float)(Math.atan(fov/2));
        float[][] matrix = new float[][]{   {mathtf,0,0,0},
                                              {0,mathtf,0,0},
                                              {0,0,-1*(float)((far+near)/fn),-1*(float)((2*(far*near))/fn)}};        
        double[] result = vecmatmult(vector,matrix);
        double x = -1*(result[0]);
        double y = -1*(result[1]);
        x+=1;
        x/=2;
        y+=1;
        y/=2;
        return(new double[]{x,y});
    }
    public static void vecmatmulttest(int runs) {
        float[][] matrix = new float[][]{{(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()},
                                               {(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()},
                                               {(float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random()}};
        float[] vector = new float[]{(float)Math.random(), (float)Math.random(), (float)Math.random()};
        long fastsum=0;
        long projsum=0;
        for(int i=0;i<runs;i++) {
            long startf = System.currentTimeMillis();
            int calcf=0;
            while(System.currentTimeMillis()-1000<startf) {
                vecmatmult(vector,matrix);
                calcf++;
            }
            long startp = System.currentTimeMillis();
            int calcp=0;
            while(System.currentTimeMillis()-1000<startp) {
                project2D(vector,90,1,0,100);
                calcp++;
            }
            fastsum+=calcf;
            projsum+=calcp;
            System.out.println("fastmath calculations per second: " + calcf);
            System.out.println("projections per second: " + calcp);
        }
        System.out.println("\n\nAverage fastmath cps: " + fastsum/runs + "\nAverage projection cps: " + projsum/runs);
    }
}
