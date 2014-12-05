/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

/**
 *
 * @author carlosmorais
 */
public interface InterfaceFerramenta {
    
    /*abastece a quantidade da ferramenta*/
    public void abastece(int qtd);
            
    /*reabastece a quantidade disponivel da ferramenta*/
    public void reabastece(int qtd);
}
