package it.prova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;

@Service
@Transactional(readOnly = true)
public class TavoloServiceImpl implements TavoloService{
	
	@Autowired
	private TavoloRepository tavoloRepository;


	@Override
	public List<Tavolo> listAllElements(boolean eager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tavolo caricaSingoloElemento(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tavolo caricaSingoloElementoEager(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		return tavoloRepository.save(tavoloInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Tavolo> findByDenominazione(String denominazione) {
		return (List<Tavolo>) tavoloRepository.findByDenominazione(denominazione);
	}

	@Override
	public List<Tavolo> listAllElementsEager() {
		// TODO Auto-generated method stub
		return null;
	}

}
