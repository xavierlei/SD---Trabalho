package gestor_de_tarefas;


import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author filiperibeiro
 */
public interface InterfaceTarefa {
    public String getID();
    public Map<String,Integer> getPedidos();
    public String getUtilizador();
    public boolean getEstado();
    public void concluir();
}
