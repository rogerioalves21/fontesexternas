package br.com.sicoob.gesic.fontesexternas.configuracao;

import br.com.sicoob.gesic.fontesexternas.recursos.FontesExternasApi;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Define os resources disponiveis para a aplicacao.
 * 
 * @author Rogerio Alves Rodrigues
 */
@ApplicationPath("api")
public class FontesExternasApplication extends Application {

  /**
   * @return Os resources disponiveis.
   */
  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();
    classes.add(FontesExternasApi.class);
    return classes;
  }

}