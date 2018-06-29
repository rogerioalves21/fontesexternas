package br.com.sicoob.gesic.fontesexternas.extracao.balancetes;

import br.com.sicoob.gesic.fontesexternas.anotacoes.Extratora;
import br.com.sicoob.gesic.fontesexternas.anotacoes.TipoExtratora;
import br.com.sicoob.gesic.fontesexternas.extracao.Extrator;
import javax.naming.OperationNotSupportedException;

/**
 * Implementacao para o download do arquivo de "Balancetes e balancos patrimoniais".
 *
 * @author Rogerio Alves Rodrigues
 */
@Extratora(tipo = TipoExtratora.BALANCETES)
public class BalancetesExtrator implements Extrator {

  private static final String ENDPOINT = "http://www4.bcb.gov.br/fis/cosif/balancetes.asp";

  /**
   * {@inheritDoc}
   */
  @Override
  public String extrair() throws OperationNotSupportedException {
    return new Builder()
            .configurarWebClient()
            .acessarURL(ENDPOINT)
            .extrairArquivo();
  }

}
