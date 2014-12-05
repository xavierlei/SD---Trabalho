/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

import java.util.List;

/**
 *
 * @author xavier
 */
public interface InterfaceArmazem {
    public List<String>tarefasActivas();
    public void notifications(List<String> tarefas);
    public void abastece(String ferramenta, int quant);
    public void addTarefa(Tarefa t);
    public void executaTarefa(String t);
    public Tarefa getTarefa(String tarefa);
}
