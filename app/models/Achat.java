package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity(name = "achat")
public class Achat extends Model {

	private static final long serialVersionUID = 1L;

	@Required
	public String pour;

	@Required
	public String cadeau;

	public Float montant;
}
