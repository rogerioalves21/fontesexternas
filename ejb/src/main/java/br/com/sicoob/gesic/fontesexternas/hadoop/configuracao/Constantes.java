package br.com.sicoob.gesic.fontesexternas.hadoop.configuracao;

/**
 * Constantes utilizadas na aplicacao.
 *
 * @author Rogerio Alves Rodrigues
 */
public enum Constantes {

  PASTA_INPUT_TESTE("/projects/fontexexternas/hadoop/input/"),
  PASTA_INPUT("/Users/rogerio.rodrigues/hadoop/input/"),
  PASTA_HDFS("/data/gesic/gesic-java/fontes-externas"),
  PASTA_HDFS_INVALIDO("/data/gesic/gesic-java/fontes-externas2"),
  BARRA("/");

  private String chave;

  /**
   * Construtor.
   *
   * @param chave Valor do parametro.
   */
  private Constantes(String chave) {
    this.chave = chave;
  }

  /**
   * @return Valor da propriedade.
   */
  public String getValor() {
    return this.chave;
  }

}
