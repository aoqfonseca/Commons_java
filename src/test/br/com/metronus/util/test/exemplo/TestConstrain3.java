package br.com.metronus.util.test.exemplo;

import br.com.metronus.util.rules.Constraint;
import br.com.metronus.util.rules.Context;

/**
 * @author Andre Fonseca
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestConstrain3 implements Constraint {

    /* (non-Javadoc)
     * @see br.com.metronus.util.rules.Constraint#check(br.com.metronus.util.rules.Context)
     */
    public boolean check(Context ctx) {
        String nome = (String) ctx.getAttribute("tipo_teste");
        return (nome.equalsIgnoreCase("cara"));
    }

}
