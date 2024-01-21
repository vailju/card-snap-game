package org.snap.game.match;

public class MatchConditionFactory {

    private MatchConditionFactory() {
    }

    public static MatchCondition create(String type) {
        MatchCondition matchCondition = getMatchCondition(type);
        System.out.println(matchCondition.getDisplayRule());
        return matchCondition;
    }

    private static MatchCondition getMatchCondition(String type) {
        if ("full".equalsIgnoreCase(type)) {
            return new FullMatchCondition();
        }
        if ("suit".equalsIgnoreCase(type)) {
            return new SuitMatchCondition();
        }
        return new ValueMatchCondition();
    }
}
