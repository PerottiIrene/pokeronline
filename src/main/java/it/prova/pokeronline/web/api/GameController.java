package it.prova.pokeronline.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.service.UtenteService;

@RestController
@RequestMapping("/api/game")
public class GameController {
	
	@Autowired
	private UtenteService utenteService;
	
	@PostMapping("/compraCredito/{credito}")
	public Integer compraCredito(@PathVariable(value = "credito", required = true) @RequestBody Integer credito) {
		
		return utenteService.compraCredito(credito);
	}

}
