package net.sourceforge.metrics.calculators;

import net.sourceforge.metrics.core.Constants;
import net.sourceforge.metrics.core.Metric;
import net.sourceforge.metrics.core.sources.AbstractMetricSource;

public class WellCommentednessForClass extends Calculator implements Constants {
	
	/**
	 * Constructor 
	 */
	
	public WellCommentednessForClass() {
		super(WELL_COMMENTEDNESS_CLASS);
	}

	@Override
	public void calculate(AbstractMetricSource source) throws InvalidSourceException {
		if (source.getLevel() != TYPE) {
			throw new InvalidSourceException("WCC only applicable to classes");
		}
		
		double McCabeValue = source.getValue("VG").doubleValue();
		double CommentDensityValue = source.getValue("CCD").doubleValue();
		source.setValue(new Metric(WELL_COMMENTEDNESS_CLASS,CommentDensityValue/McCabeValue ));
	} 
}
