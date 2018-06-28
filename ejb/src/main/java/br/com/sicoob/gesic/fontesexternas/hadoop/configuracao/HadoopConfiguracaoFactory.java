package br.com.sicoob.gesic.fontesexternas.hadoop.configuracao;

import br.com.sicoob.gesic.fontesexternas.anotacoes.ConfiguracaoProducer;
import javax.enterprise.inject.Produces;

/**
 * Fabrica de configuracao para o Hadoop.
 *
 * @author Rogerio Alves Rodrigues
 */
public class HadoopConfiguracaoFactory {

  /**
   * Obtem a implementacao de configuracao baseada no tipo passado como parametro.
   *
   * @return Configuracao.
   */
  @Produces
  @ConfiguracaoProducer
  public HadoopConfiguracao getConfiguracao() {
    return new HomolConfiguracao();
  }

}
