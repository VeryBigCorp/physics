package org.verybigcorp.math;

public class BinaryNumber {
	String binaryRepresentation;
	
	public BinaryNumber(long val){
		binaryRepresentation = binary(val);
	}
	
	public long getDecimalValue(){
		long val = 0;
		for(int i = binaryRepresentation.length(); --i >= 0;){
			val += java.lang.Math.pow(2,binaryRepresentation.length()-i-1)*bit(i);
		}
		return val;
	}
	
	public String binary(long val){
		String rep = "";
		int bits = bits();
		for(int i = 0; i++ < bits;){
			rep = val % 2 + rep;
			val /= 2;
		}
		return rep;
	}
	
	private int bit(int position){
		return binaryRepresentation.charAt(position) == '1' ? 1 : 0;
	}
	
	/*
	public BinaryNumber shift(int n){
		if(n < 0)
			binaryRepresentation = binaryRepresentation + "0";
		else
			for(int i = ){
				
			}
		return this;
	}*/
	
	public int bits(){
		return (int) (java.lang.Math.log(getDecimalValue())/java.lang.Math.log(2))+1;
	}
	/*
	public BinaryNumber add(BinaryNumber b){
		boolean carry;
		boolean sum;
		for(int i = 0; i < b.bits(); i++){
			
		}
		
		return null;
	}
	
	/*
	public BinaryNumber mult(BinaryNumber b){
		BinaryNumber result = ;
		for(int i = 0; i < b.bits()-1; i++){
			
		}
		return result;
	}*/
}
