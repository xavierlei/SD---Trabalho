/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.io.PrintWriter;

/**
 *
 * @author carlosmorais
 */
public class Thread_TrataPedido implements Runnable {
    private Armazem armazem;
    private PrintWriter out;
    private String pedido;

    public Thread_TrataPedido(Armazem armazem, PrintWriter out, String pedido) {
        this.armazem = armazem;
        this.out = out;
        this.pedido = pedido;
    }
    
    public void run(){
        String resposta = "o pedido ** " +pedido+ " ** foi recebido e tratado";
        /* como é feito o pedido? String?
            então, parser no pedido, com o switch é escolhido o metodo a executar no Armazem?
        */
        /*executa
            dependendo do metodo executado, é criada a String resposta e enviada para o Cliente
        */        
        //envia resposta para o Cliente          
        
        out.println(resposta);
        out.flush();
    }
    
    
}
