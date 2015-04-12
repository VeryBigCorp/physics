package org.verybigcorp.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Math {
	public static int PREC = 5;
	static MathContext context = new MathContext(PREC, RoundingMode.HALF_EVEN);
	
	// Exponentiation functions
	public static double pow(double a, double p){
		double pow = a;
		if(abs(p) < 1){ // Newton's Method
			double oneover = 1/p;
			System.out.println(p + " " + oneover);
			for(long i = 0; i < 200; i++){
				pow = pow - pow*p + a/(oneover*pow(pow, oneover-1));
			}
		}
		
		if(abs(p) > 1 && p % 1 == 0){
			pow = 1;
			for(int i = 0; i < p; i++)
				pow *= a;
		} else if(abs(p) >1 && p % 1 != 0){
			System.out.println("ey" + p);
			Fraction f = Fraction.fromDec(BigDecimal.valueOf(p));
			System.out.println(a+"^(1/"+(1.0/f.getDenominator().intValue()) + "^"+f.getNumerator().intValue());
			return pow(pow(a, 1.0/f.getDenominator().intValue()), f.getNumerator().intValue());
		}
		return pow;
	}
	
	public static Fraction pow(BigDecimal a, BigDecimal b){
		Fraction pow = new Fraction(a, BigDecimal.ONE);
		System.out.println("Power is "+pow + "; b is "+b);
		if(abs(b).signum() == 0)
			return new Fraction(BigDecimal.ONE, BigDecimal.ONE);
		if(abs(b).compareTo(BigDecimal.ONE) == 0){
			return new Fraction(a, BigDecimal.ONE);
		}
		System.out.println("Decimal pow!");
		BigDecimal p = BigDecimal.ONE;
		for(BigInteger i = BigInteger.ZERO; i.compareTo(b.toBigInteger().multiply(BigInteger.valueOf(2))) < 0; i = i.add(BigInteger.ONE)){
			p = p.multiply(a);
		}
		return new Fraction(p, BigDecimal.ONE);
		//return b.signum() == 1 ? pow : pow.reciprocal();
	}
	
	public static Fraction pow(Fraction a, Fraction b){
		if(b.equals(Fraction.ONE))
			return a;
		if(b.equals(Fraction.ZERO))
			return Fraction.ONE;
		Fraction pow = a;
		if(b.abs().lessThan(Fraction.ONE)){
			
		} else if(b.abs().greaterThan(Fraction.ONE) && b.mod(Fraction.ONE).equals(Fraction.ZERO)){
			System.out.println("Powwing");
			pow = new Fraction(pow(a.getNumerator(), b.evaluate().toBigInteger()), pow(a.getDenominator(), b.evaluate().toBigInteger()));
			System.out.println("Ended powwing");
			//System.out.println(b.evaluate().toBigInteger());
		} else if(!b.mod(Fraction.ONE).equals(Fraction.ZERO)){
			
		}
		
		return pow;
	}
	
	public static BigInteger pow(BigInteger a, BigInteger b){
		BigInteger pow = BigInteger.ONE;
		for(BigInteger i = BigInteger.ZERO; i.compareTo(b.abs()) == -1; i = i.add(BigInteger.ONE)){
			pow = pow.multiply(a);
		}
		return pow;
	}
	
	public static BigDecimal pow10(int pow){
		BigDecimal p = new BigDecimal("1");
		p = p.movePointRight(pow);		
		return p;
	}
	
	public static BigInteger quickPow(BigInteger b, BigInteger k){
		if(k.compareTo(BigInteger.ZERO) == 0) return BigInteger.ONE;
		if(k.compareTo(BigInteger.ONE) == 0) return b;
		if(k.remainder(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0){ // Squaring, even
			return quickPow(b.pow(2), k.divide(BigInteger.valueOf(2)));
		} else {
			return b.multiply(quickPow(b.pow(2), k.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2))));
		}
	}
	
	static BigInteger pow2(BigInteger base, BigInteger exponent) {
		  BigInteger result = BigInteger.ONE;
		  while (exponent.signum() > 0) {
		    if (exponent.testBit(0)) result = result.multiply(base);
		    base = base.multiply(base);
		    exponent = exponent.shiftRight(1);
		  }
		  return result;
	}
	
	public static double sqrt(double a){
		return pow(a, .5);
	}
	
	public static BigDecimal log_10(BigDecimal x){
		int PREC = 100; // 100 digits of precision
		MathContext context = new MathContext(PREC, RoundingMode.HALF_EVEN);
		if(x.abs().compareTo(BigDecimal.ONE) == -1){
			return log_10(BigDecimal.ONE.divide(x, context)).negate(); // -log_10(1/x)
		} else if(x.compareTo(BigDecimal.ONE) == 0){
			return BigDecimal.ZERO;
		} else if(x.compareTo(BigDecimal.ZERO) <= 0){
			try {
				throw new Exception("Can't compute logarithm of a number <= 0!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int nMantissa = x.precision()-x.scale();
		StringBuilder b = new StringBuilder(PREC).append(nMantissa-1).append(".");
		for(int i = 0; i < PREC; i++){
			x = x.movePointLeft(nMantissa-1).pow(10, context);
			nMantissa = x.precision() - x.scale();
			b.append(nMantissa-1);
		}
		return new BigDecimal(b.toString());
	}
	
	public static BigDecimal log(BigDecimal base, BigDecimal x){
		return log_10(x).divide(log_10(base), context);
	}
	
	public static double abs(double a){
		if(a < 0)
			a = -a;
		return a;
	}
	
	public static BigDecimal abs(BigDecimal b){
		return b.signum() == -1 ? b.negate() : b;
	}
	
	
	public static BigInteger mod(BigInteger a, BigInteger b){
		if(a.compareTo(b) == -1)
			return a;
		return a.subtract(b.multiply(a.divide(b)));
	}
	
	public static BigDecimal mod(BigDecimal a, BigDecimal b){
		if(a.compareTo(b) == -1)
			return a;
		return a.subtract(b.multiply(floor(a.divide(b))));
	}
	
	public static BigDecimal floor(BigDecimal a){
		return new BigDecimal(a.toBigInteger());
	}
}
