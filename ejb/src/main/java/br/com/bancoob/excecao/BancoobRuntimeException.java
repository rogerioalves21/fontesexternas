package br.com.bancoob.excecao;

/**
 * Excecao em tempo real do bancoob.
 * 
 * @author Rogerio Alves Rodrigues
 */
public class BancoobRuntimeException extends RuntimeException {
	
	/**
	 * Construtor.
	 * 
	 * @param excecao Erro a ser tratado.
	 */
	public BancoobRuntimeException(Exception excecao) {
		super(excecao);
	}
	
}