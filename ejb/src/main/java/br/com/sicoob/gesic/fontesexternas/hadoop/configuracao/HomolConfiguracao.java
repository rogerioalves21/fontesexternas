package br.com.sicoob.gesic.fontesexternas.hadoop.configuracao;

import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import org.apache.hadoop.conf.Configuration;

/**
 * Implementacao para desenvolvimento. A configuracao ira apontar para um Hadoop
 * local.
 *
 * @author Rogerio Alves Rodrigues
 */
public class HomolConfiguracao implements HadoopConfiguracao {

  private static final SicoobLoggerPadrao LOG = SicoobLoggerPadrao.getInstance(HomolConfiguracao.class);

  /**
   * {@inheritDoc}
   */
  @Override
  public Configuration getConfiguration() {
    LOG.debug("Montando a configuracao!");
    Configuration hadoopConfiguracao = new Configuration(false);
    hadoopConfiguracao.set("fs.defaultFS", "hdfs://fontesexternas");
    hadoopConfiguracao.set("fs.default.name", hadoopConfiguracao.get("fs.defaultFS"));
    hadoopConfiguracao.set("dfs.nameservices", "fontesexternas");
    hadoopConfiguracao.set("dfs.ha.namenodes.fontesexternas", "nn1,nn2");
    hadoopConfiguracao.set("dfs.namenode.rpc-address.fontesexternas.nn1", System.getProperty("hadoop-host-01"));
    hadoopConfiguracao.set("dfs.namenode.rpc-address.fontesexternas.nn2", System.getProperty("hadoop-host-02"));
    hadoopConfiguracao.set("dfs.client.failover.proxy.provider.fontesexternas", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
    return hadoopConfiguracao;
  }

}
