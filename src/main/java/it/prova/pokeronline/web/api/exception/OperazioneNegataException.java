package it.prova.pokeronline.web.api.exception;

public class OperazioneNegataException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public OperazioneNegataException(String message) {
		super(message);
	}

}
