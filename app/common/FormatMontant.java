package common;

import java.text.DecimalFormat;
import java.util.Locale;

import play.templates.JavaExtensions;

public class FormatMontant extends JavaExtensions {

	public static String enEuros(Number montant) {
		DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(Locale.FRENCH);
		format.applyPattern("###,###.00' €'");
		return format.format(montant);
	}
}
