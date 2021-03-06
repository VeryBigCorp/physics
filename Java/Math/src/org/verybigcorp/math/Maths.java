package org.verybigcorp.math;

public class Maths {
	static long time;
	public static void main(String[] args){
		//FastFrac a = new FastFrac(2, 1);
		//FastFrac b = new FastFrac(1, 2);
		//System.out.println(a.pow(b).evaluate());
		long startTime = System.currentTimeMillis();
		//for(int i = 0; i < 6; i++)
			//for(int j = 0; j < 6; j++){
				//startTime = System.currentTimeMillis();
				//System.out.println(String.format("Ackermann (%d,%d) = %d", 3, 13, ackermann(3,13)));
				//System.out.println(String.format("Time taken: %f seconds",(System.currentTimeMillis()-startTime)/1000.0f));
			//}
		/*long totalTime = 0;
		long elapsed;
		float sqrt;
		int N = 100;
		float error;
		float totalError = 0;
		for(int i = 0; i <= N; i++){
			startTime = System.nanoTime();
			sqrt = fastSqrt(i);
			elapsed = System.nanoTime()-startTime;
			error = (float) (sqrt == 0 || Math.sqrt(i) == 0 ? 1 : Math.abs(1-(sqrt/Math.sqrt(i))));
			totalError += error;
			totalTime += elapsed;
			System.out.println(String.format("Sqrt of %03d is %.10f; Completed in %d nanoseconds; Error: %.10f%%",i,sqrt, elapsed, error*100));
		}
		System.out.println(N + " iterations of fastSqrt in "+totalTime+" nanoseconds. Avg is "+totalTime/N + " nanoseconds. Avg error: "+totalError/N*100+"%");*/
		//System.out.println(a.pow(b).evaluate());
		//System.out.println(a.pow(b).evaluate());
		//BinaryNumber n = new BinaryNumber(1024);
		//System.out.println(n.binaryRepresentation);
		/*
		int k = 3;
		
		double[][] data = new double[k][k];
		for(int i = 1; i <= k; i++)
			for(int j = 1; j <= k; j++)
				data[i-1][j-1] = java.lang.Math.pow(i, j);
		
		double[][] rhs = new double[k][1];
		for(int j = 1; j <= k; j++)
			rhs[j-1][0] = sum(k, j);
		Matrix eqn = new Matrix(data); // Set up system of equations
		eqn.show();
		Matrix r = new Matrix(rhs);
		Matrix solve = eqn.solve(r);
		
		solve.show();
		
		*/
		
		double sum = sum(1, 3);
	}
	
	static double sum(int k, int j){
		double sum = 0;
		for(int i = 0; i < j; i++){
			sum += java.lang.Math.pow(j, k);
		}
		return sum;
	}
	
	static int ackermann(int m, int n){
		if(m == 0) return n + 1;
		if(n == 0) return ackermann(m-1, 1);
		else return ackermann(m-1, ackermann(m, n-1));
	}
	
	static float base;
	public static float fastSqrt(float x){
		if(x == 0 || x == 1) return x;
		base = x;
		for(int i = 0; ++i <= 6;){
			x = .5f*(x + base/x);
		}
		return x;
	}
}
