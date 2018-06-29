package br.com.sicoob.gesic.fontesexternas.parser.sncc;

import br.com.sicoob.gesic.fontesexternas.parser.Modelo;
import java.util.List;

/**
 * Representa o modelo dos schemas.
 *
 * @author Rogerio Alves Rodrigues
 */
public class SNCCModel extends Modelo {

  private List<SNCCColuna> colunas;

  /**
   * @param colunas Lista de colunas.
   */
  public void setColunas(List<SNCCColuna> colunas) {
    this.colunas = colunas;
  }

  /**
   * @return Lista de colunas.
   */
  public List<SNCCColuna> getColunas() {
    return this.colunas;
  }
}
