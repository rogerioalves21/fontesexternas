package br.com.sicoob.gesic.fontesexternas.recursos;

import br.com.sicoob.gesic.fontesexternas.anotacoes.Extratora;
import br.com.sicoob.gesic.fontesexternas.anotacoes.TipoExtratora;
import br.com.sicoob.gesic.fontesexternas.extracao.Extrator;
import br.com.sicoob.gesic.fontesexternas.model.Mensagem;
import br.com.sicoob.gesic.fontesexternas.servicos.FontesExternasServico;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import javax.inject.Inject;
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Endpoints disponibilizados.
 *
 * @author Rogerio Alves Rodrigues
 */
@Path("/")
public class FontesExternasApi {

  @Inject
  private FontesExternasServico servico;

  @Inject
  @Extratora(tipo = TipoExtratora.SCNCC)
  private Extrator snccExtrator;

  @Inject
  @Extratora(tipo = TipoExtratora.AGENCIAS_BANCARIAS)
  private Extrator agenciasExtrator;

  @Inject
  @Extratora(tipo = TipoExtratora.POSTOS_ATENDIMENTO)
  private Extrator postosAtendimentoExtrator;

  @Inject
  @Extratora(tipo = TipoExtratora.BALANCETES)
  private Extrator balancetesExtrator;
  
  @Inject
  @Extratora(tipo = TipoExtratora.IBGE)
  private Extrator ibgeExtrator;

  private static final SicoobLoggerPadrao LOG = SicoobLoggerPadrao.getInstance(FontesExternasApi.class);

  /**
   * Acessa o serviço de de portfólios migrados para instituições.
   *
   * @return Mensagem.
   */
  @GET
  @Path("/extrair")
  @Produces("application/json")
  public Mensagem obterStatusCop() {
    Mensagem mensagem = new Mensagem();
    try {
      extrairSNCC();
      extrairAgenciasBancarias();
      extrairPostosAtendimento();
      extrairBalancetes();
      extrairIbge();
      mensagem.setCode("200");
      mensagem.setDescricao("Sucesso!");
    } catch (OperationNotSupportedException excecao) {
      LOG.alerta(excecao, "Erro ao realizar a operacao!");
      mensagem.setCode("500");
      mensagem.setDescricao("Erro: ".concat(excecao.getMessage()));
    }
    return mensagem;
  }

  /**
   * Obtem o arquivo de Cooperativas de Credito do site do Banco Central, converte
   * para csv e envia para o HDFS.
   *
   * @throws OperationNotSupportedException Erro ao obter o arquivo.
   */
  private void extrairSNCC() throws OperationNotSupportedException {
    // zip
    String nomeArquivoZip = snccExtrator.extrair();
    //servico.salvarArquivoHDFS(nomeArquivoZip);

    // excel
    String nomeArquivoExcel = nomeArquivoZip.replaceAll(".zip", ".xls");

    // csv
    String nomeArquivoCsv = nomeArquivoExcel.replaceAll(".xls", ".csv");
    //servico.salvarArquivoHDFS(nomeArquivoCsv.replaceAll(".csv", "_Parse.csv"));
  }

  /**
   * Obtem o arquivo de Agencias Bancarias do site do Banco Central, converte para
   * csv e envia para o HDFS.
   *
   * @throws OperationNotSupportedException Erro ao obter o arquivo.
   */
  private void extrairAgenciasBancarias() throws OperationNotSupportedException {
    // zip
    String nomeArquivoZip = agenciasExtrator.extrair();
    //servico.salvarArquivoHDFS(nomeArquivoZip);

    // excel
    String nomeArquivoExcel = nomeArquivoZip.replaceAll(".zip", ".xls");

    // csv
    String nomeArquivoCsv = nomeArquivoExcel.replaceAll(".xls", ".csv");
    //servico.salvarArquivoHDFS(nomeArquivoCsv.replaceAll(".csv", "_Parse.csv"));
  }

  /**
   * Obtem o arquivo de Postos de Atendimento do site do Banco Central, converte para
   * csv e envia para o HDFS.
   *
   * @throws OperationNotSupportedException Erro ao obter o arquivo.
   */
  private void extrairPostosAtendimento() throws OperationNotSupportedException {
    // zip
    String nomeArquivoZip = postosAtendimentoExtrator.extrair();
    //servico.salvarArquivoHDFS(nomeArquivoZip);

    // excel
    String nomeArquivoExcel = nomeArquivoZip.replaceAll(".zip", ".xls");

    // csv
    String nomeArquivoCsv = nomeArquivoExcel.replaceAll(".xls", ".csv");
    //servico.salvarArquivoHDFS(nomeArquivoCsv.replaceAll(".csv", "_Parse.csv"));
  }

  /**
   * Obtem o arquivo de Balancetes e balancos patrimoniais do site do Banco Central, converte para
   * csv e envia para o HDFS.
   *
   * @throws OperationNotSupportedException Erro ao obter o arquivo.
   */
  private void extrairBalancetes() throws OperationNotSupportedException {
    // zip
    String nomeArquivoZip = balancetesExtrator.extrair();
    //servico.salvarArquivoHDFS(nomeArquivoZip.toUpperCase().replaceAll(".ZIP", "_Balancetes.zip"));

    // csv
    String nomeArquivoCsv = nomeArquivoZip.toUpperCase().replaceAll(".ZIP", ".csv");
    //servico.salvarArquivoHDFS(nomeArquivoCsv.replaceAll(".csv", "_Balancetes_Parse.csv"));
  }
  
  /**
   * Obtem o arquivo de Balancetes e balancos patrimoniais do site do Banco Central, converte para
   * csv e envia para o HDFS.
   *
   * @throws OperationNotSupportedException Erro ao obter o arquivo.
   */
  private void extrairIbge() throws OperationNotSupportedException {
    // zip
    String nomeArquivoZip = ibgeExtrator.extrair();
    //servico.salvarArquivoHDFS(nomeArquivoZip.toUpperCase().replaceAll(".ZIP", "_Balancetes.zip"));

    // csv
    String nomeArquivoCsv = nomeArquivoZip.toUpperCase().replaceAll(".ZIP", ".csv");
    //servico.salvarArquivoHDFS(nomeArquivoCsv.replaceAll(".csv", "_Balancetes_Parse.csv"));
  }

}