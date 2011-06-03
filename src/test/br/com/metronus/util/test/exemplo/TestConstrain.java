package br.com.neoris.util.test.exemplo;

import br.com.neoris.util.rules.Constraint;
import br.com.neoris.util.rules.Context;

/**
 * @author Andre Fonseca
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestConstrain implements Constraint {

    /* (non-Javadoc)
     * @see br.com.neoris.util.rules.Constraint#check(br.com.neoris.util.rules.Context)
     */
    public boolean check(Context ctx){
        String nome = (String) ctx.getAttribute("nome_teste");
        return (nome.equalsIgnoreCase("Andre"));
    }

}
