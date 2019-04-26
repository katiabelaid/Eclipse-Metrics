package net.sourceforge.metrics.calculators;

import net.sourceforge.metrics.core.Constants;
import net.sourceforge.metrics.core.Metric;
import net.sourceforge.metrics.core.sources.AbstractMetricSource;

public class WellCommentednessForMethod extends Calculator implements Constants {
	
	/**
	 * Constructor 
	 */
	
	public WellCommentednessForMethod() {
		super(WELL_COMMENTEDNESS_METHOD);
	}

	@Override
	public void calculate(AbstractMetricSource source) throws InvalidSourceException {
		if (source.getLevel() != METHOD) {
			throw new InvalidSourceException("WCM only applicable to methods");
		}

		double McCabeValue = source.getValue("VG").doubleValue();
		double CommentDensityValue = source.getValue("MCD").doubleValue();
		source.setValue(new Metric(WELL_COMMENTEDNESS_METHOD, CommentDensityValue/McCabeValue ));
	}


}
