package br.com.sicoob.fontesexternas.acesso;

import br.com.sicoob.gesic.fontesexternas.hadoop.HadoopHDFSOperacoes;
import br.com.sicoob.gesic.fontesexternas.hadoop.HadoopHDFSOperacoesImpl;
import br.com.sicoob.gesic.fontesexternas.hadoop.configuracao.Constantes;
import br.com.sicoob.gesic.fontesexternas.hadoop.configuracao.HadoopConfiguracao;
import br.com.sicoob.gesic.fontesexternas.hadoop.configuracao.HomolConfiguracao;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe para testes de integracao com o Hadoop Distributed File System.
 *
 * @author Rogerio Alves Rodrigues
 */
public class AcessoFileSystem {

  private static final SicoobLoggerPadrao LOG = SicoobLoggerPadrao.getInstance(AcessoFileSystem.class);

  @Before
  public void setUp() {
    System.setProperty("hadoop-host-01", "hadh101.homologacao.com.br:8020");
    System.setProperty("hadoop-host-02", "hadh107.homologacao.com.br:8020");
  }
  
  /**
   * Obtem a configuracao e expoe os arquivos encontrados na pasta de utilizacao
   * da Gesic.
   *
   * Deve retornar pelo menos um arquivo.
   */
  @Test
  public void deveRetornarArquivosDaPastaDeProducao() {
    List<String> arquivosEncontrados = new ArrayList<>();
    
    try {
      HadoopConfiguracao hadoopConfiguracao = new HomolConfiguracao();
      Configuration conf = hadoopConfiguracao.getConfiguration();
      FileSystem hadoopFileSystem = FileSystem.get(conf);
      Path caminhoPastaGesic = new Path(Constantes.PASTA_HDFS.getValor());
      RemoteIterator<LocatedFileStatus> fileStatusListIterator = hadoopFileSystem.listFiles(caminhoPastaGesic, true);
      while (fileStatusListIterator.hasNext()) {
        LocatedFileStatus fileStatus = fileStatusListIterator.next();
        LOG.info("Arquivo: ".concat(fileStatus.getPath().getName()));
        arquivosEncontrados.add(fileStatus.getPath().getName());
      }
    } catch (IOException | IllegalArgumentException excecao) {
      LOG.erro(excecao, "Erro ao acessar o file system!");
      Assert.fail("Nao deveria ocorrer nenhuma excecao!");
    }
    Assert.assertTrue(arquivosEncontrados.size() > 0);
  }

  /**
   * Deve retornar erro ao acessar pasta invalida do file system.
   */
  @Test
  public void deveRetornarErroAoAcessarFileSystem() {
    try {
      System.setProperty("hadoop-host-01", "hadh101.homologacao.com.br:8020");
      System.setProperty("hadoop-host-02", "hadh107.homologacao.com.br:8020");
      HadoopConfiguracao hadoopConfiguracao = new HomolConfiguracao();
      Configuration conf = hadoopConfiguracao.getConfiguration();
      FileSystem hadoopFileSystem = FileSystem.get(conf);
      Path caminhoPastaGesic = new Path(Constantes.PASTA_HDFS_INVALIDO.getValor());
      hadoopFileSystem.listFiles(caminhoPastaGesic, true);
      Assert.fail("Deveria ocorrer excecao");
    } catch (IOException excecao) {
      LOG.erro(excecao, "Erro ao acessar o file system!");
      Assert.assertTrue(excecao != null);
    }
  }

  @Test
  public void deveEnviarArquivoAoFileSystem() {
    try {
      System.setProperty("hadoop-host-01", "hadh101.homologacao.com.br:8020");
      System.setProperty("hadoop-host-02", "hadh107.homologacao.com.br:8020");
      HadoopHDFSOperacoes escritor = new HadoopHDFSOperacoesImpl();
      escritor.escreverNaPasta("nomes.csv");
    } catch (OperationNotSupportedException excecao) {
      LOG.erro(excecao, "Erro ao escrever no file system!");
      Assert.fail("Deveria ocorrer excecao");
    }
  }

}
