package br.com.sicoob.gesic.fontesexternas.parser.ibge;

import br.com.bancoob.excecao.BancoobRuntimeException;
import br.com.sicoob.gesic.fontesexternas.hadoop.configuracao.Constantes;
import br.com.sicoob.gesic.fontesexternas.parser.FontesExternasParser;
import br.com.sicoob.gesic.fontesexternas.parser.agenciasbancarias.AgenciasBancariasParser;
import br.com.sicoob.gesic.fontesexternas.util.WebUtil;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Classe reponsavel por converter o arquivo do ibge excel para CSV.
 *
 * @author Rogerio Alves Rodrigues
 */
public class IBGEParser implements FontesExternasParser {

  private static final String PATH = "META-INF/schemas/";
  private static final String SCHEMA_NAME = "ibge_schema.v1.xml";
  private static final int ABA_EXCEL = 0;
  private static final SicoobLoggerPadrao LOG = SicoobLoggerPadrao.getInstance(IBGEParser.class);

  private static final double MEG = (Math.pow(1024, 2));
  private static final String PONTO_E_VIRGULA = ";";
  private File arquivo;

  /**
   * Cria o arquivo e abre o arquivo existente.
   *
   * Converte o sufixo do arquivo de .xls para .csv para a criacao do arquivo.
   *
   * @return O arquivo.
   * @throws IOException para algum erro na geração do arquivo.
   */
  private File obterArquivo(String nomeArquivo) throws IOException {
    if (arquivo == null) {
      arquivo = new File(Constantes.PASTA_INPUT.getValor().concat(nomeArquivo.replaceAll(".xls", ".csv")));
      if (!arquivo.exists()) {
        arquivo.createNewFile();
      }
    }
    return arquivo;
  }

  /**
   * Fecha os recursos aberto de leitura e escrita de arquivos.
   *
   * @param writer         Escritor.
   * @param bufferedWriter Buffer.
   */
  private void fecharRecursos(FileWriter writer, BufferedWriter bufferedWriter) {
    try {
      if (bufferedWriter != null) {
        bufferedWriter.flush();
        bufferedWriter.close();
      }

      if (writer != null) {
        writer.close();
      }
    } catch (IOException excecao) {
      SicoobLoggerPadrao.getInstance(getClass()).erro(excecao, "Erro ao fechar o arquivo");
    }
  }

