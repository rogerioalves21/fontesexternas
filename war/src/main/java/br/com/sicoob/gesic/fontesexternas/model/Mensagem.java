package br.com.sicoob.gesic.fontesexternas.model;

/**
 * Mensagem rest.
 *
 * @author Rogerio Alves Rodrigues
 */
public class Mensagem {

  private String code;
  private String descricao;

  /**
   * @param code the code.
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @return the code.
   */
  public String getCode() {
    return this.code;
  }

  /**
   * @param descricao the descricao.
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  /**
   * @return the descricao.
   */
  public String getDescricao() {
    return this.descricao;
  }

}
