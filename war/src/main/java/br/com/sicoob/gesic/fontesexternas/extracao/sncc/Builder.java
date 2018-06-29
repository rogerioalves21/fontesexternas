package br.com.sicoob.gesic.fontesexternas.extracao.sncc;

import br.com.sicoob.gesic.fontesexternas.extracao.AbstractBuilder;
import br.com.sicoob.gesic.fontesexternas.parser.sncc.SNCCParser;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import com.gargoylesoftware.htmlunit.WebResponse;
import java.io.IOException;
import javax.naming.OperationNotSupportedException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Faz o download do arquivo de "Cooperativas de Credito".
 *
 * @author Filipe.Sousa
 * @author Rogerio Alves Rodrigues
 */
public class Builder extends AbstractBuilder {

  private static final SicoobLoggerPadrao LOG = SicoobLoggerPadrao.getInstance(Builder.class);

  /**
   * Construtor.
   */
  public Builder() {
    super(new SNCCParser());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String extrairArquivo() throws OperationNotSupportedException {
    String nomeArquivo = null;
    WebResponse response = getPaginaInicial().getWebResponse();
    String conteudo = response.getContentAsString();
    Document doc = Jsoup.parse(conteudo);
    Elements options = doc.select("select[id=Cooperativas]");
    for (Element option : options) {
      Element opcaoExcel = option.getAllElements().get(PRIMEIRO_ITEM_OPTION);
      if (opcaoExcel.tagName().equals("option")) {
        String hostname = obterHostname(getPaginaInicial().getUrl());
        String urlArquivo = String.format("%s%s", hostname, opcaoExcel.attr("value"));
        nomeArquivo = opcaoExcel.attr("value").replaceAll("(.*(.*)/)", "");
        try {
          downloadArquivo(urlArquivo);
          descompactar(nomeArquivo);
        } catch (IOException excecao) {
          LOG.alerta(excecao, "Erro ao obter o arquivo");
          throw new OperationNotSupportedException("Erro ao obter o arquivo");
        }
      }
    }
    return nomeArquivo;
  }

}
