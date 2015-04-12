package org.verybigcorp.math;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;

public class SquareRootTest {
	static int N_MAX = 15; // number of iterations to test, from 1
	static int N = 1000; // number of sqrt tests
	static double STEP = .001;
	
	public static void main(String[] args){
		/*PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\Ryan\\Desktop\\Math\\sqrt_test_"+N_MAX+".csv")));
			for(int i = 1; i <= N_MAX; i++){
				pw.println("n_interations = "+i);
				pw.println("n,fastSqrt(n),sqrt(n),percent error,difference,time (nanoseconds)");
				long startTime = 0;
				long totalTime = 0;
				long elapsed;
				float sqrt;
				float error;
				float totalError = 0;
				for(int n = 0; n < N; n++){
					startTime = System.nanoTime();
					sqrt = fastSqrt(n, i);
					elapsed = System.nanoTime()-startTime;
					error = (float) (sqrt == 0 || Math.sqrt(n) == 0 ? 1 : Math.abs(1-(sqrt/Math.sqrt(n))));
					totalError += error;
					totalTime += elapsed;
					pw.println(String.format("%d,%.10f,%.10f,%.10f,%.10f,%d",n,sqrt,Math.sqrt(n),error*100,Math.sqrt(n)-sqrt,elapsed));
					pw.flush();
				}
				System.out.println(N + " iterations of fastSqrt with "+i+" iterations in "+totalTime+" nanoseconds. Avg is "+totalTime/N + " nanoseconds. Avg error: "+totalError/N*100+"%");
			}
			
			pw = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\Ryan\\Desktop\\Math\\SqrtTest Iterations_"+N_MAX+".csv")));
			pw.println("n_iterations,avg percent error,avg difference,avg time (nanoseconds)");
			for(int i = 1; i <= N_MAX; i++){
				long startTime = 0;
				long totalTime = 0;
				long elapsed;
				double sqrt;
				double error;
				double totalError = 0;
				double totalDifference = 0;
				for(int n = 0; n < N; n++){
					startTime = System.nanoTime();
					sqrt = fastSqrt(n, i);
					elapsed = System.nanoTime()-startTime;
					error = sqrt == 0 || Math.sqrt(n) == 0 ? 1 : Math.abs(1-(sqrt/Math.sqrt(n)));
					totalError += error;
					totalTime += elapsed;
					totalDifference += Math.sqrt(n)-sqrt;
				}
				System.out.println("Total difference: "+totalDifference);
				pw.println(String.format("%d,%.10f,%.10f,%d", i, totalError/N*100,totalDifference/N,totalTime/N));
				pw.flush();
			}
			
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		long totalTime_fsqrt = 0;
		long totalTime_fsqrtN = 0;
		for(int i = 0; i < 1000; i++){
			totalTime_fsqrt += bench_fastSqrt(N, 10, STEP);
			totalTime_fsqrtN += bench_fastSqrt10(N,STEP);
		}
		System.out.println((int)(N/STEP)*1000*2 + " sqrts done. FSQRT(N): " + totalTime_fsqrt + " ns, avg: "+totalTime_fsqrt/N*STEP + "; FSQRTN: "+ totalTime_fsqrtN + " ns, avg: "+totalTime_fsqrtN/N*STEP);
	}
	
	public static long bench_fastSqrt(long n, int iterations, double step){
		long start = 0;
		long timeDiff = 0;
		long totalTime = 0;
		for(int i = 0; i <=(int)(n/STEP); i++){
			start = System.nanoTime();
			fastSqrt(i*STEP, iterations);
			timeDiff = System.nanoTime()-start;
			totalTime += timeDiff;
		}
		return totalTime;
	}
	
	public static long bench_fastSqrt7(long n, double step){
		long start = 0;
		long timeDiff = 0;
		long totalTime = 0;
		for(int i = 0; i <=(int)(n/STEP); i++){
			start = System.nanoTime();
			fastSqrt7(i*STEP);
			timeDiff = System.nanoTime()-start;
			totalTime += timeDiff;
		}
		return totalTime;
	}
	
	public static long bench_fastSqrt10(long n, double step){
		long start = 0;
		long timeDiff = 0;
		long totalTime = 0;
		for(int i = 0; i <=(int)(n/STEP); i++){
			start = System.nanoTime();
			fastSqrt10(i*STEP);
			timeDiff = System.nanoTime()-start;
			totalTime += timeDiff;
		}
		return totalTime;
	}
	
	static double base;
	public static double fastSqrt(double x, int n){
		if(x == 0 || x == 1) return x;
		base = x;
		for(int i = 0; ++i <= n;){
			x = .5f*(x + base/x);
		}
		return x;
	}
	
	public static double fastSqrt7(double x){
		if(x == 0 || x == 1) return x;
		base = x;
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		return x;
	}
	
	public static double fastSqrt10(double x){
		if(x == 0 || x == 1) return x;
		base = x;
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		x = .5f*(x + base/x);
		return x;
	}
}
