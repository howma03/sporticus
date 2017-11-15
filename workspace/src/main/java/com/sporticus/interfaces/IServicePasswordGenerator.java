package com.sporticus.interfaces;

public interface IServicePasswordGenerator {
    public static enum STRENGTH {
        WEAK, MEDIUM, STRONG
    }

    public abstract String generate(STRENGTH strength);

    public abstract void setStrength(STRENGTH strength);

    public abstract STRENGTH getStrength();

    public abstract String generate();
}