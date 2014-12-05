/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.util.HashMap;

/**
 *
 * @author xavier
 */
public class Gestor_de_tarefas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /* ********* algum código de teste para o Armazem ********** */        
        Armazem arm = new Armazem();
        
        //cria Ferramentas
        arm.abastece("chave",10);
        arm.abastece("prego",40);
        arm.abastece("martelo",13);
        arm.abastece("serrote",10);
        
        //abastece Ferramentas
        arm.abastece("chave",10);
        arm.abastece("martelo",7);    
        
        //cria pedidos
        HashMap<String, Integer> pedido1 = new HashMap<String, Integer>();
        pedido1.put("chave", 5);
        pedido1.put("martelo", 5);
        
        HashMap<String, Integer> pedido2 = new HashMap<String, Integer>();
        pedido2.put("chave", 3);
        
        HashMap<String, Integer> pedido3 = new HashMap<String, Integer>();
        pedido3.put("prego", 20);
        pedido3.put("martelo", 5);
        pedido3.put("chave",2);
        
        //cria e adiciona Tarefas ao Armazem
        arm.addTarefa(new Tarefa("1000",pedido1,"1"));
        arm.addTarefa(new Tarefa("1001",pedido2,"2"));
        arm.addTarefa(new Tarefa("1002",pedido3,"3"));
  
        //executa Tarefas
        arm.executaTarefa("1000");
        arm.executaTarefa("1001");
        arm.executaTarefa("1002");        
        
        //termina Tarefas
        arm.terminaTarefa("1000");
        arm.terminaTarefa("1001");
        arm.terminaTarefa("1002");
        
        //print para verificar o conteudo do Armazem
        System.out.println(arm.toString());        
    }
    
}
