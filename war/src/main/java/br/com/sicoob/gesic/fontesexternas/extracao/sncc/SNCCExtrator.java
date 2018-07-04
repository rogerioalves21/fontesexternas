package br.com.sicoob.gesic.fontesexternas.extracao.sncc;

import br.com.sicoob.gesic.fontesexternas.anotacoes.Extratora;
import br.com.sicoob.gesic.fontesexternas.anotacoes.TipoExtratora;
import br.com.sicoob.gesic.fontesexternas.extracao.Extrator;
import javax.naming.OperationNotSupportedException;
import br.com.sicoob.gesic.fontesexternas.util.PropertiesUtil;

/**
 * Implementacao responsavel por obter o arquivo de "Cooperativas de Credito".
 *
 * @author Rogerio Alves Rodrigues
 */
@Extratora(tipo = TipoExtratora.SCNCC)
public class SNCCExtrator implements Extrator {

  private static final String ENDPOINT = "http://www.bcb.gov.br/fis/info/instituicoes.asp?idpai=INFCAD";

  /**
   * {@inheritDoc}
   */
  @Override
  public String extrair() throws OperationNotSupportedException {
    return new Builder()
            .configurarWebClient()
            .acessarURL(PropertiesUtil.getInstance().obter("sncc.host"))
            .extrairArquivo();
  }

}
