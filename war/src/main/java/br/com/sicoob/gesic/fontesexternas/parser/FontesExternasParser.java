package br.com.sicoob.gesic.fontesexternas.parser;

import java.io.FileNotFoundException;

/**
 * Define o comportamento de um parser para o fontes externas.
 * 
 * @author Rogerio Alves Rodrigues
 */
public interface FontesExternasParser {

    /**
     * Faz o parse do arquivo.
     * 
     * @param nomeArquivo Nome do arquivo a ser transformado.
     * @throws FileNotFoundException Schema nao encontrado.
     */
    void executar(String nomeArquivo) throws FileNotFoundException;

}