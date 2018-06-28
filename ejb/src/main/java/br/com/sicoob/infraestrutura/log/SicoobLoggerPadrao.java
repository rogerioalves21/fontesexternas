package br.com.sicoob.infraestrutura.log;

import java.util.logging.Logger;

/**
 * Logger padrao.
 * 
 * @author Rogerio Alves Rodrigues
 */
public class SicoobLoggerPadrao {
	
	private final Logger log = Logger.getLogger(SicoobLoggerPadrao.class.getName());
	private static final SicoobLoggerPadrao INSTANCE = new SicoobLoggerPadrao();
	//private static final Class clazz;
	
	/**
	 * Retorna a instancia singleton.
	 * 
	 * @param clazz Classe a ser logada.
	 */
	public static SicoobLoggerPadrao getInstance(Class clazz) {
		//this.clazz = clazz;
		return INSTANCE;
	}
	
	public void debug(String message) {
		log.info(message);
	}
	
	public void alerta(Exception excecao, String message) {
		
	}
	
	public void erro(Exception excecao, String message) {
		
	}
	
	public void info(String message) {
		log.info(message);
	}
	
}