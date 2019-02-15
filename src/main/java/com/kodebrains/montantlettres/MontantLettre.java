/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodebrains.montantlettres;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe implémentant la logique métier de l'écriture d'un montant en lettres.
 *
 * @author joelkdb
 */
public class MontantLettre implements Serializable {

    private BigDecimal montant;
    Map<Integer, String> niveaux = new HashMap();
    Map<String, String> unites = new HashMap();
    Map<String, String> dizaines = new HashMap();
    Map<String, String> elements = new HashMap();

    /**
     * Constructeur par défaut.
     */
    public MontantLettre() {
        this(BigDecimal.ZERO);
    }

    /**
     * Constructeur prenant en paramètre le montant à transformer en lettres.
     *
     * @param montant : Le montant à écrire en lettres.<br>Attention au format :
     * la virgule est spécifié par un ".".<br><strong>Exemple (pour les montants
     * à virgule) : </strong> 5500.55
     */
    public MontantLettre(BigDecimal montant) {
        this.montant = montant;
        niveaux.put(1, "");
        niveaux.put(2, "Mille");
        niveaux.put(3, "Million");
        niveaux.put(4, "Milliard");

        unites.put("0", "");
        unites.put("1", "Un");
        unites.put("2", "Deux");
        unites.put("3", "Trois");
        unites.put("4", "Quatre");
        unites.put("5", "Cinq");
        unites.put("6", "Six");
        unites.put("7", "Sept");
        unites.put("8", "Huit");
        unites.put("9", "Neuf");

        dizaines.put("0", "");
        dizaines.put("1", "Dix");
        dizaines.put("2", "Vingt");
        dizaines.put("3", "Trente");
        dizaines.put("4", "Quarante");
        dizaines.put("5", "Cinquante");
        dizaines.put("6", "Soixante");
        dizaines.put("7", "Soixante-Dix");
        dizaines.put("8", "Quatre-vingt");
        dizaines.put("9", "Quatre-vingt-Dix");

        elements.put("11", "Onze");
        elements.put("12", "Douze");
        elements.put("13", "Treize");
        elements.put("14", "Quatorze");
        elements.put("15", "Quinze");
        elements.put("16", "Seize");
        elements.put("71", "Soixante-Onze");
        elements.put("72", "Soixante-Douze");
        elements.put("73", "Soixante-Treize");
        elements.put("74", "Soixante-Quatorze");
        elements.put("75", "Soixante-Quinze");
        elements.put("76", "Soixante-Seize");
        elements.put("91", "Quatre-vingt-Onze");
        elements.put("92", "Quatre-vingt-Douze");
        elements.put("93", "Quatre-vingt-Treize");
        elements.put("94", "Quatre-vingt-Quatorze");
        elements.put("95", "Quatre-vingt-Quinze");
        elements.put("96", "Quatre-vingt-Seize");
    }

    /**
     * Cette méthode transforme un montant non décimal en lettre.
     *
     * @return Une chaîne de caractère (Type : String) représentant l'écriture
     * en lettre du montant.
     * @author joelkdb
     */
    public String getNotDecimalAmountLetter() {
        BigDecimal formatedMontant = this.getPartieEntiere(this.montant);
        return this.transformation(formatedMontant);
    }

    /**
     * Cette méthode transforme un montant décimal en lettre, en tenant compte
     * de la virgule ",", celle-ci écrit en lettre aussi.
     *
     * @return Retourne une chaîne de caractère (Type : String) représentant
     * l'écriture en lettre du montant.
     * @author joelkdb
     */
    public String getDecimalAmountLetter() {
        BigDecimal partieEntiere = this.getPartieEntiere(this.montant);
        BigDecimal partieDecimale = this.getPartieDecimale(this.montant);
        String virguleStr = "virgule ";
        String formatedStr;
        String mttLettresE = this.transformation(partieEntiere);
        String mttLettresD = this.transformation(partieDecimale);
        if (partieDecimale.equals(BigDecimal.ZERO)) {
            formatedStr = mttLettresE.substring(0, mttLettresE.length() - 2);
        } else {
            formatedStr = mttLettresE.substring(0, mttLettresE.length() - 2) + virguleStr + mttLettresD;
        }
        return formatedStr;
    }

    /**
     * Cette méthode transforme un montant décimal en lettre en tenant compte de
     * la monnaie.<br> Attention tout autre monnaie sauf <strong>XOF, XAF (F
     * CFA)</strong>.
     *
     * @param change Le libellé de la monnaie.<br><strong>Exemple :</strong>
     * Euro, Dollars, etc.
     * @return Retourne une chaîne de caractère (Type : String) représentant
     * l'écriture en lettre du montant.
     * @author joelkdb
     */
    public String getDecimalAmountLetterWithChange(String change) {
        BigDecimal partieEntiere = this.getPartieEntiere(this.montant);
        BigDecimal partieDecimale = this.getPartieDecimale(this.montant);
        String formatedStr;
        String mttLettresE = this.transformation(partieEntiere);
        String mttLettresD = this.transformation(partieDecimale);
        if (partieDecimale.equals(BigDecimal.ZERO)) {
            formatedStr = mttLettresE.substring(0, mttLettresE.length() - 2).concat(change);
        } else {
            formatedStr = mttLettresE.substring(0, mttLettresE.length() - 2).concat(change) + " " + mttLettresD;
        }
        return formatedStr;
    }

