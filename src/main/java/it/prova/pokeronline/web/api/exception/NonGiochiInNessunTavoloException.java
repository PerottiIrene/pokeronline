package it.prova.pokeronline.web.api.exception;

public class NonGiochiInNessunTavoloException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public NonGiochiInNessunTavoloException(String message) {
		super(message);
	}

}
