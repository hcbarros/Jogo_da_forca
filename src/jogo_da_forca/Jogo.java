/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo_da_forca;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.swing.JButton;
import org.json.JSONObject;


/**
 *
 * @author henrique
 */
public class Jogo {

    private Frame frame = null;
    private List<String> palavras = null;
    private String sorteada = "";
    private String palavraChave = "";
    private int lista = 0;    
    
    
    public Jogo(Frame meuframe) {
        
        frame = meuframe;
        frame.setTextButton("Reiniciar");
        palavras = new ArrayList<>();
        lista = frame.getCombo().getSelectedIndex();                             
               
        if(lista == 0) inicio("wordlist_pt_br"); 
        if(lista == 1) inicio("wordlist_en");
        if(lista == 2) inicio("first-name");
        if(lista == 3) inicio("brazilian-soccer-teams");        
    }
       
        
    private void verificar(ActionEvent evt, JButton button) {
        
        String text = frame.getTextField().getText().toUpperCase();
        if(text.length() == 0) return;
        frame.getTextField().setText("");
        char[] palavra = palavraChave.toCharArray();
        boolean find = false;
        
        for(int i = 0; i < sorteada.length(); i++) {
           
            if(sorteada.charAt(i) == text.charAt(0)) {
                palavra[i] = text.charAt(0);
                find = true;                    
            }                
        }
        
        palavraChave = new String(palavra);
        
        imprimirPalavra();
    
        if(palavraChave.equals(sorteada)) gameOver(button);
               
        if(!find) {
            find = frame.setNotFound(text);
            if(find) {
                frame.setErros();
                frame.setLabelErros();
                if(frame.getErros() > 5) gameOver(button);               
            }
        }
        
    }
    
   
    private void criarBotao(){
        
        focar();
        JButton button = new JButton("Verificar");
        Font f = new Font("Bitstream Charter Italic", Font.PLAIN, 25);
        button.setFont(f);
        frame.getContentPane().add(button);
        button.setBounds(520, 215, 140, 33);    
        button.addActionListener((ActionEvent evt) -> {
           verificar(evt, button);
           focar();
        });            
    }
    
    
    private void inicio(String caminho) {
                
        caminho = "src/listas/"+caminho+".txt";
        obterPalavras(caminho);
        
        Random random = new Random();
        int index = random.nextInt(palavras.size());
        sorteada = palavras.get(index);
        sorteada = sorteada.toUpperCase();
                      
        for(int i = 0; i < sorteada.length(); i++) {
            
            if(sorteada.charAt(i) == '-') palavraChave+= '-';
            else palavraChave+= '_';   
        }
        
        imprimirPalavra();
        
        criarBotao();
    }
    
    
    private void imprimirPalavra(){
                
        char[] p = palavraChave.toCharArray();
        String temp = "";
        for(int i = 0; i < sorteada.length(); i++) {
            
            if(p[i] == '_' && i > 0 && p[i - 1] == '_') 
                temp+= " _";
            else temp+= p[i];
        }        
        frame.setPalavraChave(temp);
    }
    

    private void obterPalavras(String caminho) {
        
        try {
              BufferedReader br = new BufferedReader(new FileReader(caminho));
              
              if(lista != 1) 
                  palavras = br.lines().collect(Collectors.toCollection(ArrayList<String>::new));
              
              else {
                StringBuilder sb = new StringBuilder();  
                String line = null;
                while ((line = br.readLine()) != null) {  
                  sb.append(line + "\n");  
                }
                  
                JSONObject jsonObject = new JSONObject(sb.toString());
                for (String key: jsonObject.keySet()) palavras.add(key);      
              }       
              
        } catch (IOException e) {}        
    }

    
    private void gameOver(JButton button) {
         
         button.setVisible(false);
         frame.getTextField().setVisible(false);
         frame.gameOver(sorteada);
    }
    
    
    private void focar(){
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() { 
            public void run() { 
                frame.getTextField().requestFocusInWindow(); 
            } 
        }); 
    }
       
}
