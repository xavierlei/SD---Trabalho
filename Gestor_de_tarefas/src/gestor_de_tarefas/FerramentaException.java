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
public class FerramentaException extends Exception {

    /**
     * Creates a new instance of <code>FerramentaException</code> without detail
     * message.
     */
    public FerramentaException() {
    }

    /**
     * Constructs an instance of <code>FerramentaException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FerramentaException(String msg) {
        super(msg);
    }
}
