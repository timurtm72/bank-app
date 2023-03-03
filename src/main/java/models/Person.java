package models;

import java.text.DecimalFormat;

public class Person {
    private String name;
    private double wallet;
    private double appendFromBank;

    private double walletAccount;

    public Person() {
    }

    public Person(String name, double wallet, double appendFromBank) {
        this.name = name;
        this.wallet = wallet;
        this.appendFromBank = appendFromBank;
    }

    public double getWalletAccount() {
        return walletAccount;
    }

    public void setWalletAccount(double walletAccount) {
        this.walletAccount = walletAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public double getAppendFromBank() {
        return appendFromBank;
    }

    public void setAppendFromBank(double appendFromBank) {
        this.appendFromBank = appendFromBank;
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        return "Person " +
                "name = '" + name + '\'' +
                ", wallet = " + wallet +
                ", appendFromBank = " + formatter.format(appendFromBank);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (Double.compare(person.wallet, wallet) != 0) return false;
        if (Double.compare(person.appendFromBank, appendFromBank) != 0) return false;
        if (Double.compare(person.walletAccount, walletAccount) != 0) return false;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(wallet);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(appendFromBank);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(walletAccount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
