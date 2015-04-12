package ryan.cphys.helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Output {
	PrintWriter pw;
	
	HashMap<String, ArrayList> columns;
	
	String[] cols;
	public Output(String fName, String[] columns){
		this.columns = new HashMap<String, ArrayList>();
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(fName)));
		} catch (IOException e) {
			System.out.println("Unable to open PrintWriter to write to file!");
			e.printStackTrace();
		}
		
		for(String s : columns){
			this.columns.put(s, null);
		}
		cols = columns;
	}
	
	@SuppressWarnings("unchecked")
	public <T> void addVal(String col, T a){
		if(columns.get(col) == null){
			columns.put(col, new ArrayList<T>());
		}
		columns.get(col).add(a);
	}
	
	public <T> void setColumn(String col, ArrayList<T> vals){
		columns.put(col, vals);
	}
	
	public void write(){
		// Output Heading
		String[] keySet = cols;
		
		for(int i = 0; i < keySet.length-1; i++){
			pw.print(keySet[i] + ",");
		}
		pw.println(keySet[keySet.length-1]);
		
		// Find max column length
		int maxLen = 0;
		for(ArrayList col : columns.values())
			if(col.size() > maxLen)
				maxLen = col.size();
		
		
		StringBuilder b = new StringBuilder();
		for(int r = 0; r < maxLen; r++){
			for(int c = 0; c < columns.size()-1; c++){
				if(r < columns.get(keySet[c]).size())
					b.append(columns.get(keySet[c]).get(r)).append(",");
				else
					b.append(",");
			}
			b.append(columns.get(keySet[columns.size()-1]).get(r));

			pw.println(b);
			
			b.setLength(0);
			b.trimToSize();
		}
		
		pw.flush();
	}
	
	public void close(){
		pw.close();
	}
	
}
