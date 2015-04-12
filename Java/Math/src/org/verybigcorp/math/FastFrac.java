package org.verybigcorp.math;

public class FastFrac {
	private long num, denom;
	public static FastFrac ONE = new FastFrac(1,1);
	public static FastFrac ZERO = new FastFrac(0, 1);
	public FastFrac(){
		num = 0;
		denom = 1;
	}

	public FastFrac(double num, double den){
		
	}
	
	public FastFrac(long num, long den){
		this.num = num;
		this.denom = den;
		neg();
	}
	
	public FastFrac(FastFrac a){
		num = a.num;
		denom = a.denom;
	}
	
	public void setNum(long num){
		this.num = num;
		neg();
	}
	
	public long getNum(){
		return num;
	}
	
	public void setDen(long den){
		denom = den;
		neg();
	}
	
	public long getDen(){
		return denom;
	}
	
	public double evaluate(){
		return (double) num / denom;
	}
	
	public FastFrac add(FastFrac b){
		FastFrac f = new FastFrac();
		f.setNum(num * b.denom + b.num * denom);
		f.setDen(denom * b.denom);
		f.neg();
		return f.simplify();
	}
	
	public FastFrac subtract(FastFrac b){
		return add(b.negate());
	}
	
	public FastFrac mult(FastFrac b){
		FastFrac f = new FastFrac(num * b.num, denom * b.denom);
		f.neg();
		return f.simplify();
	}
	
	public FastFrac divide(FastFrac b){
		return mult(b.reciprocal());
	}
	
	public boolean lessThan(FastFrac b){
		return num * b.denom < denom * b.num;
	}
	
	public boolean greaterThan(FastFrac b){
		return num * b.denom > denom * b.num;
	}
	
	public boolean equals(FastFrac b){
		return num * b.denom == denom * b.num;
	}
	
	public FastFrac negate(){
		FastFrac f = new FastFrac(this);
		f.setNum(-f.num);
		return f;
	}
	
	public FastFrac simplify(){
		long gcf = gcd(num, denom);
		FastFrac f = new FastFrac(num / gcf, denom/gcf);
		return f;
	}
	
	public FastFrac reciprocal(){
		FastFrac r = new FastFrac(denom, num);
		return r;
	}
	
	public FastFrac abs(){
		FastFrac abs = this;
		if(abs.num < 0)
			abs.num = -num;
		if(abs.denom < 0)
			abs.denom = -denom;
		return abs;
	}
	
	public boolean isNegative(){
		return lessThan(FastFrac.ZERO);
	}
	
	public void neg(){
		if(num < 0 && denom < 0){
			num = -num;
			denom = -denom;
		} else if(num > 0 && denom < 0){
			num = -num;
			denom = -denom;
		}
	}
	
	public FastFrac pow(FastFrac p){
		if(p.equals(FastFrac.ONE)) return this;
		if(p.equals(FastFrac.ZERO)) return FastFrac.ONE;
		FastFrac pow = new FastFrac(this);
		boolean neg = p.isNegative();
		FastFrac n = p.abs();
		if(p.abs().lessThan(FastFrac.ONE)){ // Newton's Method
			System.out.println("Newton's method");
			long b = n.denom;
			System.out.println("b is " + b);
			long bless1 = b-1;
			System.out.println("bminus 1 is "+ bless1);
			FastFrac factor = new FastFrac(bless1, b);
			System.out.println("Factor is "+factor);
			FastFrac SdivideD = pow.divide(new FastFrac(b, 1));
			System.out.println("SdivideD is " + SdivideD);
			for(int i = 0; i < 10; i++){
				pow = pow.mult(factor).add(SdivideD.divide(expBySquaring(pow,bless1)));
				System.out.println("Pow is now "+pow);
			}
		} else if(p.abs().greaterThan(FastFrac.ONE) && p.evaluate() % 1 == 0){
			pow =  new FastFrac(expBySquaring(new FastFrac(num, 1), p.num).getNum(), expBySquaring(new FastFrac(denom, 1), p.denom).getNum());
		} else if(p.abs().greaterThan(FastFrac.ONE)){
			pow = pow(new FastFrac(1, p.denom)).pow(new FastFrac(p.num, 1));
		}
		return neg ? pow.reciprocal() : pow;
	}
	
	static FastFrac expBySquaring(FastFrac f, long exp){
		if(exp == 0) return FastFrac.ONE;
		if(exp == 1) return f;
		if(exp % 2 == 0){
			return expBySquaring(f.square(), exp/2);
		} else {
			return f.mult(expBySquaring(f.square(), (exp-1)/2));
		}
	}
	
	private FastFrac square(){
		return this.mult(this);
	}
	
	private long gcd(long a, long b){
		if(a == 0 || b == 0) return a+b;
		return gcd(b, a % b);
	}
	
	public String toString(){
		return num + (denom == 1 ? "" : "/"+denom);
	}
}
