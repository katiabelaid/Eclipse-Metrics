package net.sourceforge.metrics.internal.csv;


import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import net.sourceforge.metrics.core.Constants;
import net.sourceforge.metrics.core.sources.AbstractMetricSource;

public class CSVexport implements Constants {
	
	Method[] methods =null ;
	
	public CSVexport() {
	}
	
	public static void MethodCSVExport(AbstractMetricSource source, FileWriter writer) {
		try {
			writer.append(source.getPath()+";"+ source.getName() +";"+ 
					source.getValue("MCD").doubleValue() +";"+ 
					source.getValue("VG").doubleValue());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	
	}
	
	public static void ClassCSVExport (AbstractMetricSource source, FileWriter writer) {
		List<AbstractMetricSource> children = source.getChildren();
		for (int i =0;i<children.size();i++) {
			MethodCSVExport(source,writer);
		}
	}
	
	public static void PackageCSVExport (AbstractMetricSource source, FileWriter writer) {
		List<AbstractMetricSource> children = source.getChildren();
		for (int i =0;i<children.size();i++) {
			ClassCSVExport(source,writer);
		}
	}
	
	public static void ProjectCSVExport (AbstractMetricSource source, FileWriter writer) {
		List<AbstractMetricSource> children = source.getChildren();
		for (int i =0;i<children.size();i++) {
			PackageCSVExport(source,writer);
		}
	}
	
	/* 
	 * Exports CSVFile from given source 
	 */
	public static void doExport (AbstractMetricSource source, FileWriter writer) {
		
		try {
			writer.append("path");
			writer.append(";");			
			writer.append("name");
		    writer.append(";");
			writer.append("comment density");
			writer.append(";");
			writer.append("VG");
			writer.append("\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (source.getLevel() == METHOD) {
			MethodCSVExport( source, writer);
		}
		else if (source.getLevel() == TYPE) {
			ClassCSVExport(source,writer);
		}
		else if (source.getLevel() == PACKAGEFRAGMENT ) {
			PackageCSVExport(source,writer); 
		}
		else if (source.getLevel() == PROJECT) {
			ProjectCSVExport(source,writer);
		}
	}
}
	
	
	
	
