package br.com.sicoob.gesic.fontesexternas.hadoop.configuracao;

import org.apache.hadoop.conf.Configuration;

/**
 * Define o comportamento da configuracao para acesso remoto do Hadoop
 * Distributed File System.
 *
 * @author Rogerio Alves Rodrigues
 */
public interface HadoopConfiguracao {

  /**
   * Devolve a configuracao do Hadoop.
   *
   * @return Configuracao do Hadoop.
   */
  Configuration getConfiguration();

}
