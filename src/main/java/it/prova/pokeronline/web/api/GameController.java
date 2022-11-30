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
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.NonGiochiInNessunTavoloException;
import it.prova.pokeronline.web.api.exception.NonPuoiGiocareInNessunTavoloException;

@RestController
@RequestMapping("/api/game")
public class GameController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private TavoloService tavoloService;

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

}
