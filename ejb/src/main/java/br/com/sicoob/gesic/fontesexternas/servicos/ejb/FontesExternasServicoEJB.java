package br.com.sicoob.gesic.fontesexternas.servicos.ejb;

import br.com.sicoob.gesic.fontesexternas.hadoop.HadoopHDFSOperacoes;
import br.com.sicoob.gesic.fontesexternas.servicos.FontesExternasServico;
import java.io.InputStream;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.OperationNotSupportedException;

/**
 * Implementacao dos servicos de fontes externas.
 *
 * @author Rogerio Alves Rodrigues
 */
@Stateless
public class FontesExternasServicoEJB implements FontesExternasServico {

  @Inject
  private HadoopHDFSOperacoes operacoes;

  /**
   * {@inheritDoc}
   */
  @Override
  public void salvarArquivoHDFS(String nomeArquivoLocal) throws OperationNotSupportedException {
    operacoes.escreverNaPasta(nomeArquivoLocal);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void salvarArquivoLocal(InputStream inputStream) {

  }

}
