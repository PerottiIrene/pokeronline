package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;


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
	
//	@JsonIgnoreProperties(value = { "tavolo" })
	private Set<UtenteDTO> utentiGiocatori = new HashSet<UtenteDTO>(0);
	
//	@JsonIgnoreProperties(value = { "tavoli" })
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
	
	public TavoloDTO(Long id, Integer esperienzaMinima, Integer cifraMinima, String denominazione, Date dataCreazione) {
		super();
		this.id = id;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
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

	public Tavolo buildTavoloModel() {
		Tavolo result= new Tavolo(this.id,  this.cifraMinima, this.esperienzaMinima,this.denominazione,this.dataCreazione);
		
		if(this.utentiGiocatori.size() > 1) {
			Set<Utente> set = result.getUtentiGiocatori();
			this.utentiGiocatori.forEach(utente -> set.add(utente.buildUtenteModel(false)));
		}
		
		if(this.utenteCreazione != null)
			result.setUtenteCreazione(this.utenteCreazione.buildUtenteModel(false));
			
		return result;
	}

	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavoloModel, boolean includeUtentiGiocatori) {
		TavoloDTO result = new TavoloDTO(tavoloModel.getId(),tavoloModel.getEsperienzaMinima(), tavoloModel.getCifraMinima(),tavoloModel.getDenominazione(), 
				tavoloModel.getDataCreazione());
		if(includeUtentiGiocatori)
			result.setUtentiGiocatori(UtenteDTO.createUtenteDTOSetFromModelSet(tavoloModel.getUtentiGiocatori()));
		if (tavoloModel.getUtenteCreazione() != null && tavoloModel.getUtenteCreazione().getId() != null
				&& tavoloModel.getUtenteCreazione().getId() > 0) {
			result.setUtenteCreazione(UtenteDTO.buildUtenteDTOFromModel(tavoloModel.getUtenteCreazione()));
		}
		return result;
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput, boolean includeUtentiGiocatori) {
		return modelListInput.stream().map(tavoloEntity -> {
			TavoloDTO result = TavoloDTO.buildTavoloDTOFromModel(tavoloEntity,includeUtentiGiocatori);
			if(includeUtentiGiocatori)
				result.setUtentiGiocatori(UtenteDTO.createUtenteDTOSetFromModelSet(tavoloEntity.getUtentiGiocatori()));
			return result;
		}).collect(Collectors.toList());
	}

	
}
