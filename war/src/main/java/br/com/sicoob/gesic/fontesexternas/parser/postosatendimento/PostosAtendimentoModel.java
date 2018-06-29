package br.com.sicoob.gesic.fontesexternas.parser.postosatendimento;

import br.com.sicoob.gesic.fontesexternas.parser.Modelo;
import java.util.List;

/**
 * Representa o modelo do schema.
 *
 * @author Rogerio Alves Rodrigues
 */
public class PostosAtendimentoModel extends Modelo {

  private List<PostosAtendimentoColuna> colunas;

  /**
   * @param colunas Lista de colunas.
   */
  public void setColunas(List<PostosAtendimentoColuna> colunas) {
    this.colunas = colunas;
  }

  /**
   * @return Lista de colunas.
   */
  public List<PostosAtendimentoColuna> getColunas() {
    return this.colunas;
  }
  
}
