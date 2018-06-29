package br.com.sicoob.gesic.fontesexternas.parser.balancetes;

import br.com.sicoob.gesic.fontesexternas.hadoop.configuracao.Constantes;
import br.com.sicoob.gesic.fontesexternas.parser.FontesExternasParser;
import br.com.sicoob.gesic.fontesexternas.util.WebUtil;
import com.google.common.base.Charsets;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.output.FileWriterWithEncoding;

/**
 * Implementacao da conversao do arquivo em csv formatado.
 *
 * @author Rogerio Alves Rodrigues
 */
public class BalancetesParser implements FontesExternasParser {

  private static final Logger LOG = Logger.getLogger(BalancetesParser.class.getName());
  private File arquivo;
  private static final double MEG = (Math.pow(1024, 2));
  private static final String PATH = "META-INF/schemas/";
  private static final String SCHEMA_NAME = "balancetes_schema.v1.xml";

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
      arquivo = new File(Constantes.PASTA_INPUT.getValor().concat(nomeArquivo.replaceAll(".csv", "_Parse.csv")));
      if (!arquivo.exists()) {
        arquivo.createNewFile();
      }
    }
    return arquivo;
  }

  /**
   * Obtem o schema para o inputStream;
   *
   * @return Modelo Schema.
   * @throws FileNotFoundException Schema nao encontrado.
   */
  private BalancetesModel getSchema() throws FileNotFoundException {
    ClassLoader tccl = Thread.currentThread().getContextClassLoader();
    InputStream inputStream = tccl.getResourceAsStream(PATH.concat(SCHEMA_NAME));
    if (inputStream == null) {
      throw new FileNotFoundException("Schema nao encontrado!");
    }
    XStream xStream = new XStream(new DomDriver());
    xStream.alias("schema", BalancetesModel.class);
    xStream.alias("coluna", BalancetesColuna.class);
    BalancetesModel schema = (BalancetesModel) xStream.fromXML(inputStream);
    return schema;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executar(String nomeArquivo) throws FileNotFoundException {
    nomeArquivo = nomeArquivo.replaceAll(".zip", ".csv");
    BufferedReader bufferedReader = null;
    FileWriterWithEncoding writer;
    BufferedWriter bufferedWriter;
    String linha;
    String delimitador = ";";
    try {

      // Abre/Cria o arquivo
      int tamanhoDoBuffer = (int) MEG;
      File arquivoCSV = obterArquivo(nomeArquivo);
      writer = new FileWriterWithEncoding(arquivoCSV, Charsets.UTF_8, Boolean.FALSE);
      bufferedWriter = new BufferedWriter(writer, tamanhoDoBuffer);
      
      // Obtem o schema
      BalancetesModel schema = getSchema();

      // Linha do CSV
      final StringBuilder linhaCabecalho = new StringBuilder();

      // Le o arquivo CSV original
      bufferedReader = new BufferedReader(new FileReader(Constantes.PASTA_INPUT.getValor().concat(nomeArquivo)));
      int count = 0;
      while ((linha = bufferedReader.readLine()) != null) {

        // Cabecalho
        if (count == schema.getCabecalho()) {
          String[] conteudo = linha.split(delimitador);
          for (int i = 0; i < schema.getColunas().size(); i++) {
            // obtem a coluna
            String conteudoCelula = conteudo[i];

            // remove o jogo da velha se for a primeira coluna
            if (i == 0) {
              conteudoCelula = conteudoCelula.replaceAll("#", "");
            }

            // remove os caracteres especiais
            conteudoCelula = WebUtil.removerCaracteresEspeciais(conteudoCelula);

            // faz o append no builder
            linhaCabecalho.append(conteudoCelula).append(delimitador);
          }
          bufferedWriter.write(linhaCabecalho.toString());
          bufferedWriter.newLine();
        } else if (count > schema.getInicia()) { // Conteudo
          final StringBuilder linhaConteudo = new StringBuilder();
          String[] conteudo = linha.split(delimitador);          
          schema
                  .getColunas()
                  .stream()
                  .map(coluna -> conteudo[coluna.getNumero()] + delimitador)
                  .forEach(linhaConteudo::append);
          byte[] utf8 = linhaConteudo.toString().getBytes("UTF-8");
          bufferedWriter.write(new String(utf8, "UTF-8"));
          bufferedWriter.newLine();
        }
        count++;
      }

      bufferedWriter.flush();
      bufferedWriter.close();

      writer.close();
    } catch (FileNotFoundException excecao) {
      LOG.log(Level.SEVERE, "Erro ao ler o arquivo", excecao);
      throw new FileNotFoundException(excecao.getMessage());
    } catch (IOException excecao) {
      LOG.log(Level.SEVERE, "Erro ao ler o arquivo", excecao);
      throw new FileNotFoundException(excecao.getMessage());
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException excecao) {
          LOG.log(Level.SEVERE, "Erro ao ler o arquivo", excecao);
        }
      }
    }
  }

}
