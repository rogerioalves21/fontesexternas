package br.com.sicoob.gesic.fontesexternas.util;

import br.com.sicoob.gesic.fontesexternas.hadoop.configuracao.Constantes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Classe responsavel por descompactar o conteudo de um arquivo .zip
 *
 * @author Rogerio Alves Rodrigues
 */
public class Descompactador {

  public static final String OUTPUT_FOLDER = Constantes.PASTA_INPUT.getValor();

/**
   * Descompacta os arquivos dentro do zipfile e extrai para o output folder.
   *
   * @param zipFile      Arquivo zipado.
   * @param outputFolder Pasta de saida.
   * @param charSet      Encoding do arquivo.
   * @throws IOException Erro ao descompactar o arquivo.
   */
  public void descompactar(String nomeArquivoDescompactado,
    String zipFile,
    String outputFolder,
    Charset charSet
  ) throws IOException {
    byte[] buffer = new byte[1024];

    File folder = new File(OUTPUT_FOLDER);
    if (!folder.exists()) {
      folder.mkdir();
    }

    ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile), charSet);
    ZipEntry ze = zis.getNextEntry();
    while (ze != null) {
      String fileName = ze.getName();
      File newFile2 = new File(outputFolder + File.separator + nomeArquivoDescompactado.replaceAll(".zip",".xls"));
      File newFile = new File(outputFolder + File.separator + fileName);
      new File(newFile.getParent()).mkdirs();
      FileOutputStream fos = new FileOutputStream(newFile2);
      int len;
      while ((len = zis.read(buffer)) > 0) {
        fos.write(buffer, 0, len);
      }
      fos.close();
      ze = zis.getNextEntry();
    }
    zis.closeEntry();
    zis.close();
  }

  /**
   * Descompacta os arquivos dentro do zipfile e extrai para o output folder.
   *
   * @param zipFile      Arquivo zipado.
   * @param outputFolder Pasta de saida.
   * @param charSet      Encoding do arquivo.
   * @throws IOException Erro ao descompactar o arquivo.
   */
  public void descompactar(String zipFile, String outputFolder, Charset charSet) throws IOException {
    byte[] buffer = new byte[1024];

    File folder = new File(OUTPUT_FOLDER);
    if (!folder.exists()) {
      folder.mkdir();
    }

    ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile), charSet);
    ZipEntry ze = zis.getNextEntry();
    while (ze != null) {
      String fileName = ze.getName();
      File newFile = new File(outputFolder + File.separator + fileName);
      new File(newFile.getParent()).mkdirs();
      FileOutputStream fos = new FileOutputStream(newFile);
      int len;
      while ((len = zis.read(buffer)) > 0) {
        fos.write(buffer, 0, len);
      }
      fos.close();
      ze = zis.getNextEntry();
    }
    zis.closeEntry();
    zis.close();
  }

  /**
   * Descompacta os arquivos dentro do zipfile e extrai para o output folder.
   *
   * @param zipFile      Arquivo zipado.
   * @param outputFolder Pasta de saida.
   * @param sufixo       Sufixo para o arquivo.
   * @throws IOException Erro ao descompactar o arquivo.
   */
  public void descompactar(String zipFile, String outputFolder, String sufixo) throws IOException {
    byte[] buffer = new byte[1024];

    File folder = new File(OUTPUT_FOLDER);
    if (!folder.exists()) {
      folder.mkdir();
    }

    ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
    ZipEntry ze = zis.getNextEntry();
    while (ze != null) {
      String fileName = ze.getName();
      File newFile = new File(outputFolder + File.separator + fileName.replaceAll(".CSV", sufixo));
      new File(newFile.getParent()).mkdirs();
      FileOutputStream fos = new FileOutputStream(newFile);
      int len;
      while ((len = zis.read(buffer)) > 0) {
        fos.write(buffer, 0, len);
      }
      fos.close();
      ze = zis.getNextEntry();
    }
    zis.closeEntry();
    zis.close();
  }

}
