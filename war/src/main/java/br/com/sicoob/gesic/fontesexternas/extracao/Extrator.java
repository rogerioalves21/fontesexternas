package br.com.sicoob.gesic.fontesexternas.extracao;

import javax.naming.OperationNotSupportedException;

public interface Extrator {

  /**
   * Extrai o recurso do destino configurado.
   *
   * @return Nome do arquivo do download.
   * @throws OperationNotSupportedException Operacao nao suportada.
   */
  String extrair() throws OperationNotSupportedException;

}
