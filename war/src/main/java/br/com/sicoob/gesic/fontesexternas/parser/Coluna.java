package br.com.sicoob.gesic.fontesexternas.parser;

/**
 * Representa a coluna do schema.
 *
 * @author Rogerio Alves Rodrigues
 */
public class Coluna {

  private String nome;
  private Integer numero;
  private String tipo;
  private Boolean removeCaracterEspecial;
  private Boolean retiraPontoZero;

  /**
   * Setter.
   *
   * @param nome Nome da coluna.
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * @return Nome da coluna.
   */
  public String getNome() {
    return this.nome;
  }

  /**
   * Setter.
   *
   * @param numero da coluna.
   */
  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  /**
   * @return Numero da coluna.
   */
  public Integer getNumero() {
    return this.numero;
  }

  /**
   * @return the tipo
   */
  public String getTipo() {
    return tipo;
  }

  /**
   * @param tipo the tipo to set
   */
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  /**
   * @return the removeCaracterEspecial
   */
  public Boolean getRemoveCaracterEspecial() {
    return removeCaracterEspecial;
  }

  /**
   * @param removeCaracterEspecial the removeCaracterEspecial to set
   */
  public void setRemoveCaracterEspecial(Boolean removeCaracterEspecial) {
    this.removeCaracterEspecial = removeCaracterEspecial;
  }

  /**
   * @return the retiraPontoZero
   */
  public Boolean getRetiraPontoZero() {
    return retiraPontoZero;
  }

  /**
   * @param retiraPontoZero the retiraPontoZero to set
   */
  public void setRetiraPontoZero(Boolean retiraPontoZero) {
    this.retiraPontoZero = retiraPontoZero;
  }
  
}
