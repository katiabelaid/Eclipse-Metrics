package net.sourceforge.metrics.internal.analysisFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalysisFileExport {
	
	public AnalysisFileExport() {
	}
	
	public static void doExport(FileWriter file, File CSVfile) throws IOException {
		ArrayList<Double> CD_analysisdata = calculate_boxplot( CSVfile, 2);
		ArrayList<Double> VG_analysisdata = calculate_boxplot( CSVfile, 3);
		file.append("< Comment density analysis data for methods> \n");
		file.append("Q1 = " + CD_analysisdata.get(0)+"\n");
		file.append("Q2 = " + CD_analysisdata.get(1)+"\n");
		file.append("Q3 = " + CD_analysisdata.get(2)+"\n");
		file.append("lower limit = " + CD_analysisdata.get(3)+"\n");
		file.append("upper limit = " + CD_analysisdata.get(4)+"\n");
		file.append("Low commented methods :\n");
		getLowCommentedMethods(CSVfile, file ,CD_analysisdata.get(3) );
		file.append("\n\n");
		
		file.append("< Cyclomatic complexity analysis data for methods > \n");
		file.append("Q1 = " + VG_analysisdata.get(0)+"\n");
		file.append("Q2 = " + VG_analysisdata.get(1)+"\n");
		file.append("Q3 = " + VG_analysisdata.get(2)+"\n");
		file.append("lower limit = " + VG_analysisdata.get(3)+"\n");
		file.append("upper limit = " + VG_analysisdata.get(4)+"\n");
		file.append("High complexity methods :\n");
		getHighComplexityMethods (CSVfile, file , VG_analysisdata.get(4));
	}
	public static ArrayList<Double> calculate_boxplot(final File CSVfile, int row) throws IOException {
		
		ArrayList<Double> values = new ArrayList<Double>();  
		
		List<String> lines = Files.readAllLines((CSVfile).toPath(), StandardCharsets.UTF_8); 
		
		for (int i=1; i<lines.size();i++) { 
		   String[] array = lines.get(i).split(";"); 
		   values.add(Double.parseDouble(array[row]));
		}
		
		QuickSort.quickSort(values,0,values.size()-1);
		int index_Q1 = (int) Math.ceil(values.size() /4);
		int index_Q2 = (int) Math.ceil(values.size()/2);
		int index_Q3 = (int) Math.ceil(values.size()*3/4);
		double Q1 =values.get(index_Q1);
		double Q2 = values.get(index_Q2);
		double Q3 =values.get(index_Q3);
		double intervalle = Q3-Q1;
		double inf = Math.max(values.get(0),Q1-intervalle*1.5);
		double sup = Math.min(values.get(values.size()-1),Q3+intervalle*1.5);
		
		return new ArrayList<Double>(Arrays.asList(Q1,Q2,Q3,inf,sup));
	}
	public static void getLowCommentedMethods(final File CSVfile, FileWriter writer , double inf) throws IOException {
		List<String> lines = Files.readAllLines((CSVfile).toPath(), StandardCharsets.UTF_8);
		
		for (int i=1; i<lines.size();i++) { 
			   
			   String[] line = lines.get(i).split(";"); 
			   if ((Float.parseFloat(line[2])<inf) ) {
				   writer.append(lines.get(i) + "\n");
			   }
		}
	}
	public static void getHighComplexityMethods (final File CSVfile, FileWriter writer , double sup) throws IOException {
		List<String> lines = Files.readAllLines((CSVfile).toPath(), StandardCharsets.UTF_8);
		
		for (int i=1; i<lines.size();i++) { 
			   
			   String[] line = lines.get(i).split(";"); 
			   if ((Float.parseFloat(line[3])>sup) ) {
				   writer.append(lines.get(i) + "\n");
			   }
		}
	}
}
