package br.com.sicoob.gesic.fontesexternas.extracao.ibge;

import br.com.sicoob.gesic.fontesexternas.extracao.AbstractBuilder;
import br.com.sicoob.gesic.fontesexternas.parser.ibge.IBGEParser;
import java.net.URL;
import javax.naming.OperationNotSupportedException;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;

/**
 * Realiza o download do arquivo do "Produto interno bruto dos municipios".
 *
 * @author Rogerio Alves Rodrigues
 */
public class Builder extends AbstractBuilder {
  
  private String url;

  /**
   * Construtor.
   */
  public Builder() {
    super(new IBGEParser());
  }

  /**
   * Setter.
   *
   * @param url Url de download.
   */
  public Builder url(String url) {
    this.url = url;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String extrairArquivo() throws OperationNotSupportedException {
		try {
			// faz o download do arquivo
      downloadArquivoFtp(this.url);
		} catch(Exception excecao) {
      SicoobLoggerPadrao
        .getInstance(Builder.class)
        .alerta(
          excecao,
          "Erro ao realizar o download do arquivo!"
      );
		}
    
    return "";
  }

}
