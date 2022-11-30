package it.prova.pokeronline.web.api.exception;

public class NonPuoiGiocareInNessunTavoloException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public NonPuoiGiocareInNessunTavoloException(String message) {
		super(message);
	}

}
