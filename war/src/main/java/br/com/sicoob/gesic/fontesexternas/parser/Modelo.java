package br.com.sicoob.gesic.fontesexternas.parser;

/**
 * Representa o modelo do schema.
 *
 * @author Rogerio Alves Rodrigues
 */
public class Modelo {

  private Integer cabecalho;
  private Integer inicia;

  /**
   * @param cabecalho Numero da linha do cabecalho.
   */
  public void setCabecalho(Integer cabecalho) {
    this.cabecalho = cabecalho;
  }

  /**
   * @return Numero da linha do cabecalho.
   */
  public Integer getCabecalho() {
    return this.cabecalho;
  }

  /**
   * @param inicia Numero da linha dos itens.
   */
  public void setInicia(Integer inicia) {
    this.inicia = inicia;
  }

  /**
   * @return Numero da linha dos itens.
   */
  public Integer getInicia() {
    return this.inicia;
  }

}
