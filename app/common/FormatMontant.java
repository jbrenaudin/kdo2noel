package common;

import java.text.DecimalFormat;
import play.templates.JavaExtensions;

public class FormatMontant extends JavaExtensions {

	public static String enEuros(Number montant) {
		return new DecimalFormat("#,##0.00' €'").format(montant);
	}
}
