package br.com.sicoob.gesic.fontesexternas.servicos;

import java.io.InputStream;

import javax.ejb.Local;
import javax.naming.OperationNotSupportedException;

/**
 * Disponibiliza os servicos de fontes externas.
 * 
 * Gravacao no HDFS.
 * 
 * @author Rogerio Alves Rodrigues
 */
@Local
public interface FontesExternasServico {

    /**
     * Salva o arquivo no HDFS.
     * 
     * @param nomeArquivoLocal Path do arquivo local.
     */
    void salvarArquivoHDFS(String nomeArquivoLocal) throws OperationNotSupportedException;

    /**
     * Salva o arquivo em pasta local.
     * 
     * @param inputStream Bytes.
     */
    void salvarArquivoLocal(InputStream inputStream);

}