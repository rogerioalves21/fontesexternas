package br.com.sicoob.gesic.fontesexternas.extracao.ibge;

import br.com.sicoob.gesic.fontesexternas.anotacoes.Extratora;
import br.com.sicoob.gesic.fontesexternas.anotacoes.TipoExtratora;
import br.com.sicoob.gesic.fontesexternas.extracao.Extrator;
import javax.naming.OperationNotSupportedException;

/**
 * Classe responsavel por realizar o download do arquivo do IBGE.
 *
 * @author Rogerio Alves Rodrigues
 */
@Extratora(tipo = TipoExtratora.IBGE)
public class IBGEExtrator implements Extrator {

  private static final String ENDPOINT = "ftp://ftp.ibge.gov.br/Pib_Municipios/2015/base/base_de_dados_2010_2015_xls.zip";

  /**
   * {@inheritDoc}
   */
  @Override
  public String extrair() throws OperationNotSupportedException {
    return new Builder()
            .url(ENDPOINT)
            .extrairArquivo();
  }

}
