package it.prova.pokeronline.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.utente.UtenteRepository;
import it.prova.pokeronline.web.api.exception.OperazioneNegataException;

@Service
@Transactional(readOnly = true)
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Utente> listAllUtenti() {
		
		Utente utenteInSessione=utenteInSessione();
		if(!utenteInSessione.isAdmin()) {
			throw new OperazioneNegataException("accesso autorizzato solamente ad utenti admin");
		}
		return (List<Utente>) repository.findAll();
	}

	public Utente caricaSingoloUtente(Long id) {
		
		Utente utenteInSessione=utenteInSessione();
		if(!utenteInSessione.isAdmin()) {
			throw new OperazioneNegataException("accesso autorizzato solamente ad utenti admin");
		}
		return repository.findById(id).orElse(null);
	}

	public Utente caricaSingoloUtenteConRuoli(Long id) {
		
		Utente utenteInSessione=utenteInSessione();
		if(!utenteInSessione.isAdmin()) {
			throw new OperazioneNegataException("accesso autorizzato solamente ad utenti admin");
		}
		return repository.findByIdConRuoli(id).orElse(null);
	}

	@Transactional
	public Utente aggiorna(Utente utenteInstance) {
		
		Utente utenteInSessione=utenteInSessione();
		if(!utenteInSessione.isAdmin()) {
			throw new OperazioneNegataException("accesso autorizzato solamente ad utenti admin");
		}
		// deve aggiornare solo nome, cognome, username, ruoli
		Utente utenteReloaded = repository.findById(utenteInstance.getId()).orElse(null);
		if (utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		utenteReloaded.setNome(utenteInstance.getNome());
		utenteReloaded.setCognome(utenteInstance.getCognome());
		utenteReloaded.setUsername(utenteInstance.getUsername());
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		return repository.save(utenteReloaded);
	}

	@Transactional
	public Utente inserisciNuovo(Utente utenteInstance) {
		
		Utente utenteInSessione=utenteInSessione();
		if(!utenteInSessione.isAdmin()) {
			throw new OperazioneNegataException("accesso autorizzato solamente ad utenti admin");
		}
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteInstance.setDateCreated(new Date());
		return repository.save(utenteInstance);
	}

	@Transactional
	public void rimuovi(Long idToRemove) {
		
		Utente utenteInSessione=utenteInSessione();
		if(!utenteInSessione.isAdmin()) {
			throw new OperazioneNegataException("accesso autorizzato solamente ad utenti admin");
		}
		repository.deleteById(idToRemove);
	}

	public List<Utente> findByExample(Utente example) {
		// TODO Da implementare
		return listAllUtenti();
	}

	public Utente eseguiAccesso(String username, String password) {
		return repository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
	}

	public Utente findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Transactional
	public void changeUserAbilitation(Long utenteInstanceId) {
		
		Utente utenteInSessione=utenteInSessione();
		if(!utenteInSessione.isAdmin()) {
			throw new OperazioneNegataException("accesso autorizzato solamente ad utenti admin");
		}
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	public Utente findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}

	@Override
	public Utente utenteInSessione() {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		return repository.findByUsername(username).orElse(null);
	}

	@Override
	public boolean isThisRole(String ruoloUtente) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(roleItem -> roleItem.getAuthority().equals(ruoloUtente));
	}

	@Override
	public Integer compraCredito(Integer credito) {
		
		Utente utenteinSessione=utenteInSessione();
		if(utenteinSessione.getCreditoAccumulato() == null)
			utenteinSessione.setCreditoAccumulato(credito);
		else
			utenteinSessione.setCreditoAccumulato(utenteinSessione.getCreditoAccumulato() + credito);
		repository.save(utenteinSessione);
		return utenteinSessione.getCreditoAccumulato();
	}

}
