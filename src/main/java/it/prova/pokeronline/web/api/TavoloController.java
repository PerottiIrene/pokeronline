package it.prova.pokeronline.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.service.TavoloService;

@RestController
@RequestMapping("/api/tavolo")
public class TavoloController {
	
	@Autowired
	private TavoloService tavoloService;
	
	

}
