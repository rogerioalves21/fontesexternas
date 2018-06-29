package br.com.sicoob.gesic.fontesexternas.download;

import javax.naming.OperationNotSupportedException;

import org.junit.Assert;
import org.junit.Test;

import br.com.sicoob.gesic.fontesexternas.extracao.Extrator;
import br.com.sicoob.gesic.fontesexternas.extracao.agenciasbancarias.AgenciasBancariasExtrator;

/**
 * Classe responsavel por realizar testes de download de arquivos.
 * 
 * @author Rogerio Alves Rodrigues
 */
public class DownloadTest {
  
  /**
   * 
   */
  @Test
  public void deveObterArquivoDeAgenciasBancarias() {
    Extrator extrator = new AgenciasBancariasExtrator();
    try {
      extrator.extrair();
    } catch (OperationNotSupportedException e) {
      Assert.fail(e.getMessage());
    }
  }

}
