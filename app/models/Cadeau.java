package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.data.validation.Required;
import play.data.validation.Validation;
import play.db.jpa.Model;

@Entity(name = "cadeau")
public class Cadeau extends Model {

	private static final long serialVersionUID = 1L;

	public Cadeau(String libelle) {
		Validation.required("libelle", libelle);
		this.libelle = libelle;
	}

	@Required
	@Column(nullable = false, name = "libelle")
	public String libelle;
}
