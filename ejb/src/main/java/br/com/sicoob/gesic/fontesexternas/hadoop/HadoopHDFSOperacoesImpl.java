package br.com.sicoob.gesic.fontesexternas.hadoop;

import br.com.sicoob.gesic.fontesexternas.hadoop.configuracao.Constantes;
import br.com.sicoob.gesic.fontesexternas.hadoop.configuracao.HadoopConfiguracao;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import java.io.IOException;
import javax.inject.Inject;
import javax.naming.OperationNotSupportedException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;

/**
 * Implementacao das operacoes no hadoop distributed file system.
 *
 * @author Rogerio Alves Rodrigues
 */
public class HadoopHDFSOperacoesImpl implements HadoopHDFSOperacoes {

  private static final SicoobLoggerPadrao LOG = SicoobLoggerPadrao.getInstance(HadoopHDFSOperacoesImpl.class);

  @Inject
  private HadoopConfiguracao hadoopConfiguracao;
    
  /**
   * {@inheritDoc}
   */
  @Override
  public void escreverNaPasta(String nomeArquivoLocal) throws OperationNotSupportedException {
    try {
      LOG.debug("Criando a configuracao...");      
      Configuration configuracao = hadoopConfiguracao.getConfiguration();

      LOG.debug("Conectando com o file system...");
      FileSystem fileSystem = FileSystem.get(configuracao);

      LOG.debug("Criando o input path...");
      Path localFilePath = new Path(Constantes.PASTA_INPUT.getValor().concat(nomeArquivoLocal));

      LOG.debug("Criando o output path...");
      Path hdfsFilePath = new Path(
              Constantes.PASTA_HDFS.getValor().concat(Constantes.BARRA.getValor().concat(nomeArquivoLocal)));

      LOG.debug("Enviando para o HDFS...");
      fileSystem.copyFromLocalFile(localFilePath, hdfsFilePath);

      LOG.debug("Alterando a permissao...");
      fileSystem.setPermission(hdfsFilePath, new FsPermission("777"));
    } catch (IOException | IllegalArgumentException excecao) {
      LOG.alerta(excecao, "Erro ao incluir o arquivo no HDFS!");
      throw new OperationNotSupportedException("Erro ao incluir o arquivo no HDFS!");
    }
  }

}
