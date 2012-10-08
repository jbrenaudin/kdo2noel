package models;

import javax.persistence.Entity;

import common.CurrencyBinder;

import play.data.binding.As;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity(name = "achat")
public class Achat extends Model {

	private static final long serialVersionUID = 1L;

	@Required
	public String pour;

	@Required
	public String cadeau;

	@As(binder=CurrencyBinder.class)
	public Float montant;
}
