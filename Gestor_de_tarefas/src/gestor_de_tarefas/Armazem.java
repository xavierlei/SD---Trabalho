/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xavier
 */
public class Armazem implements InterfaceArmazem {
    private Map<String,Ferramentas> ferramentas;
    private Map<String,Tarefas> tarefas;
    
    public Armazem(){
        this.ferramentas = new HashMap<String,Ferramenta>();
        this.tarefas = new HashMap<String,Tarefas>();
    }

    @Override
    public List<String> tarefasActivas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifications(List<String> tarefas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void abastece(String ferramenta, int quant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTarefa(Tarefa t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void executaTarefa(String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tarefa getTarefa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
