package com.greenvest.rules;

public interface Rule {

    String getName();

    void apply(RuleContext context);
}
