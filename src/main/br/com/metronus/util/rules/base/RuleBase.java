package br.com.neoris.util.rules.base;

import java.util.Collection;

import br.com.neoris.util.rules.Rule;

/**
 * @author Andre Fonseca
 *
 * Interface que define o comportamento da implementações de bases de regras
 */
public interface RuleBase {

    public Collection getAllRules();
    public Rule getRule(Object key);
}
