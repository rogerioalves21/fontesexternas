package br.com.sicoob.gesic.fontesexternas.extracao.postosatendimento;

import br.com.sicoob.gesic.fontesexternas.anotacoes.Extratora;
import br.com.sicoob.gesic.fontesexternas.anotacoes.TipoExtratora;
import br.com.sicoob.gesic.fontesexternas.extracao.Extrator;
import javax.naming.OperationNotSupportedException;

/**
 * Extrator do arquivo de "Postos de atendimentos coopeartivo e bancario".
 *
 * @author Rogerio Alves Rodrigues
 */
@Extratora(tipo = TipoExtratora.POSTOS_ATENDIMENTO)
public class PostosAtendimentoExtrator implements Extrator {

  private static final String ENDPOINT = "http://www.bcb.gov.br/fis/info/agencias.asp";

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
