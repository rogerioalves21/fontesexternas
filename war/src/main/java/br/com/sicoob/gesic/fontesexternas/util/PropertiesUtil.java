package br.com.sicoob.gesic.fontesexternas.util;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;

/**
 * Classe responsavel por obter valores gravados em arquivo de propriedades.
 * 
 * @author Rogerio Alves Rodrigues
 */
public class PropertiesUtil {
  
  private static PropertiesUtil INSTANCIA;
  private static final String FILE = "/fontesexternas.properties";
  private Properties propriedades;
  
  /**
   * Obtem uma nova instancia Singleton.
   *
   * @return um {@link PropertiesUtil).
   */
  public static PropertiesUtil getInstance() {
    if (INSTANCIA == null) {
      synchronized (PropertiesUtil.class) {
        INSTANCIA = new PropertiesUtil();
      }
    }
    return INSTANCIA;
  }
  
  /**
   * Construtor.
   */
  private PropertiesUtil() {

  }

  /**
   * Carrega o arquivo properties na variavel {@code properties}.
   */
  private void carregarArquivo() {
    try {
      InputStream input = PropertiesUtil.class.getResourceAsStream(FILE);
      this.propriedades.load(input);
    } catch (Exception ex) {
      SicoobLoggerPadrao.getInstance(getClass()).alerta(ex, "Erro ao ler o arquivo de propriedades.");
    }
  }
  
  /**
   * Obtem uma mensagem pela chave.
   *
   * @param chave chave.
   * @return Mensagem.
   */
  public String obter(String chave) {
    inicializar();
    return (String) propriedades.get(chave);
  }
  
  /**
   * Inicializa o arquivo de properties/
   */
  private void inicializar() {
    if (this.propriedades == null) {
      this.propriedades = new Properties();
      carregarArquivo();
    }
  }
  
}