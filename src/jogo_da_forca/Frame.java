/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo_da_forca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Frame extends JFrame {
	
        private static final long serialVersionUID = 2L;
        private JComboBox comboBox;
        private boolean inicio = false;
        private String lista = "Dicionário de português";
        private int erros = 0;
        private JButton button = null;
        private JLabel labelErros = null;
        private JLabel maximoErros = null;
        private JLabel palavraChave = null;
        private JTextField textField = null;
        private JLabel informarLetra = null;
        private JLabel notFound = null;
        private List<String> letras = null;
        
        
        public Frame() {
                        
            inicializar();            
        }
        
        private void definirLista(ActionEvent evt) {
            lista = comboBox.getModel().getSelectedItem().toString();            
        }
        
        private void iniciar(ActionEvent evt) {
            
            this.getContentPane().removeAll();
            inicializar();
                        
            new Jogo(this);
            labelErros.setVisible(true);
            maximoErros.setVisible(true);
            textField.setVisible(true);
            informarLetra.setVisible(true);
            notFound.setVisible(true);
        }
                      
        
        public String getLista() {
            return lista;
        }
        
        public boolean getInicio() {
            return inicio;
        }
        
        public int getErros() {
            return erros;
        }
        
        public JComboBox getCombo(){
            return comboBox;
        }
        
        public JTextField getTextField() {
            return textField;
        }
        
        public void setTextButton(String texto) {
            button.setText(texto);
        }
                       
        public void setInicio(boolean inicio) {
            this.inicio = inicio;
        }
        
        public void setErros(){
            erros++;
        }
        
        public void setLabelErros(){
            labelErros.setText("Total de erros: "+erros);
        }
        
        public void setPalavraChave(String palavra){
            palavraChave.setText(palavra);
        }
        
        public boolean setNotFound(String palavra){
            
            if(!letras.contains(palavra)) {
                letras.add(palavra);
                notFound.setText("Letras não encontradas: "+letras);
                return true;    
            }
            return false;
        }        
        
                
        private void inicializar(){
            
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(1300,800);
            this.setTitle("Jogo da forca");
            
            JPanel contentPane = (JPanel) this.getContentPane();
            contentPane.setLayout(null);
       
            contentPane.setBackground(Color.WHITE);
            this.setContentPane(contentPane);
                
            this.setVisible(true);
                       
            letras = new ArrayList<>();
            
            comboBox = new JComboBox();
            comboBox.setModel(new DefaultComboBoxModel(new String[]{
                "Dicionário de português", "Dicionário de inglês", "Nome de pessoas", "Times de futebol"
            }));
            comboBox.addActionListener((ActionEvent evt) -> {
                definirLista(evt);
            });
            contentPane.add(comboBox);  
            comboBox.setBounds(180, 0, 180, 30);
            comboBox.setSelectedItem(lista);
            
            
            button = new JButton("Iniciar");                         
            contentPane.add(button);
            button.setBounds(400, 0, 100, 30);    
            button.addActionListener((ActionEvent evt) -> {
              iniciar(evt);
            });    
           
            Font f = new Font("Bitstream Charter Bold Italic", Font.BOLD, 20);

            JLabel label = new JLabel("Listas de palavras:");
            label.setFont(f);
            contentPane.add(label);
            label.setBounds(0, 0, 200, 30);

            erros = 0;
            labelErros = new JLabel("Total de erros: "+erros);
            labelErros.setFont(f);
            contentPane.add(labelErros);
            labelErros.setBounds(550, 0, 170, 30);
            labelErros.setVisible(false);
                        
            maximoErros = new JLabel("Erros permitidos: 5");
            maximoErros.setFont(f);
            contentPane.add(maximoErros);
            maximoErros.setBounds(750, 0, 200, 30);
            maximoErros.setVisible(false);
                        
            palavraChave = new JLabel();
            f = new Font("Bitstream Charter Bold Italic", Font.BOLD, 40);
            palavraChave.setFont(f);
            contentPane.add(palavraChave);
            palavraChave.setBounds(400, 300, 700, 100);    
        
            textField = new JTextField();
            textField.setFont(f);
            contentPane.add(textField);
            textField.setBounds(400, 200, 100, 50);            
            textField.setVisible(false);
            textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (textField.getText().length() > 0 ) e.consume();
                }
            });             
            
            informarLetra = new JLabel("Digite uma letra: ");
            informarLetra.setFont(f);
            contentPane.add(informarLetra);
            informarLetra.setBounds(75, 200, 400, 50);
            informarLetra.setVisible(false);
                       
            notFound = new JLabel("Letras não encontradas: ");
            f = new Font("Bitstream Charter Bold Italic", Font.BOLD, 30);
            notFound.setFont(f);
            contentPane.add(notFound);
            notFound.setBounds(75, 100, 1200, 50);
            notFound.setVisible(false);
        }
        
        public void gameOver(String palavra){
            
            Font f = new Font("Bitstream Charter Bold Italic", Font.BOLD, 50);
            informarLetra.setFont(f);
            informarLetra.setBounds(75, 200, 600, 60);
            if(erros > 5) {
                informarLetra.setText("Fim de jogo! Você perdeu!");                
                JOptionPane.showMessageDialog(null, "Palavra sorteada: "+palavra);                
            }
            
            else informarLetra.setText("Parabéns! Você venceu!");            
                    
        }        
        
}
