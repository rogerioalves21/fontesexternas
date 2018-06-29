package br.com.sicoob.gesic.fontesexternas.extracao.agenciasbancarias;

import br.com.sicoob.gesic.fontesexternas.extracao.AbstractBuilder;
import br.com.sicoob.gesic.fontesexternas.parser.agenciasbancarias.AgenciasBancariasParser;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import com.gargoylesoftware.htmlunit.WebResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.OperationNotSupportedException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Faz o download do arquivo de "Agencias Bancarias".
 *
 * @author Rogerio Alves Rodrigues
 */
public class Builder extends AbstractBuilder {
  
  private static final SicoobLoggerPadrao LOG = SicoobLoggerPadrao.getInstance(Builder.class);
    
  /**
   * Construtor.
   */
  public Builder() {
    super(new AgenciasBancariasParser());
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
    Elements options = doc.select("select[id=Agencias]");
    for (Element option : options) {
      Element opcaoExcel = option.getAllElements().get(PRIMEIRO_ITEM_OPTION);
      if (opcaoExcel.tagName().equals("option")) {
        String hostname = obterHostname(getPaginaInicial().getUrl());
        String urlArquivo = String.format("%s%s", hostname, opcaoExcel.attr("value"));
        Logger.getLogger(Builder.class.getName()).info(">> ".concat(urlArquivo));
        nomeArquivo = opcaoExcel.attr("value").replaceAll("(.*(.*)/)", "");
        try {
          downloadArquivo(urlArquivo);
          descompactar(nomeArquivo);
        } catch (IOException excecao) {
          LOG.alerta(excecao, "Erro ao obter o arquivo");
          Logger.getLogger(Builder.class.getName()).log(Level.SEVERE, "Erro ao obter o arquivo", excecao);
          throw new OperationNotSupportedException("Erro ao obter o arquivo");
        }
      }
    }
    return nomeArquivo;
  }
  
}
