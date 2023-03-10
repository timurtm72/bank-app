package models;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Person {
    private String name;
    private BigDecimal wallet;
    private BigDecimal appendFromBank;

    private static BigDecimal walletAccount;

    public Person() {
    }

    public Person(String name, BigDecimal wallet, BigDecimal appendFromBank) {
        this.name = name;
        this.wallet = wallet;
        this.appendFromBank = appendFromBank;
    }

    public static BigDecimal getWalletAccount() {
        return walletAccount;
    }

    public static void setWalletAccount(BigDecimal walletAccount) {
        Person.walletAccount = walletAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWallet() {
        return wallet;
    }

    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }

    public BigDecimal getAppendFromBank() {
        return appendFromBank;
    }

    public void setAppendFromBank(BigDecimal appendFromBank) {
        this.appendFromBank = appendFromBank;
    }

    @Override
    public String toString() {
        return "Person " +
                "name = '" + name + '\'' +
                ", wallet = " + wallet +
                ", appendFromBank = " + appendFromBank;
    }
}
