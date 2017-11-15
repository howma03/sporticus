package com.sporticus.services;

import com.sporticus.interfaces.IServicePasswordGenerator;
import org.springframework.stereotype.Service;

import java.util.Random;

/***
 * Provides function to generate a password with a given strength
 *
 * Strength dictates which chars and number of chars to use
 *
 * WEAK - just lower case characters (7 long)
 *
 * MEDIUM - lower(4), upper(2) and number(2) characters
 *
 * STRONG - lower(4), upper(2), number(2) and special chars(1)
 */
@Service
public class ServicePasswordGeneratorImplSimple implements IServicePasswordGenerator {

    public static final String ALPHA_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALPHA_LOWER = "abcdefghijklmnopqrstuwxyz";
    public static final String NUMBERS = "0123456789";
    public static final String CHARS = "!£$=^-*";

    private static Random rnd = new Random();

    private STRENGTH strength = STRENGTH.STRONG;

    public ServicePasswordGeneratorImplSimple() {

    }

    public static void main(final String[] args) {
        final ServicePasswordGeneratorImplSimple passwordGenerator = new ServicePasswordGeneratorImplSimple();
        for (int i = 10; i > 0; i--) {
            System.out.println(passwordGenerator.generate(STRENGTH.STRONG));
        }
    }

    private String randomString(final int alphaUpperCount, final int alphaLowerCount, final int numberCount, final int charCount) {
        final StringBuilder sb = new StringBuilder(alphaUpperCount + alphaLowerCount + numberCount + charCount);

        for (int i = 0; i < alphaUpperCount; i++) {
            sb.append(ALPHA_UPPER.charAt(rnd.nextInt(ALPHA_UPPER.length())));
        }

        for (int i = 0; i < alphaLowerCount; i++) {
            sb.append(ALPHA_LOWER.charAt(rnd.nextInt(ALPHA_LOWER.length())));
        }

        for (int i = 0; i < numberCount; i++) {
            sb.append(NUMBERS.charAt(rnd.nextInt(NUMBERS.length())));
        }

        for (int i = 0; i < charCount; i++) {
            sb.append(CHARS.charAt(rnd.nextInt(CHARS.length())));
        }

        return sb.toString();
    }

    @Override
    public String generate(final STRENGTH strength) {
        switch (strength) {
            case WEAK:
                break;
            case MEDIUM:
                return randomString(2, 4, 2, 0);
            case STRONG:
                return randomString(2, 4, 2, 1);
        }
        return randomString(0, 7, 0, 0);
    }

    @Override
    public STRENGTH getStrength() {
        return this.strength;
    }

    @Override
    public void setStrength(final STRENGTH strength) {
        this.strength = strength;
    }

    @Override
    public String generate() {
        return generate(this.strength);
    }
}
