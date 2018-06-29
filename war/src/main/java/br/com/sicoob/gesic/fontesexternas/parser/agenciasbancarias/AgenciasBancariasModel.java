package br.com.sicoob.gesic.fontesexternas.parser.agenciasbancarias;

import br.com.sicoob.gesic.fontesexternas.parser.Modelo;
import java.util.List;

/**
 * Representa o modelo do schema.
 *
 * @author Rogerio Alves Rodrigues
 */
public class AgenciasBancariasModel extends Modelo {

  private List<AgenciasBancariasColuna> colunas;

  /**
   * @param colunas Lista de colunas.
   */
  public void setColunas(List<AgenciasBancariasColuna> colunas) {
    this.colunas = colunas;
  }

  /**
   * @return Lista de colunas.
   */
  public List<AgenciasBancariasColuna> getColunas() {
    return this.colunas;
  }
}
