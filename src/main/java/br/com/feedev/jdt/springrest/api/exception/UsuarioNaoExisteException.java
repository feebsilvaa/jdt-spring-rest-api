package br.com.feedev.jdt.springrest.api.exception;

public class UsuarioNaoExisteException extends Exception {

	private static final long serialVersionUID = 233008718190430610L;
	
	public UsuarioNaoExisteException(String message) {
		super(message);
	}

}
