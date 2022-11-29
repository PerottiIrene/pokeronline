package it.prova.pokeronline;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;

	@Autowired
	private UtenteService utenteServiceInstance;

	@Autowired
	private TavoloService tavoloService;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", Ruolo.ROLE_ADMIN));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", Ruolo.ROLE_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic Player", Ruolo.ROLE_PLAYER));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_SPECIAL_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Special Player", Ruolo.ROLE_SPECIAL_PLAYER));
		}

		// a differenza degli altri progetti cerco solo per username perche' se vado
		// anche per password ogni volta ne inserisce uno nuovo, inoltre l'encode della
		// password non lo
		// faccio qui perche gia lo fa il service di utente, durante inserisciNuovo
		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Mario", "Rossi", new Date());
			admin.setEmail("a.admin@prova.it");
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
			admin.setCreditoAccumulato(0);
			admin.setEsperienzaAccumulata(0);

			utenteServiceInstance.inserisciNuovo(admin);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getId());

			if (tavoloService.findByDenominazione("tavoloAdmin") == null) {
				Tavolo tavoloAdmin = new Tavolo(50, 100, "tavoloAdmin", new Date());
				tavoloAdmin.setUtenteCreazione(admin);
				tavoloService.inserisciNuovo(tavoloAdmin);
			}
		}

		if (utenteServiceInstance.findByUsername("classicPlayer") == null) {
			Utente classicPlayer = new Utente("classicPlayer", "classicPlayer", "Antonio", "Verdi", new Date());
			classicPlayer.setEmail("u.user@prova.it");
			classicPlayer.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic player", Ruolo.ROLE_PLAYER));
			classicPlayer.setCreditoAccumulato(0);
			classicPlayer.setEsperienzaAccumulata(0);
			utenteServiceInstance.inserisciNuovo(classicPlayer);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(classicPlayer.getId());

			if (tavoloService.findByDenominazione("tavoloClassicPlayer") == null) {
				Tavolo tavoloClassicPlayer = new Tavolo(0, 0, "tavoloClassicPlayer", new Date());
				tavoloClassicPlayer.setUtenteCreazione(classicPlayer);
				tavoloService.inserisciNuovo(tavoloClassicPlayer);
			}
		}

		if (utenteServiceInstance.findByUsername("specialPlayer") == null) {
			Utente specialPlayer = new Utente("specialPlayer", "specialPlayer", "Antonioo", "Verdii", new Date());
			specialPlayer.setEmail("u.user1@prova.it");
			specialPlayer.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Special player", Ruolo.ROLE_SPECIAL_PLAYER));
			specialPlayer.setCreditoAccumulato(0);
			specialPlayer.setEsperienzaAccumulata(0);
			utenteServiceInstance.inserisciNuovo(specialPlayer);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(specialPlayer.getId());

			if (tavoloService.findByDenominazione("tavoloSpecialPlayer") == null) {
				Tavolo tavoloSpecialPlayer = new Tavolo(0, 0, "tavoloSpecialPlayer", new Date());
				tavoloSpecialPlayer.setUtenteCreazione(specialPlayer);
				tavoloService.inserisciNuovo(tavoloSpecialPlayer);
			}
		}

	}

}
