package br.com.sicoob.gesic.fontesexternas.parser.balancetes;

import br.com.sicoob.gesic.fontesexternas.parser.Modelo;
import java.util.List;

/**
 * Representa o Modelo do schema.
 *
 * @author Rogerio Alves Rodrigues
 */
public class BalancetesModel extends Modelo {

  private List<BalancetesColuna> colunas;

  /**
   * @return the colunas
   */
  public List<BalancetesColuna> getColunas() {
    return colunas;
  }

  /**
   * @param colunas the colunas to set
   */
  public void setColunas(List<BalancetesColuna> colunas) {
    this.colunas = colunas;
  }

}
