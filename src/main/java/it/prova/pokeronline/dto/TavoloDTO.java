package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TavoloDTO {
	
	private Long id;
	
	@NotNull(message = "{esperienzaMinima.notnull}")
	private Integer esperienzaMinima;
	
	@NotNull(message = "{cifraMinima.notnull}")
	private Integer cifraMinima;
	
	@NotBlank(message = "{denominazione.notblank}")
	private String denominazione;
	
	@NotNull(message = "{dataCreazione.notnull}")
	private Date dataCreazione;
	
	@JsonIgnoreProperties(value = { "tavolo" })
	private Set<UtenteDTO> utentiGiocatori = new HashSet<UtenteDTO>(0);
	
	@JsonIgnoreProperties(value = { "tavoli" })
	@NotNull(message = "{utente.notnull}")
	private UtenteDTO utenteCreazione;

	public TavoloDTO(Long id, Integer esperienzaMinima, Integer cifraMinima,String denominazione, Date dataCreazione, Set<UtenteDTO> utentiGiocatori, UtenteDTO utenteCreazione) {
		super();
		this.id = id;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.utentiGiocatori = utentiGiocatori;
		this.utenteCreazione = utenteCreazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEsperienzaMinima() {
		return esperienzaMinima;
	}

	public void setEsperienzaMinima(Integer esperienzaMinima) {
		this.esperienzaMinima = esperienzaMinima;
	}

	public Integer getCifraMinima() {
		return cifraMinima;
	}

	public void setCifraMinima(Integer cifraMinima) {
		this.cifraMinima = cifraMinima;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Set<UtenteDTO> getUtentiGiocatori() {
		return utentiGiocatori;
	}

	public void setUtentiGiocatori(Set<UtenteDTO> utentiGiocatori) {
		this.utentiGiocatori = utentiGiocatori;
	}

	public UtenteDTO getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(UtenteDTO utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}
	
	

	
}
