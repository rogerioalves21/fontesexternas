package br.com.sicoob.gesic.fontesexternas.hadoop;

import javax.enterprise.inject.Produces;

import br.com.sicoob.gesic.fontesexternas.anotacoes.OperacoesProducer;

/**
 * Produtor de operacoes de hadoop.
 * 
 * @author Rogerio Alves Rodrigues
 */
public class HadoopFactory {

    /**
     * Cria HDFS operador.
     * 
     * @return HDFS operador.
     */
    @Produces
    @OperacoesProducer
    public HadoopHDFSOperacoes criar() {
        return new HadoopHDFSOperacoesImpl();
    }

}