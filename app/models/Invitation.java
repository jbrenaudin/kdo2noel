package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity(name = "invitation")
public class Invitation extends Model {

	private static final long serialVersionUID = 1L;

	public static List<Invitation> envoyeesPar(Utilisateur emetteur) {
		return Invitation.find("emetteur = ? order by dateCreation asc",
				emetteur).fetch();
	}

	public static List<Invitation> recuesPar(Utilisateur destinataire) {
		return Invitation.find("destinataire = ? order by dateCreation asc",
				destinataire).fetch();
	}

	public static long nombreRecuesPar(Utilisateur destinataire) {
		return Invitation.count("destinataire = ?", destinataire);
	}

	public static Invitation envoyeeParEtRecuePar(Utilisateur emetteur,
			Utilisateur destinataire) {
		return Invitation.find("emetteur = ? and destinataire = ?", emetteur,
				destinataire).first();
	}

	public Invitation(Utilisateur emetteur, Utilisateur destinataire) {
		this.emetteur = emetteur;
		this.destinataire = destinataire;
		this.dateCreation = new Date();
	}

	public Invitation accepte() {
		emetteur.ajouteAmi(destinataire);
		destinataire.ajouteAmi(emetteur);
		return this;
	}

	@Required
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public Utilisateur emetteur;

	@Required
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public Utilisateur destinataire;

	@Required
	public Date dateCreation;
}
