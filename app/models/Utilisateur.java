package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import play.data.validation.Email;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity(name = "utilisateur")
public class Utilisateur extends Model {

	private static final long serialVersionUID = 1L;

	public static boolean existe(String email, String motDePasse) {
		return Utilisateur.count("byEmailAndMotDePasse", email.toLowerCase(), motDePasse) == 1;
	}

	public static Utilisateur recupereParSurnom(String surnom) {
		return Utilisateur.find("bySurnom", surnom).first();
	}

	public static Utilisateur recupereParEmail(String email) {
		return Utilisateur.find("byEmail", email.toLowerCase()).first();
	}

	public List<Utilisateur> chercheAmisPossibles(String surnom) {
		return Utilisateur
				.find("FROM utilisateur u "
						+ "WHERE LOWER(u.surnom) LIKE LOWER(:surnom) "
						+ "AND u != :utilisateurConnecte "
						+ "AND u NOT IN (SELECT i.emetteur FROM invitation i WHERE i.destinataire = :utilisateurConnecte) "
						+ "AND u NOT IN (SELECT i.destinataire FROM invitation i WHERE i.emetteur = :utilisateurConnecte) "
						+ "AND u NOT IN (SELECT ami FROM utilisateur u2 INNER JOIN u2.amis ami WHERE u2 = :utilisateurConnecte) ")
				.bind("surnom", "%" + surnom + "%")
				.bind("utilisateurConnecte", this).fetch(10);
	}

	public Cadeau ajouteCadeau(String libelle) {
		Cadeau cadeau = new Cadeau(libelle);
		cadeaux.add(cadeau);
		return cadeau;
	}

	public Cadeau supprimeCadeau(long id) {
		Cadeau cadeau = recupereCadeauParId(id);
		cadeaux.remove(cadeau);
		return cadeau;
	}

	public Cadeau recupereCadeauParId(long id) {
		for (Cadeau cadeau : cadeaux) {
			if (cadeau.id == id) {
				return cadeau;
			}
		}
		return null;
	}

	public void changePositionCadeau(Cadeau cadeau, int position) {
		if (cadeaux.remove(cadeau)) {
			cadeaux.add(position - 1, cadeau);
		}
	}

	public Utilisateur ajouteAmi(Utilisateur ami) {
		amis.add(ami);
		return this;
	}

	public Utilisateur supprimeAmi(Utilisateur ami) {
		amis.remove(ami);
		return this;
	}

	public Utilisateur recupereAmiParId(long id) {
		for (Utilisateur ami : amis) {
			if (ami.id == id) {
				return ami;
			}
		}
		return null;
	}

	public void changePositionAmi(Utilisateur ami, int position) {
		if (amis.remove(ami)) {
			amis.add(position - 1, ami);
		}
	}

	public List<Invitation> getInvitationsEnvoyees() {
		return Invitation.envoyeesPar(this);
	}

	public List<Invitation> getInvitationsRecues() {
		return Invitation.recuesPar(this);
	}

	public long getNombreDInvitationsRecues() {
		return Invitation.nombreRecuesPar(this);
	}

	public List<Utilisateur> getAmisOublies() {
		List<Utilisateur> amisOublies = new LinkedList<Utilisateur>(amis);
		for (Achat achat : achats) {
			amisOublies.remove(achat.ami);
		}
		return amisOublies;
	}

	public List<Achat> getMesAchats() {
		return achats;
	}

	public void acheteCadeau(Utilisateur ami, Cadeau cadeau) {
		achats.add(new Achat(ami, cadeau));
	}

	public float getMontantTotalDesAchats() {
		float montantTotal = 0;
		for (Achat achat : achats) {
			montantTotal += (achat.montant != null ? achat.montant : 0);
		}
		return montantTotal;
	}

	public void setEmail(String email) {
		if (email != null) {
			this.email = email.toLowerCase();
		}
	}

	public Utilisateur() {
		this(null, null, null);
	}
	
	public Utilisateur(String surnom, String email, String motDePasse) {
		this.surnom = surnom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.cadeaux = new LinkedList<Cadeau>();
		this.amis = new LinkedList<Utilisateur>();
		this.achats = new LinkedList<Achat>();
	}

	@Required
	@Unique(message = "Ce surnom est déjà utilisé")
	@Column(unique = true, nullable = false, name = "surnom")
	public String surnom;

	@Required
	@Unique(message = "Cet email est déjà utilisé")
	@Email
	@Column(unique = true, nullable = false, name = "email")
	public String email;

	@Required
	@Column(nullable = false, name = "mot_de_passe")
	public String motDePasse;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "utilisateur_id", referencedColumnName = "id", nullable = false)
	@OrderColumn(name = "position")
	public List<Cadeau> cadeaux;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ami", inverseJoinColumns = @JoinColumn(name = "ami_id", referencedColumnName = "id"))
	@OrderColumn(name = "position")
	public List<Utilisateur> amis;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "utilisateur_id", referencedColumnName = "id", nullable = false)
	@OrderColumn(name = "position")
	private List<Achat> achats;
}
