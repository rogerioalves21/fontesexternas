package br.com.sicoob.gesic.fontesexternas.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;

/**
 * Testes para a classe descompactadora.
 *
 * @author Rogerio Alves Rodrigues
 */
public class DescompactadorTest {

  private static final String INPUT_ZIP_FILE = "/Users/rogerio.rodrigues/hadoop/input/201804COOPERATIVAS.zip";

  /**
   * Deve descompactar o zip.
   */
  //@Test
  public void deveDescompactar() {
    try {
      Descompactador unzip = new Descompactador();
      unzip.descompactar(INPUT_ZIP_FILE, Descompactador.OUTPUT_FOLDER, StandardCharsets.ISO_8859_1);
    } catch (IOException excecao) {
      Logger.getLogger(DescompactadorTest.class.getName()).log(Level.SEVERE, "Erro ao descompactar o arquivo", excecao);
      Assert.fail("Nao deveria ocorrer excecao");
    }
  }

}
