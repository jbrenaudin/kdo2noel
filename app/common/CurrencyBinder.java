package common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import play.data.binding.TypeBinder;

public class CurrencyBinder implements TypeBinder<String> {

	@Override
	public Object bind(String name, Annotation[] anns, String value,
			Class klass, Type type) throws Exception {
		Float valeur = null;
		if (value != null && !value.isEmpty()) {
			value = value.replaceAll(",", ".");
			valeur = Float.parseFloat(value);
		}
		return valeur;
	}
}