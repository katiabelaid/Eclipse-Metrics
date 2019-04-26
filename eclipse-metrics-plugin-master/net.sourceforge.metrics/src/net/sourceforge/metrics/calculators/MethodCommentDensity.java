package net.sourceforge.metrics.calculators;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sourceforge.metrics.core.Constants;
import net.sourceforge.metrics.core.Metric;
import net.sourceforge.metrics.core.sources.AbstractMetricSource;

public class MethodCommentDensity extends Calculator implements Constants {
	
	/**
	 * Constructor 
	 */
	
	public MethodCommentDensity() {
		super(METHOD_COMMENT_DENSITY);
	}
	
	@Override
	public void calculate(AbstractMetricSource source) throws InvalidSourceException {
		if (source.getLevel() != METHOD) {
			throw new InvalidSourceException("Method Comment Density only applicable to methods");
		}
			double Value = method_comment_density ( source.getPath(),source.getName());
			source.setValue(new Metric(METHOD_COMMENT_DENSITY, Value ));
	}
	public static double method_comment_density(String class_path, String nom_methode) {
 		double TLOC =0, CLOC = 0; 
 		int nb_ouverts = 0; 
 		int nb_fermes = 0; 
 	
		try{
			InputStream flux=new FileInputStream(class_path); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			
			while ((ligne=buff.readLine())!=null){
						
						if (ligne.contains(nom_methode) && ligne.contains("{")) {
							TLOC+=1;
							nb_ouverts +=1; 
							while (((ligne=buff.readLine())!=null) && (nb_ouverts != nb_fermes)){
								if ((ligne.equals("") == false )&& (ligne.length()>2)) TLOC +=1; 
								if (ligne.contains("//")) {
									if (ligne.contains("System.out.print") == false) CLOC+=1;
									else {
										if ((ligne.indexOf("//") < ligne.indexOf("(")) || (ligne.indexOf("//") > ligne.indexOf(")")) ) CLOC+=1;
									}
								}
								else if ((ligne.contains("/*") && !(ligne.contains("*/")))) {
									if (ligne.contains("System.out.print") == false) {
										CLOC+=1;
										while (((ligne=buff.readLine())!=null) && (ligne.contains("*/") == false) ) {CLOC +=1;TLOC +=1 ;}
									}
									else {
										if ((ligne.indexOf("/*") < ligne.indexOf("(")) || (ligne.indexOf("/*") > ligne.indexOf(")")) ) {
											CLOC+=1;
										       while (((ligne=buff.readLine())!=null) && (ligne.contains("*/") == false) ) {CLOC +=1; TLOC +=1;}
										}
									}
								}
								
								else if (ligne.contains("/*") && ligne.contains("*/")) {
										if (ligne.contains("System.out.print") == false) CLOC+=1;
										else {
											if (((ligne.indexOf("/*") < ligne.indexOf("/*")) || (ligne.indexOf("/*") > ligne.indexOf("/*"))) && ((ligne.indexOf("*/") < ligne.indexOf("*/")) || (ligne.indexOf("*/") > ligne.indexOf("*/"))) ) CLOC+=1;
										}
								}
								if (ligne.contains("{") ) nb_ouverts ++;
								if (ligne.contains("}")) nb_fermes ++;
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
