package com.greenvest.patterns.strategy;

public interface RankingStrategy {

    int compare(String sellerIdA, String sellerIdB);
}
