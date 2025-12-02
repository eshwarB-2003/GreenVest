package com.greenvest.service;

import com.greenvest.rules.Rule;
import com.greenvest.rules.RuleContext;

import java.util.ArrayList;
import java.util.List;

public class RuleEngineService {

    private final List<Rule> rules = new ArrayList<>();

    public void registerRule(Rule rule) {
        rules.add(rule);
    }

    public void executeAll(RuleContext context) {
        for (Rule rule : rules) {
            rule.apply(context);
        }
    }
}
