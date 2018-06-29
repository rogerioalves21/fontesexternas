package br.com.sicoob.gesic.fontesexternas.parser.ibge;

import br.com.sicoob.gesic.fontesexternas.parser.Modelo;
import java.util.List;

/**
 * Representa o modelo do schema.
 *
 * @author Rogerio Alves Rodrigues
 */
public class IBGEModel extends Modelo {

  private List<IBGEColuna> colunas;

  /**
   * @return the colunas
   */
  public List<IBGEColuna> getColunas() {
    return colunas;
  }

  /**
   * @param colunas the colunas to set
   */
  public void setColunas(List<IBGEColuna> colunas) {
    this.colunas = colunas;
  }

}
