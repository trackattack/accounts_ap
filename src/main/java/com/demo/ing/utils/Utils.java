package com.demo.ing.utils;

import com.demo.ing.exceptions.IbanException;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Random;

public class Utils {
    public static boolean validateAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) == 1;
    }

    public static boolean validateIban(String iban) {
        if (StringUtils.isEmpty(iban)) {
            return false;
        }
        String ibanTrimmed = sanitizeIban(iban);
        return IBANCheckDigit.IBAN_CHECK_DIGIT.isValid(ibanTrimmed);
    }

    public static String sanitizeIban(String iban) {
        if (null != iban) {
            return iban.replaceAll("\\s+", "");
        }
        throw new IbanException("Iban is null");
    }

    public static String generateIban() {
        Iban iban = new Iban.Builder()
                .countryCode(CountryCode.RO)
                .buildRandom();
        return iban.toString();
    }
}
