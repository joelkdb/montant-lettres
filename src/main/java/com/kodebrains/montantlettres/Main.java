/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodebrains.montantlettres;

import java.math.BigDecimal;

/**
 *
 * @author joelkdb
 */
public class Main {

    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MontantLettre ml = new MontantLettre();
        BigDecimal[] montants = {new BigDecimal("73000000"), new BigDecimal("11"), new BigDecimal("16"),
            new BigDecimal("72"), new BigDecimal("76"), new BigDecimal("77"), new BigDecimal("91"),
            new BigDecimal("95"), new BigDecimal("90"), new BigDecimal("99"), new BigDecimal("71000.00"),
            new BigDecimal("74000"), new BigDecimal("93000"), new BigDecimal("123456789"),
            new BigDecimal("103456789"), new BigDecimal("100456789"), new BigDecimal("123406700"),
            new BigDecimal("123406000"), new BigDecimal("123000000"), new BigDecimal("12345000000"),
            new BigDecimal("17345000010"), new BigDecimal("2940992.00"), null, new BigDecimal("0000000")};
        ml.setMontant(montants[0]);
//        System.out.println("1 => " + ml.getNotDecimalAmountLetter());
    }

}