    /**
     * Méthode permettant de transformer les chiffres du montant en lettres
     *
     * @param montant
     * @return Retourne une chaîne de caractère représantant l'écriture en
     * lettre du montant.
     */
    private String transformation(BigDecimal montant) {
        if (montant == null) {
            return "";
        }
        if (montant.equals(BigDecimal.ZERO)) {
            return "Zéro";
        }
        String montantLettres = "";
        int coupure = 0;
        String montantStr = String.valueOf(montant);

        while (montantStr.length() >= 3) {
            coupure++;
            String jeton = montantStr.substring(montantStr.length() - 3);
            montantStr = montantStr.substring(0, montantStr.length() - 3);
            String cent = jeton.substring(0, 1);
            String diz = jeton.substring(1, 2);
            String unit = jeton.substring(2, 3);
            String jetonLettres = "";
            if (!cent.equals("0")) {
                if ((!cent.equals("1"))) {
                    jetonLettres = (String) unites.get(cent) + " Cent";
                } else {
                    jetonLettres = "Cent";
                }
            }
            int unitAsInt = Integer.parseInt(unit);
            if (((diz.equals("1")) || (diz.equals("7")) || (diz.equals("9"))) && (unitAsInt >= 1) && (unitAsInt <= 6)) {
                jetonLettres = jetonLettres + (String) elements.get(new StringBuilder().append(diz).append(unit).toString());
            } else {
                jetonLettres = jetonLettres + " " + (String) dizaines.get(diz);
                switch (unit) {
                    case "1":
                        jetonLettres = jetonLettres + "-et-";
                        break;
                    case "0":
                        jetonLettres = jetonLettres + "";
                        break;
                    default:
                        jetonLettres = jetonLettres + (diz.equals("0") ? "" : "-");
                        break;
                }
                jetonLettres = jetonLettres + (String) unites.get(unit);
            }

            if (!jeton.equals("000")) {
                jetonLettres = jetonLettres + " " + (String) niveaux.get(coupure);
                if (coupure >= 3) {
                    jetonLettres = jetonLettres + "s";
                }
            }
            montantLettres = jetonLettres + " " + montantLettres;
        }

        if ((montantStr != null) && (montantStr.length() > 0)) {
            coupure++;
            String firstLettres = "";

            if (montantStr.length() == 1) {
                if ((coupure != 2) || (!montantStr.equals("1"))) {
                    firstLettres = firstLettres + (String) unites.get(montantStr);
                }
            } else if (montantStr.length() == 2) {
                String firstChiffre = montantStr.substring(0, 1);
                int secondChiffre = Integer.parseInt(montantStr.substring(1, 2));
                if (((firstChiffre.equals("1")) || (firstChiffre.equals("7")) || (firstChiffre.equals("9"))) && (secondChiffre >= 1)
                        && (secondChiffre <= 6)) {
                    firstLettres = firstLettres + (String) elements.get(montantStr);
                } else {
                    String diz = montantStr.substring(0, 1);
                    String unit = montantStr.substring(1, 2);
                    firstLettres = firstLettres + (String) dizaines.get(diz);
                    switch (unit) {
                        case "1":
                            firstLettres = firstLettres + "-et-";
                            break;
                        case "0":
                            firstLettres = firstLettres + "";
                            break;
                        default:
                            firstLettres = firstLettres + "-";
                            break;
                    }
                    firstLettres = firstLettres + (String) unites.get(unit);
                }
            }

            firstLettres = firstLettres + " " + (String) niveaux.get(coupure);
            if ((coupure >= 3) && (!montantStr.equals("1"))) {
                firstLettres = firstLettres + "s";
            }
            montantLettres = firstLettres + " " + montantLettres;
        }
        return montantLettres;
    }

    /**
     * Méthode permettant de retourner la partie décimale du montant
     *
     * @param montant
     * @return
     */
    private BigDecimal getPartieDecimale(BigDecimal montant) {
        String montantStr = String.valueOf(montant);
        int virguleIndex = montantStr.indexOf(".");
        if (virguleIndex != -1) {
            montantStr = montantStr.substring(virguleIndex + 1, montantStr.length());
        } else {
            montantStr = "0";
        }
        return new BigDecimal(montantStr);
    }

    /**
     * Méthode permettant de retrourner la partie entière du montant
     *
     * @param montant
     * @return
     */
    private BigDecimal getPartieEntiere(BigDecimal montant) {
        String montantStr = String.valueOf(montant);
        int virguleIndex = montantStr.indexOf(".");
        if (virguleIndex != -1) {
            montantStr = montantStr.substring(0, virguleIndex);
        }
        return new BigDecimal(montantStr);
    }

    /**
     * Méthode récupérant le montant à écrire en lettre.
     *
     * @return Retourne le montant à écrire en lettre.
     */
    public BigDecimal getMontant() {
        return montant;
    }

    /**
     * Méthode définissant le montant à écrire en lettre.
     *
     * @param montant Le montant à écrire en lettre.<br>Attention au format : la
     * virgule est spécifié par un ".".<br><strong>Exemple (pour les montants à
     * virgule) : </strong> 5500.55
     */
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

}
