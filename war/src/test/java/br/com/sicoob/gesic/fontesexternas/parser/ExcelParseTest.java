package br.com.sicoob.gesic.fontesexternas.parser;

import br.com.sicoob.gesic.fontesexternas.parser.sncc.SNCCParser;
import br.com.sicoob.gesic.fontesexternas.util.WebUtil;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import java.io.FileNotFoundException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testes para o parse do excel.
 *
 * @author Rogerio Alves Rodrigues
 */
public class ExcelParseTest {

  private static final SicoobLoggerPadrao LOG = SicoobLoggerPadrao.getInstance(ExcelParseTest.class);

  //@Test
  public void deveRealizarConversaoDeExcelParaCsv() {
    try {
      FontesExternasParser parser = new SNCCParser();
      parser.executar("201804COOPERATIVAS.xls");
    } catch (FileNotFoundException excecao) {
      LOG.alerta(excecao, "Erro ao realizar parse do excel");
      Assert.fail(excecao.getMessage());
    }
  }

 // @Test
  public void deveRemoverCaracteresEspeciais() {
    Assert.assertEquals("WWW_SITE_COM_BR", WebUtil.removerCaracteresEspeciais("WWW.SITE.COM.BR"));
    Assert.assertEquals("EU_SITE_COM", WebUtil.removerCaracteresEspeciais("EU@SITE.COM"));
  }

  //@Test
  public void deveRemoverVirgula() {
    Assert.assertEquals("_", WebUtil.removerCaracteresEspeciais(","));
    Assert.assertEquals("_", WebUtil.removerCaracteresEspeciais(", "));
  }

  //@Test
  public void deveRetornarDataArquivo() {
    Assert.assertEquals("201804", WebUtil.getDataFromNomeArquivo("201804COOPERATIVAS.xls"));
  }

  //@Test
  public void deveTransformarCedilhaEmC() {
    Assert.assertEquals("C", WebUtil.removerCaracteresEspeciais("Ç"));
  }

  //@Test
  public void deveTransformarAspasEmUnderline() {
    Assert.assertEquals("_Eita_", WebUtil.removerCaracteresEspeciais("'Eita'"));
  }

  //@Test
  public void deveTransformarExclamacaoEmUnderline() {
    Assert.assertEquals("Eita_", WebUtil.removerCaracteresEspeciais("Eita!"));
  }

  //@Test
  public void deveTransformarDolarParaS() {
    Assert.assertEquals("S", WebUtil.removerCaracteresEspeciais("$"));
  }

}
