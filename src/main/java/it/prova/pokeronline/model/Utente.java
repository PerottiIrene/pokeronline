package it.prova.pokeronline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "utente")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;
	@Column(name = "email")
	private String email;
	@Column(name = "dateCreated")
	private Date dateCreated;
	@Column(name = "esperienzaAccumulata")
	private Integer esperienzaAccumulata;
	@Column(name = "creditoAccumulato")
	private Integer creditoAccumulato;

	// se non uso questa annotation viene gestito come un intero
	@Enumerated(EnumType.STRING)
	private StatoUtente stato;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utenteCreazione")
	private Set<Tavolo> tavoliCreati = new HashSet<Tavolo>(0);
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tavolo_id")
	private Tavolo tavoloDiGioco;
	
	@ManyToMany
	@JoinTable(name = "utente_ruolo", joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "ID"))
	private Set<Ruolo> ruoli = new HashSet<>(0);
	

	public Utente() {
	}

	public Utente(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Utente(String username, String password, String nome, String cognome, Date dateCreated) {
		this(username, password);
		this.nome = nome;
		this.cognome = cognome;
		this.dateCreated = dateCreated;
	}

	public Utente(Long id, String username, String password, String nome, String cognome, String email,
			Date dateCreated, StatoUtente stato) {
		this(username, password, nome, cognome, dateCreated);
		this.id = id;
		this.email = email;
		this.stato = stato;
	}
	
	public Utente(Long id, String username, String password, String nome, String cognome, String email,
			Date dateCreated, Integer esperienzaAccumulata, Integer creditoAccumulato, StatoUtente stato,
			Set<Tavolo> tavoliCreati, Tavolo tavoloDiGioco, Set<Ruolo> ruoli) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.dateCreated = dateCreated;
		this.esperienzaAccumulata = esperienzaAccumulata;
		this.creditoAccumulato = creditoAccumulato;
		this.stato = stato;
		this.tavoliCreati = tavoliCreati;
		this.tavoloDiGioco = tavoloDiGioco;
		this.ruoli = ruoli;
	}
	
	public Utente(Long id, String username, String password,String nome, StatoUtente stato,String cognome,String email, Date dateCreated, Integer esperienzaAccumulata,
			Integer creditoAccumulato) {
		super();
		this.id = id;
		this.username = username;
		this.password=password;
		this.nome = nome;
		this.cognome = cognome;
		this.email=email;
		this.dateCreated = dateCreated;
		this.stato = stato;
		this.esperienzaAccumulata = esperienzaAccumulata;
		this.creditoAccumulato = creditoAccumulato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}
	
	public Integer getEsperienzaAccumulata() {
		return esperienzaAccumulata;
	}

	public void setEsperienzaAccumulata(Integer esperienzaAccumulata) {
		this.esperienzaAccumulata = esperienzaAccumulata;
	}

	public Integer getCreditoAccumulato() {
		return creditoAccumulato;
	}

	public void setCreditoAccumulato(Integer creditoAccumulato) {
		this.creditoAccumulato = creditoAccumulato;
	}

	public Set<Tavolo> getTavoliCreati() {
		return tavoliCreati;
	}

	public void setTavoliCreati(Set<Tavolo> tavoliCreati) {
		this.tavoliCreati = tavoliCreati;
	}

	public Tavolo getTavoloDiGioco() {
		return tavoloDiGioco;
	}

	public void setTavoloDiGioco(Tavolo tavoloDiGioco) {
		this.tavoloDiGioco = tavoloDiGioco;
	}

	public boolean isAdmin() {
		for (Ruolo ruoloItem : ruoli) {
			if (ruoloItem.getCodice().equals(Ruolo.ROLE_ADMIN))
				return true;
		}
		return false;
	}
	
	public boolean isPlayer() {
		for (Ruolo ruoloItem : ruoli) {
			if (ruoloItem.getCodice().equals(Ruolo.ROLE_PLAYER))
				return true;
		}
		return false;
	}
	
	public boolean isSpecialPlayer() {
		for (Ruolo ruoloItem : ruoli) {
			if (ruoloItem.getCodice().equals(Ruolo.ROLE_SPECIAL_PLAYER))
				return true;
		}
		return false;
	}

	public boolean isAttivo() {
		return this.stato != null && this.stato.equals(StatoUtente.ATTIVO);
	}

	public boolean isDisabilitato() {
		return this.stato != null && this.stato.equals(StatoUtente.DISABILITATO);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
