package br.com.sicoob.gesic.fontesexternas.extracao;

import br.com.sicoob.gesic.fontesexternas.extracao.agenciasbancarias.Builder;
import br.com.sicoob.gesic.fontesexternas.hadoop.configuracao.Constantes;
import br.com.sicoob.gesic.fontesexternas.parser.FontesExternasParser;
import br.com.sicoob.gesic.fontesexternas.util.Descompactador;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.naming.OperationNotSupportedException;

/**
 * Define o comportamento de um builder de htmlunit.
 *
 * @author Rogerio Alves Rodrigues
 */
public abstract class AbstractBuilder {

  private final FontesExternasParser parser;
  private static final int BUFFER_SIZE = 4096;
  private static final int CONNECTION_TIMEOUT = 60 * 1000;
  private final WebClient webClient;
  private HtmlPage paginaInicial;
  private static final SicoobLoggerPadrao LOG = SicoobLoggerPadrao.getInstance(AbstractBuilder.class);
  private final Descompactador descompactador = new Descompactador();
  protected static final int PRIMEIRO_ITEM_OPTION = 1;

  /**
   * Construtor.
   *
   * @param parser Implementacao do conversor.
   */
  public AbstractBuilder(FontesExternasParser parser) {
    this.webClient = new WebClient();
    this.parser = parser;
  }

  /**
   * @return A pagina inicial.
   */
  protected HtmlPage getPaginaInicial() {
    return this.paginaInicial;
  }

  /**
   * Obtem o arquivo do url.
   *
   * @return Nome do arquivo obtido.
   * @throws OperationNotSupportedException Erro ao realizar a operacao.
   */
  public abstract String extrairArquivo() throws OperationNotSupportedException;

  /**
   * Metodo responsavel por acessar a url apos configuracao do webclient.
   *
   * @param url url a ser acessada.
   * @return Builder.
   * @throws OperationNotSupportedException Operacao nao suportada.
   */
  public AbstractBuilder acessarURL(String url) throws OperationNotSupportedException {
    try {
      this.paginaInicial = this.webClient.getPage(url);
    } catch (IOException | FailingHttpStatusCodeException excecao) {
      LOG.alerta(excecao, "A pagina ".concat(url).concat(" nao esta accessivel!"));
      throw new OperationNotSupportedException("A pagina ".concat(url).concat(" nao esta accessivel!"));
    }
    return this;
  }

  /**
   * Metodo responsavel por configura
   *
   * @return Builder.
   */
  public AbstractBuilder configurarWebClient() {
    this.webClient.addRequestHeader("User-Agent", RandomUserAgent.getRandomUserAgent());
    this.webClient.addRequestHeader("Connection", "keep-alive");
    this.webClient.addRequestHeader("Accept-Encoding", "gzip, deflate");
    this.webClient.getOptions().setTimeout(CONNECTION_TIMEOUT);
    this.webClient.getOptions().setJavaScriptEnabled(false);
    this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    this.webClient.getOptions().setCssEnabled(false);
    return this;
  }

