package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;


public interface TavoloService {
	
	List<Tavolo> listAllElements(boolean eager);

	Tavolo caricaSingoloElemento(Long id);

	Tavolo caricaSingoloElementoEager(Long id);

	Tavolo aggiorna(Tavolo tavoloInstance);

	Tavolo inserisciNuovo(Tavolo tavoloInstance);

	void rimuovi(Long idToRemove);
	
	List<Tavolo> findByDenominazione(String denominazione);
	
	List<Tavolo> listAllElementsEager();
	
	List<Tavolo> findAllByRole();
	
	Tavolo findByIdSpecialPlayer( Long idTavolo);
	
	Tavolo lastGame(Long idU);
	
	List<Tavolo> ricercaTavoli();
	
	public Tavolo giocaPartita(Long idTavolo);

}
