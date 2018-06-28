package br.com.sicoob.gesic.fontesexternas.hadoop;

import javax.naming.OperationNotSupportedException;

/**
 * Comportamento de escrita, leitura e remocao de arquivo do HDFS.
 * 
 * @author Rogerio Alves Rodrigues
 */
public interface HadoopHDFSOperacoes {

    /**
     * Salva o arquivo com permissao read, write e execute na pasta determinada para
     * o projeto fontes externas.
     * 
     * @param nomeArquivoLocal Nome do arquivo local que sera salvo.
     * @throws OperationNotSupportedException Operacao nao suportada.
     */
    void escreverNaPasta(String nomeArquivoLocal) throws OperationNotSupportedException;

}