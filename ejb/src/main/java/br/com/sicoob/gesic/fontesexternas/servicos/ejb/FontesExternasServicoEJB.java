package br.com.sicoob.gesic.fontesexternas.servicos.ejb;

import br.com.sicoob.gesic.fontesexternas.hadoop.HadoopHDFSOperacoes;
import br.com.sicoob.gesic.fontesexternas.servicos.FontesExternasServico;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.naming.OperationNotSupportedException;

/**
 * Implementacao dos servicos de fontes externas.
 *
 * @author Rogerio Alves Rodrigues
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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

}
