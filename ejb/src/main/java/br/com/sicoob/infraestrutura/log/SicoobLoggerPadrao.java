package br.com.sicoob.infraestrutura.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger padrao.
 * 
 * @author Rogerio Alves Rodrigues
 */
public class SicoobLoggerPadrao {
	
	private static Class clazze;
	private static final SicoobLoggerPadrao INSTANCE = new SicoobLoggerPadrao();
	
	/**
	 * Retorna a instancia singleton.
	 * 
	 * @param clazz Classe a ser logada.
	 */
	public static SicoobLoggerPadrao getInstance(Class clazz) {
	  clazze = clazz;
		return INSTANCE;
	}
	
	/**
	 * Escreve a mensagem em modo debug.
	 * 
	 * @param message Mensagem.
	 */
	public void debug(String message) {
		Logger.getLogger(clazze.getName()).log(Level.WARNING, message);
	}
	
	/**
	 * Escreve em modo de erro.
	 * 
	 * @param excecao Erro.
	 * @param message Mensagem.
	 */
	public void alerta(Exception excecao, String message) {
		Logger.getLogger(clazze.getName()).log(Level.SEVERE, message, excecao);
	}
	
	/**
	 * Escreve em modo de erro.
	 * 
	 * @param excecao Erro.
	 * @param message Mensagem.
	 */
	public void erro(Exception excecao, String message) {
    Logger.getLogger(clazze.getName()).log(Level.SEVERE, message, excecao);		
	}
	
	/**
	 * Escreve em modo de informacao.
	 * 
	 * @param excecao Erro.
	 * @param message Mensagem.
	 */
	public void info(String message) {
		Logger.getLogger(clazze.getName()).info(message);
	}
	
}