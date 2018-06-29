package br.com.sicoob.gesic.fontesexternas.util;

import org.junit.Assert;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sicoob
 */
public class WebUtilTest {

  @Test
  public void deveFormatarSequencialDoCnpj() {
    try {
      String formatado = WebUtil.formatarSequencialDoCnpj("2");
      System.out.println(formatado);
      Assert.assertEquals("0002", formatado);
    } catch (Exception excecao) {
      Assert.fail(excecao.getMessage());
    }
  }

  @Test
  public void deveRemoverJogoDaVelha() {
    String celula = "#DATA_BASE";
    Assert.assertEquals("DATA_BASE", celula.replaceAll("#", ""));
  }

}