  /**
   * Obtem o schema para o inputStream.
   *
   * @return Modelo Schema.
   * @throws FileNotFoundException Schema nao encontrado.
   */
  private IBGEModel getSchema() throws FileNotFoundException {
    ClassLoader tccl = Thread.currentThread().getContextClassLoader();
    InputStream inputStream = tccl.getResourceAsStream(PATH.concat(SCHEMA_NAME));
    if (inputStream == null) {
      throw new FileNotFoundException("Schema nao encontrado!");
    }
    XStream xStream = new XStream(new DomDriver());
    xStream.alias("schema", IBGEColuna.class);
    xStream.alias("coluna", IBGEModel.class);
    IBGEModel schema = (IBGEModel) xStream.fromXML(inputStream);
    return schema;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executar(String nomeArquivo) throws FileNotFoundException {
    nomeArquivo = nomeArquivo.replaceAll(".zip", ".xls");
    LOG.debug("Realizando conversao do arquivo .xls para .csv");
    FileWriter writer = null;
    BufferedWriter bufferedWriter = null;
    try {

      // Abre/Cria o arquivo
      int tamanhoDoBuffer = (int) MEG;
      File arquivoCSV = obterArquivo(nomeArquivo);
      writer = new FileWriter(arquivoCSV, Boolean.FALSE);
      bufferedWriter = new BufferedWriter(writer, tamanhoDoBuffer);

      // Obtem o schema
      IBGEModel schema = getSchema();

      // Abre o arquivoExcel
      File arquivoExcel = new File(Constantes.PASTA_INPUT.getValor().concat(nomeArquivo));
      FileInputStream input_document = new FileInputStream(arquivoExcel);

      // Abre o xsl
      HSSFWorkbook workbook = new HSSFWorkbook(input_document);

      // Abre a aba 0 da planilha
      HSSFSheet datatypeSheet = workbook.getSheetAt(ABA_EXCEL);
      Iterator<Row> iterator = datatypeSheet.iterator();

      // Linha do CSV
      final StringBuilder linhaCabecalho = new StringBuilder();

      // adiciona a coluna de data no cabecalho do csv
      linhaCabecalho.append("DATA").append(PONTO_E_VIRGULA);

      // Obtem o cabecalho
      int contadorLinhas = 0;
      List<Row> linhas = Lists.newArrayList(iterator);

      for (Row currentRow : linhas) {
        List<Cell> celulas = Lists.newArrayList(currentRow.iterator());

        // Cabecalho
        if (contadorLinhas == schema.getCabecalho()) {
          schema.getColunas().stream().map((coluna) -> celulas.get(coluna.getNumero())).forEachOrdered((itemCell) -> {
            linhaCabecalho.append(itemCell.getStringCellValue()).append(PONTO_E_VIRGULA);
          });
          bufferedWriter.write(WebUtil.removerCaracteresEspeciais(linhaCabecalho.toString()));
          bufferedWriter.newLine();
        }

        // Conteudo
        if (contadorLinhas >= schema.getInicia() && celulas.size() == schema.getColunas().size()) {
          final StringBuilder linhaCSV = new StringBuilder();
          linhaCSV.append(WebUtil.getDataFromNomeArquivo(nomeArquivo)).append(PONTO_E_VIRGULA);
          schema.getColunas().forEach((coluna) -> {
            Cell itemCell = celulas.get(coluna.getNumero());
            switch (itemCell.getCellType()) {
              case Cell.CELL_TYPE_STRING:
                linhaCSV.append(tratarFormatoString(itemCell, coluna)).append(PONTO_E_VIRGULA);
                break;
              case Cell.CELL_TYPE_NUMERIC:
                linhaCSV.append(tratarFormatoNumero(itemCell, coluna)).append(PONTO_E_VIRGULA);
                break;
            }
          });
          bufferedWriter.write(linhaCSV.toString());
          bufferedWriter.newLine();
        }
        contadorLinhas++;
      }
    } catch (IOException | EncryptedDocumentException excecao) {
      LOG.alerta(excecao, "Erro ao converter o arquivo");
      Logger.getLogger(AgenciasBancariasParser.class.getName()).log(Level.SEVERE, "Arquivo nao convertido!", excecao);
      throw new FileNotFoundException("Arquivo nao convertido ou nao encontrado");
    } finally {
      fecharRecursos(writer, bufferedWriter);
    }

    // Cria o arquivo com encondig correto
    try {
      Logger.getLogger(AgenciasBancariasParser.class.getName()).log(Level.INFO, "CRIANDO O ARQUIVO COM ENCONDING CORRETO");
      criarArquivoUTF8(nomeArquivo.replaceAll(".xls", ".csv"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Formata a celula quando a mesma eh do estilo Numerica.
   *
   * @param itemCell Item da celula.
   * @param coluna   Coluna.
   * @return Numero formatado.
   */
  private String tratarFormatoNumero(Cell itemCell, IBGEColuna coluna) {
    Double valor = itemCell.getNumericCellValue();
    String valorNumerico = (valor.intValue() + "").replaceAll("E7", "");
    if (coluna.getRemoveCaracterEspecial() && !coluna.getRetiraPontoZero()) {
      switch (coluna.getNome()) {
        case "CNPJ":
          return formatarCnpj(valorNumerico).replaceAll("\\.", "");
        default:
          return valorNumerico.trim().replaceAll("\\.", "");
      }
    } else if (coluna.getRemoveCaracterEspecial() && coluna.getRetiraPontoZero()) {
      return valorNumerico.trim().replaceAll("\\.0", "");
    } else {
      switch (coluna.getNome()) {
        case "SEQUENCIAL DO CNPJ":
          return WebUtil.formatarSequencialDoCnpj(valorNumerico);
        default:
          return valorNumerico.trim();
      }
    }
  }

  /**
   * Trata quando o formato do valor da celula eh string.
   *
   * @param itemCell Celula.
   * @param coluna   Coluna do Schema.
   * @return string formatada.
   */
  private String tratarFormatoString(Cell itemCell, IBGEColuna coluna) {
    try {
      String frase = null;
      if (coluna.getRemoveCaracterEspecial() && !coluna.getRetiraPontoZero()) {
        frase = itemCell.getStringCellValue().trim().replaceAll("\\.", "");
      } else {
        switch (coluna.getNome()) {
          default:
            frase = itemCell.getStringCellValue().trim();
        }

      }
      return frase;
    } catch (Exception excecao) {
      LOG.alerta(excecao, "Erro ao converter o arquivo");
      throw new BancoobRuntimeException(excecao);
    }
  }

  /**
   * Formata o numero da cooperativa completando com zeros a esquerda Total de
   * digitos igual a 4
   *
   * @param idInstituicao
   * @return Numero da Instituicao formatado
   */
  private String formatarCnpj(String cnpj) {
    NumberFormat nf = new DecimalFormat("0");
    nf.setMinimumIntegerDigits(8);
    String numeroFormatado = nf.format(Integer.parseInt(cnpj));
    return numeroFormatado;
  }

  private void criarArquivoUTF8(String arquivoCSV) throws IOException {
    Logger.getLogger(AgenciasBancariasParser.class.getName()).log(Level.INFO, ">> ".concat(arquivoCSV));
    String arquivoUTF = arquivoCSV.replaceAll(".csv", "_Parse.csv");
    Logger.getLogger(AgenciasBancariasParser.class.getName()).log(Level.INFO, ">> ".concat(arquivoUTF));
    int tamanhoDoBuffer = (int) MEG;
    File arquivoUTF8 = new File(Constantes.PASTA_INPUT.getValor().concat(arquivoUTF));
    arquivoUTF8.createNewFile();
    FileWriterWithEncoding writer = new FileWriterWithEncoding(arquivoUTF8, Charsets.UTF_8, Boolean.FALSE);
    BufferedWriter bufferedWriter = new BufferedWriter(writer, tamanhoDoBuffer);
    FileReader fr = new FileReader(Constantes.PASTA_INPUT.getValor().concat(arquivoCSV));
    BufferedReader br = new BufferedReader(fr);
    String linha;
    while ((linha = br.readLine()) != null) {
      byte[] utf8 = linha.getBytes("UTF-8");
      bufferedWriter.write(new String(utf8, "UTF-8"));
      bufferedWriter.newLine();
    }
    bufferedWriter.close();
    fr.close();
  }

}
