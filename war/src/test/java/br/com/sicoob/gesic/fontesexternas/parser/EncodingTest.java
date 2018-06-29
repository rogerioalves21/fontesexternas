package br.com.sicoob.gesic.fontesexternas.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import com.google.common.base.Charsets;
import com.google.common.collect.LinkedHashMultimap;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.junit.Assert;
import org.junit.Test;

public class EncodingTest {

    private static final double MEG = (Math.pow(1024, 2));

    //@Test
    public void deveConverterParaAnsii() {
        try {
            // Convert from Unicode to UTF-8
            String string = "CR\u00C9DITO";
            byte[] utf8 = string.getBytes("UTF-8");

            // Convert from UTF-8 to Unicode
            string = new String(utf8, "UTF-8");
            Logger.getLogger(EncodingTest.class.getName()).info(string);
            Assert.assertEquals("CRÉDITO", string);
        } catch (UnsupportedEncodingException e) {
        }
    }

    //@Test
    public void deveConverterGuava() {
        try {
            // writer
            int tamanhoDoBuffer = (int) MEG;
            File arquivo = new File("C:\\Users\\rogerio.rodrigues\\hadoop\\input\\201804COOPERATIVAS_Outro_Parse.csv");
            arquivo.createNewFile();
            FileWriterWithEncoding writer = new FileWriterWithEncoding(arquivo, Charsets.UTF_8, Boolean.FALSE);
            BufferedWriter bufferedWriter = new BufferedWriter(writer, tamanhoDoBuffer);

            FileReader fr = new FileReader("C:\\Users\\rogerio.rodrigues\\hadoop\\input\\201804COOPERATIVAS_Parse.csv");
            BufferedReader br = new BufferedReader(fr);
            String linha;
            while ((linha = br.readLine()) != null) {
                byte[] utf8 = linha.getBytes("UTF-8");
                bufferedWriter.write(new String(utf8, "UTF-8"));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}