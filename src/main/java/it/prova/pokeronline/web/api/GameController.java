package it.prova.pokeronline.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.CreditoInsufficienteException;
import it.prova.pokeronline.web.api.exception.EsperienzaAccumulataInferioreException;
import it.prova.pokeronline.web.api.exception.NonGiochiInNessunTavoloException;
import it.prova.pokeronline.web.api.exception.NonPuoiGiocareInNessunTavoloException;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;

@RestController
@RequestMapping("/api/game")
public class GameController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private TavoloService tavoloService;
	
	@Autowired
	private TavoloRepository tavoloRepository;

	@PostMapping("/compraCredito/{credito}")
	public Integer compraCredito(@PathVariable(value = "credito", required = true) @RequestBody Integer credito) {

		return utenteService.compraCredito(credito);
	}

	@GetMapping("/lastGame")
	public TavoloDTO lastGame() throws NonGiochiInNessunTavoloException {

		try {
			Utente utenteInSessione = utenteService.utenteInSessione();
			return TavoloDTO.buildTavoloDTOFromModel(tavoloService.lastGame(utenteInSessione.getId()), false);
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping("/ricercaTavoli")
	public List<TavoloDTO> ricercaTavoli() throws NonPuoiGiocareInNessunTavoloException {

		try {
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.ricercaTavoli(), false);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@GetMapping("/gioca/{id}")
	public TavoloDTO gioca(@PathVariable(value = "id", required = true) @RequestBody Long id) throws Exception{
		
		Utente utenteInSessione = utenteService.utenteInSessione();
		Tavolo tavoloPerPartita = tavoloRepository.findById(id).orElse(null);
		
		if (tavoloPerPartita.getEsperienzaMinima() <= utenteInSessione.getEsperienzaAccumulata())
			throw new EsperienzaAccumulataInferioreException(
					"non puoi giocare in questo tavolo, la tua esperienza e' inferiore a quella richiesta");
		
		if (utenteInSessione.getCreditoAccumulato() < 0 || utenteInSessione.getCreditoAccumulato() == 0) {
			throw new CreditoInsufficienteException("il tuo credito e' esaurito, non puoi continuare a giocare");
		}
		
			return TavoloDTO.buildTavoloDTOFromModel(tavoloService.giocaPartita(id),true);
	}
	
	@GetMapping("/abbandonaPartita/{id}")
	public TavoloDTO abbandonaPartita(@PathVariable(value = "id", required = true) @RequestBody Long id) throws Exception{
		
		Utente utenteInSessione = utenteService.utenteInSessione();
		Tavolo tavoloPerPartita = tavoloRepository.findById(id).orElse(null);
		
		if(tavoloPerPartita == null) {
			throw new TavoloNotFoundException("tavolo non trovato");
		}
		
		for(Utente utenteItem: tavoloPerPartita.getUtentiGiocatori()) {
			if(utenteItem.getId() == utenteInSessione.getId()) {
			    tavoloPerPartita.getUtentiGiocatori().remove(utenteInSessione);
			}
		}
		
		utenteInSessione.setEsperienzaAccumulata(utenteInSessione.getEsperienzaAccumulata() +2);
		utenteService.aggiorna(utenteInSessione);
		
		
		return TavoloDTO.buildTavoloDTOFromModel(tavoloService.caricaSingoloElemento(id), false);
	}
	
}


