package br.com.sicoob.gesic.fontesexternas.util;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;

/**
 * Classe responsavel por manter os metodos utilitarios para a camada web.
 *
 * @author Rogerio Alves Rodrigeus
 */
public class WebUtil {

  private static final Integer ZERO = 0;
  private static final int INICIO_DATA_ARQUIVO = 0;
  private static final int FIM_DATA_ARQUIVO = 6;
  private static final int TAMANHO_FORMATO_SEQUENCIAL_CNPJ = 4;
  private static final String VIRGULA_ESPACO = ", ";
  private static final String UNDERLINE = "_";
  private static final String S = "S";
  private static final String DOLLAR = "\\$";
  private static final String VIRGULA = "\\,";
  private static final String ESPACO = " ";
  private static final String DOIS_PONTOS = ":";
  private static final String BARRA = "/";
  private static final String PONTO = "\\.";
  private static final String ARROBA = "@";
  private static final String ASPA_SIMPLES = "'";
  private static final String EXCLAMACAO = "!";
  private static final String IFEN = "-";
  private static final String ASCII_PATTERN = "[^\\p{ASCII}]";

  /**
   * Retorna o ANOMES do nome do arquivo.
   *
   * @param nomeArquivo Nome do arquivo.
   * @return o ANOMES do nome do arquivo.
   */
  public static String getDataFromNomeArquivo(String nomeArquivo) {
    return nomeArquivo.substring(INICIO_DATA_ARQUIVO, FIM_DATA_ARQUIVO);
  }

  /**
   * Formata o numero sequencial do cnpj para quatro digitos.
   *
   * Caso o tamanho do inteiro nao tenho 4 digitos, o mesmo eh completado com zeros.
   *
   * @param sequencialCnpj Sequencial do cnpj.
   * @return Sequencial do cnpj formatado.
   */
  public static String formatarSequencialDoCnpj(String sequencialCnpj) {
    NumberFormat nf = new DecimalFormat("0");
    nf.setMinimumIntegerDigits(TAMANHO_FORMATO_SEQUENCIAL_CNPJ);
    return nf.format(Integer.parseInt(sequencialCnpj));
  }

  /**
   * Transforma em underline os caracteres definidos no documento de projeto.
   *
   * @param palavra Palavra fonte.
   * @return String normalizada.
   */
  public static final String toUnderline(String palavra) {
    String normalizado = palavra.replaceAll(VIRGULA_ESPACO, UNDERLINE);
    normalizado = normalizado.replaceAll(VIRGULA, UNDERLINE);
    normalizado = normalizado.replaceAll(ESPACO, UNDERLINE);
    normalizado = normalizado.replaceAll(DOIS_PONTOS, UNDERLINE);
    normalizado = normalizado.replaceAll(BARRA, UNDERLINE);
    normalizado = normalizado.replaceAll(PONTO, UNDERLINE);
    normalizado = normalizado.replaceAll(ARROBA, UNDERLINE);
    normalizado = normalizado.replaceAll(ASPA_SIMPLES, UNDERLINE);
    normalizado = normalizado.replaceAll(EXCLAMACAO, UNDERLINE);
    normalizado = normalizado.replaceAll(DOLLAR, S);
    normalizado = normalizado.replaceAll("#", UNDERLINE);
    return normalizado.replaceAll(IFEN, UNDERLINE);
  }

  /**
   * Metodo responsavel por remover os caracteres especiais referentes a fontes externas.
   *
   * @param linha Linha do csv.
   * @return String sem os caracteres especiais.
   */
  public static String removerCaracteresEspeciais(String linha) {
    String normalizado = Normalizer
            .normalize(linha, Normalizer.Form.NFD)
            .replaceAll(ASCII_PATTERN, "");
    return WebUtil.toUnderline(normalizado);
  }

  public static String removerUnderlineInicialEFinal(String palavra) {
    
  }
}
