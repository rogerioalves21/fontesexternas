package br.com.sicoob.gesic.fontesexternas.download;

import javax.naming.OperationNotSupportedException;

import org.junit.Assert;
import org.junit.Test;

import br.com.sicoob.gesic.fontesexternas.extracao.Extrator;
import br.com.sicoob.gesic.fontesexternas.extracao.agenciasbancarias.AgenciasBancariasExtrator;
import br.com.sicoob.gesic.fontesexternas.extracao.sncc.SNCCExtrator;
import br.com.sicoob.gesic.fontesexternas.extracao.postosatendimento.PostosAtendimentoExtrator;
import br.com.sicoob.gesic.fontesexternas.extracao.balancetes.BalancetesExtrator;
import br.com.sicoob.gesic.fontesexternas.extracao.ibge.IBGEExtrator;

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
  
  /**
   * 
   */
  @Test
  public void deveObterArquivoDeSNCC() {
    Extrator extrator = new SNCCExtrator();
    try {
      extrator.extrair();
    } catch (OperationNotSupportedException e) {
      Assert.fail(e.getMessage());
    }
  }

  /**
   * 
   */
  @Test
  public void deveObterArquivoDePostosDeAtendimento() {
    Extrator extrator = new PostosAtendimentoExtrator();
    try {
      extrator.extrair();
    } catch (OperationNotSupportedException e) {
      Assert.fail(e.getMessage());
    }
  }
  
  /**
   * 
   */
  @Test
  public void deveObterArquivoDeBalancetes() {
    Extrator extrator = new BalancetesExtrator();
    try {
      extrator.extrair();
    } catch (OperationNotSupportedException e) {
      Assert.fail(e.getMessage());
    }
  }
  
  /**
   * 
   */
  @Test
  public void deveObterArquivoDoIbge() {
    Extrator extrator = new IBGEExtrator();
    try {
      extrator.extrair();
    } catch (OperationNotSupportedException e) {
      Assert.fail(e.getMessage());
    }
  }
}
