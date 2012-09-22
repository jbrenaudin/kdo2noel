package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity(name = "achat")
public class Achat extends Model {

	public Achat(Utilisateur ami, Cadeau cadeau) {
		this.ami = ami;
		this.cadeau = cadeau;
		this.montant = null;
	}
	
	@ManyToOne(optional=false)
	public Utilisateur ami;

	@ManyToOne(optional=false)
	public Cadeau cadeau;
	
	public Float montant;
}
