package br.com.sicoob.gesic.fontesexternas.extracao.agenciasbancarias;

import br.com.sicoob.gesic.fontesexternas.anotacoes.Extratora;
import br.com.sicoob.gesic.fontesexternas.anotacoes.TipoExtratora;
import br.com.sicoob.gesic.fontesexternas.extracao.Extrator;
import javax.naming.OperationNotSupportedException;
import br.com.sicoob.gesic.fontesexternas.util.PropertiesUtil;

/**
 * Classe responsavel por extrair os dados do site do banco central de agencias bancarias.
 *
 * @author Rogerio Alves Rodrigues
 */
@Extratora(tipo = TipoExtratora.AGENCIAS_BANCARIAS)
public class AgenciasBancariasExtrator implements Extrator {

  private static final String ENDPOINT = "http://www.bcb.gov.br/fis/info/agencias.asp";

  /**
   * {@inheritDoc}
   */
  @Override
  public String extrair() throws OperationNotSupportedException {
    return new Builder()
            .configurarWebClient()
            .acessarURL(PropertiesUtil.getInstance().obter("agenciasbancarias.host"))
            .extrairArquivo();
  }

}
