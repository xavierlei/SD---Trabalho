/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor_de_tarefas;

/**
 *
 * @author xavier
 */
public class TarefaException extends Exception {

    /**
     * Creates a new instance of <code>TarefaException</code> without detail
     * message.
     */
    public TarefaException() {
    }

    /**
     * Constructs an instance of <code>TarefaException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public TarefaException(String msg) {
        super(msg);
    }
}
