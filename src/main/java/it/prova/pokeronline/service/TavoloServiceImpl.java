package it.prova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.web.api.exception.OperazioneNegataException;

@Service
@Transactional(readOnly = true)
public class TavoloServiceImpl implements TavoloService{
	
	@Autowired
	private TavoloRepository tavoloRepository;
	
	@Autowired 
	private UtenteService utenteService;


	@Override
	public List<Tavolo> listAllElements(boolean eager) {
		
		if(eager)
		return (List<Tavolo>) tavoloRepository.findAllEagerUtentiGiocatori();
		
		else
		return (List<Tavolo>) tavoloRepository.findAll();
	}

	@Override
	public Tavolo caricaSingoloElemento(Long id) {
		return tavoloRepository.findById(id).orElse(null);
	}

	@Override
	public Tavolo caricaSingoloElementoEager(Long id) {
		return tavoloRepository.findByIdEagerUtentiGiocatori(id);
	}

	@Override
	@Transactional
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		return tavoloRepository.save(tavoloInstance);
	}

	@Override
	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		return tavoloRepository.save(tavoloInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
		tavoloRepository.deleteById(idToRemove);
		
	}

	@Override
	public List<Tavolo> findByDenominazione(String denominazione) {
		return (List<Tavolo>) tavoloRepository.findByDenominazione(denominazione);
	}

	@Override
	public List<Tavolo> listAllElementsEager() {
		return tavoloRepository.findAllEagerUtentiGiocatori();
	}

	@Override
	public List<Tavolo> findAllByRole() {
		if(utenteService.isThisRole(Ruolo.ROLE_SPECIAL_PLAYER))
			return tavoloRepository.findAllSpecialPlayer(utenteService.utenteInSessione().getId());
		else if(utenteService.isThisRole(Ruolo.ROLE_ADMIN))
			return (List<Tavolo>) tavoloRepository.findAll();
		else
			throw new OperazioneNegataException("accesso negato");
	}

	@Override
	public Tavolo findByIdSpecialPlayer(Long idTavolo) {
		if(utenteService.isThisRole(Ruolo.ROLE_SPECIAL_PLAYER))
			return tavoloRepository.findByIdSpecialPlayer(utenteService.utenteInSessione().getId(),idTavolo).get(0);
		else if(utenteService.isThisRole(Ruolo.ROLE_ADMIN))
			return tavoloRepository.findById(idTavolo).orElse(null);
		else
			throw new OperazioneNegataException("accesso negato");
	}

}