  /**
   * Realiza o download do arquivo.
   *
   * @param fileURL Url do arquivo.
   * @throws IOException Erro ao realizar o download.
   */
  protected void downloadArquivo(String fileURL) throws IOException {
    URL url = new URL(fileURL);
    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
    int responseCode = httpConn.getResponseCode();

    // verifica se a resposta esta ok
    if (responseCode == HttpURLConnection.HTTP_OK) {
      String fileName;
      String contentType = httpConn.getContentType();
      int contentLength = httpConn.getContentLength();

      // extrai o nome do arquivo da url
      fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());

      LOG.info("Content-Type = ".concat(contentType));
      LOG.info("Content-Length = ".concat(contentLength + ""));
      LOG.info("fileName = ".concat(fileName));

      // abre o inputstream da conexao http
      try (InputStream inputStream = httpConn.getInputStream()) {
        String saveFilePath = Constantes.PASTA_INPUT_TESTE.getValor().concat(File.separator + fileName);

        // abre o outputstream do arquivo salvo
        try (FileOutputStream outputStream = new FileOutputStream(saveFilePath)) {
          int bytesRead = -1;
          byte[] buffer = new byte[BUFFER_SIZE];
          while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
          }
        }
      }
      LOG.debug("Download feito com sucesso!");
    } else {
      LOG.debug("No file to download. Server replied HTTP code: ".concat(responseCode + ""));
      throw new IOException("O servidor HTTP nao responde, HTTP code: ".concat(responseCode + ""));
    }
    httpConn.disconnect();
  }

  /**
   * Realiza o download do arquivo.
   *
   * @param fileURL Url do arquivo.
   * @param sufixo  Sufixo para o nome do arquivo.
   * @throws IOException Erro ao realizar o download.
   */
  protected void downloadArquivo(String fileURL, String sufixo) throws IOException {
    URL url = new URL(fileURL);
    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
    int responseCode = httpConn.getResponseCode();

    // verifica se a resposta esta ok
    if (responseCode == HttpURLConnection.HTTP_OK) {
      String fileName;
      String contentType = httpConn.getContentType();
      int contentLength = httpConn.getContentLength();

      // extrai o nome do arquivo da url
      fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());

      LOG.info("Content-Type = ".concat(contentType));
      LOG.info("Content-Length = ".concat(contentLength + ""));
      LOG.info("fileName = ".concat(fileName));

      // abre o inputstream da conexao http
      try (InputStream inputStream = httpConn.getInputStream()) {
        String saveFilePath = Constantes.PASTA_INPUT_TESTE.getValor().concat(File.separator + fileName.replaceAll(".ZIP", sufixo));

        // abre o outputstream do arquivo salvo
        try (FileOutputStream outputStream = new FileOutputStream(saveFilePath)) {
          int bytesRead = -1;
          byte[] buffer = new byte[BUFFER_SIZE];
          while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
          }
        }
      }
      LOG.info("Download feito com sucesso!");
    } else {
      LOG.info("No file to download. Server replied HTTP code: ".concat(responseCode + ""));
      throw new IOException("O servidor HTTP nao responde, HTTP code: ".concat(responseCode + ""));
    }
    httpConn.disconnect();
  }

  /**
   * Descompacta o arquivo zip.
   *
   * @param nomeArquivo Nome do arquivo zipado.
   * @throws java.io.IOException Erro ao converter o arquivo.
   */
  protected void descompactar(String nomeArquivo) throws IOException {
    this.descompactador.descompactar(Constantes.PASTA_INPUT_TESTE.getValor().concat(nomeArquivo),
            Constantes.PASTA_INPUT_TESTE.getValor(), StandardCharsets.ISO_8859_1);
    converterParaCsv(nomeArquivo);
  }

  /**
   * Descompacta o arquivo zip.
   *
   * @param nomeArquivo Nome do arquivo zipado.
   * @param sufixo      Sufixo para o arquivo csv.
   * @throws java.io.IOException Erro ao converter o arquivo.
   */
  protected void descompactar(String nomeArquivo, String sufixo) throws IOException {
    this.descompactador.descompactar(Constantes.PASTA_INPUT_TESTE.getValor().concat(nomeArquivo),
            Constantes.PASTA_INPUT_TESTE.getValor(), sufixo);
    converterParaCsv(nomeArquivo);
  }

  /**
   * Converte o arquivo para csv.
   *
   * @param nomeArquivo Nome do arquivo excel.
   * @throws java.io.IOException Erro ao converter o arquivo.
   */
  protected void converterParaCsv(String nomeArquivo) throws IOException {
    this.parser.executar(nomeArquivo);
  }

  /**
   * Metodo responsavel por obter o hostname formatado
   *
   * @param url url para se obter o hostname
   * @return String hostname formatado.
   */
  protected String obterHostname(URL url) {
    return String.format("%s://%s", url.getProtocol(), url.getHost());
  }
}
