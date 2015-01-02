/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosmorais
 */
public class Thread_TrataPedido implements Runnable {
    private Armazem armazem;
    private PrintWriter out;
    private String pedido;
    private String userID;

    public Thread_TrataPedido(Armazem armazem, String userID, PrintWriter out, String pedido) {
        this.armazem = armazem;
        this.out = out;
        this.pedido = pedido;
        this.userID = userID;        
    }
       /*) 
    public Thread_TrataPedido(Armazem armazem, String userID, System out, String pedido) {
        this.armazem = armazem;
        this.out = (PrintWriter;
        this.pedido = pedido;
        this.userID = userID;
    }*/
    
    public TipoTarefa constroiTarefa(String lista[]) {
        
        TreeMap<String,Integer> pedidos = new TreeMap<>();
        String id = lista[1];
        
        for(int i=2; i<lista.length; ){
            String nome = lista[i];
            i++;
            int qtd = Integer.parseInt(lista[i]);
            pedidos.put(nome, qtd);
            i++;
        }
        return (new TipoTarefa(id, pedidos));
    }
    
    public void run(){
        String resposta=null;
        System.out.println("o pedido ** " +pedido+ " ** foi recebido e tratado");
        String lista[] = pedido.split(":");
        int ie = 0;
        for(String s : lista){
            lista[ie] = comeEspaco(s);
            ie++;
        }
        System.out.print("PEDIDO=");
        for(String s : lista){
            System.out.print(s+":");
        }
        System.out.println(";");
        switch (lista[0]){
            case "abastece": 
                try{
                    this.armazem.abastece(lista[1], Integer.parseInt(lista[2]));
                    resposta = "abastecido!";
                }
                catch(IndexOutOfBoundsException e){
                    resposta = "comando errado: faltam argumentos";
                }
                catch(NumberFormatException e){
                    resposta = "comando errado: argumento não numerico";
                }
                break;
            
            case "executa": 
        {
            try {
                resposta = this.armazem.executaTarefa(lista[1],userID);
                resposta=  "executa a tarefa "+lista[1]+" como o id "+resposta;
            } catch (FerramentaException | TarefaException ex) {
                resposta = ex.getMessage();
            }
        }                
                break;
            
            case "adiciona": 
                try{
                    TipoTarefa t = constroiTarefa(lista);
                    this.armazem.addTarefa(t);
                    resposta = "controi a tarefa " + lista[1];
                }
                catch(IndexOutOfBoundsException e){
                    System.out.println(e.toString());
                    resposta = "comando errado: faltam argumentos";
                }
                catch(NumberFormatException e){
                    System.out.println(e.toString());
                    resposta = "comando errado: argumento não numerico";
                }
                catch(TarefaException ex){
                    resposta = ex.getMessage();
                }
                break;
            
            case "notifica": 
                List<String> ids = new ArrayList<String>();
                for(int i=1; i<lista.length; i++)
                    ids.add(lista[i]);
                    
        {
            try {
                this.armazem.notifica(ids);
            } catch (InterruptedException ex) {
                Logger.getLogger(Thread_TrataPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                resposta = "as tarefas ";
                for(String aux : ids)
                    resposta = resposta +aux+", ";
                resposta += "foram terminadas.";
                break;  
                    
            case "listagem":
                resposta = this.armazem.listagemTarefas();
                break;
            
            case "termina":   
        {
            try {
                this.armazem.terminaTarefa(lista[1], userID);
                resposta = "termina a tarefa " + lista[1];
            } catch (TarefaException ex) {
                resposta = ex.getMessage();
            }
        }                
                break;          
            
            case "print": 
                resposta = this.armazem.toString();
                break;        
            
            default:
                resposta = "comando errado!";
                break;                
        }
                     
        //envia resposta ao Cliente
        out.println(resposta);
        out.flush();
    }
    
    public String comeEspaco(String s){
        int tam = s.length();
        char [] st = s.toCharArray();
        char res [] = new char[tam+1];
        String resposta = "";
        int i,j;
        i=0;
        j=0;
        while(j < tam && st[j]!=' '){
            res[i] = st[j];
            i++;
            j++;
        }
        
        for(; j < tam; j++){
                if(i > 0){
                        if(res[i-1] == ' ' && st[j] == ' ');
                    else if(j == tam-1 && st[j] == ' ');
                    else{
                        res[i] = st[j];
                        i++;
                    }
                }
                else{
                    if(st[j]!=' '){
                        res[i] = st[j];
                        i++;
                    }
                }
        }
        
        if(res[i-1]==' ')
            i-=1;
        //else
            //res[i] ='\0';
        
        tam = i;
        for(i = 0; i < tam; i++)
            resposta = resposta +res[i];
        return resposta;
        //return Arrays.toString(res);
    }
    
    
}
