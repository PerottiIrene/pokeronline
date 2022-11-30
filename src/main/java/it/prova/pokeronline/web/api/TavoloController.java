package it.prova.pokeronline.web.api;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.IdNotNullForInsertException;
import it.prova.pokeronline.web.api.exception.OperazioneNegataException;
import it.prova.pokeronline.web.api.exception.TavoloConGiocatoriException;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;

@RestController
@RequestMapping("/api/tavolo")
public class TavoloController {

	@Autowired
	private TavoloService tavoloService;

	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public List<TavoloDTO> getAll() {
		try {
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findAllByRole(), false);
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping("/{id}")
	public TavoloDTO getById(@PathVariable(value = "id", required = true) long id) {
		try {
			return TavoloDTO.buildTavoloDTOFromModel(tavoloService.findByIdSpecialPlayer(id), false);
		} catch (Exception e) {
			throw e;
		}
	}

	// gli errori di validazione vengono mostrati con 400 Bad Request ma
	// elencandoli grazie al ControllerAdvice
	@PostMapping
	public TavoloDTO createNew(@Valid @RequestBody TavoloDTO tavoloInput) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		if (tavoloInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(roleItem -> roleItem.getAuthority().equals(Ruolo.ROLE_PLAYER))) {
			throw new OperazioneNegataException("operazione negata");
		}

		tavoloInput.setUtenteCreazione(UtenteDTO.buildUtenteDTOFromModel(utenteService.utenteInSessione()));
		tavoloInput.setDataCreazione(new Date());

		Tavolo tavoloInserito = tavoloService.inserisciNuovo(tavoloInput.buildTavoloModel());
		return TavoloDTO.buildTavoloDTOFromModel(tavoloInserito, true);
	}

	@PutMapping("/{id}")
	public TavoloDTO update(@Valid @RequestBody TavoloDTO tavoloInput, @PathVariable(required = true) Long id) {
		Tavolo tavolo = tavoloService.caricaSingoloElementoEager(id);

		if (tavolo == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);

		if (tavolo.getUtentiGiocatori().size() > 0) {
			throw new TavoloConGiocatoriException("Questo tavolo non puo essere aggiornato, ci sono ancora giocatori!");
		}

		if (utenteService.utenteInSessione().isPlayer())
			throw new OperazioneNegataException("non puoi aggiornare il tavolo");

		tavoloInput.setId(id);
		Tavolo tavoloAggiornato = tavoloService.aggiorna(tavoloInput.buildTavoloModel());
		return TavoloDTO.buildTavoloDTOFromModel(tavoloAggiornato, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) throws OperazioneNegataException {
		Tavolo tavolo = tavoloService.caricaSingoloElemento(id);
		if (tavolo.getUtentiGiocatori().size() > 0) {
			throw new TavoloConGiocatoriException("Questo tavolo non puo essere eliminato, ci sono ancora giocatori!");
		}
		tavoloService.rimuovi(id);
	}

}
