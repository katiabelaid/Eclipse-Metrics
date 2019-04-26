package net.sourceforge.metrics.calculators;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sourceforge.metrics.core.Constants;
import net.sourceforge.metrics.core.Metric;
import net.sourceforge.metrics.core.sources.AbstractMetricSource;

public class ClassCommentDensity extends Calculator implements Constants{
	/**
	 * Constructor 
	 */
	
	public ClassCommentDensity() {
		super(CLASS_COMMENT_DENSITY);
	}
	
	@Override
	public void calculate(AbstractMetricSource source) throws InvalidSourceException {
		if (source.getLevel() != TYPE) {
			throw new InvalidSourceException("Class Comment Density only applicable to classes");
		}
		
			double Value = class_comment_density ( source.getPath());
			source.setValue(new Metric(CLASS_COMMENT_DENSITY, Value ));
		
	}
	
	public static double class_comment_density(String class_path) {	
		double TLOC = 0, CLOC = 0; 
		try {
			InputStream flux=new FileInputStream(class_path); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			while ((ligne=buff.readLine())!=null){
				if ((ligne.equals("") == false )&& (ligne.length()>2)) TLOC +=1;
				if (ligne.contains("//")) {
					if (ligne.contains("System.out.print") == false) CLOC+=1;
					else {
						if ((ligne.indexOf("//") < ligne.indexOf("(")) || (ligne.indexOf("//") > ligne.indexOf(")")) ) CLOC+=1;
					}
				}
				else if ((ligne.contains("/*")&& !(ligne.contains("*/")))) {
					
					if (ligne.contains("System.out.print") == false) {
						CLOC+=1;
						while (((ligne=buff.readLine())!=null) && (ligne.contains("*/") == false) ) {CLOC +=1;TLOC+=1;}
					}
					else {
						if ((ligne.indexOf("/*") < ligne.indexOf("(")) || (ligne.indexOf("/*") > ligne.indexOf(")")) ) {
							CLOC+=1;
					        while (((ligne=buff.readLine())!=null) && (ligne.contains("*/") == false) ) {CLOC +=1;TLOC+=1;}
						}
					}
				}
				else if (ligne.contains("/*") && ligne.contains("*/")) {
						if (ligne.contains("System.out.print") == false) CLOC+=1;
						else {
							if (((ligne.indexOf("/*") < ligne.indexOf("/*")) || (ligne.indexOf("/*") > ligne.indexOf("/*"))) && ((ligne.indexOf("*/") < ligne.indexOf("*/")) || (ligne.indexOf("*/") > ligne.indexOf("*/"))) ) CLOC+=1;
					}
				}
			}
			buff.close(); 
			}		
			catch (Exception e){
			System.out.println(e.toString());
			}

		return (CLOC/TLOC);
	}
}
