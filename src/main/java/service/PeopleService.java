package service;

import models.Person;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PeopleService {
    private Person person;
    private List<Person> people = new ArrayList<>();
    Person p1;
    Person p2;
    Person p3;
    Person p4;
    Person p5;
    Person p6;
    Person p7;

    public PeopleService(Person person) {
        this.person = person;
    }

    public void InitPersons() {
        p1 = new Person("Михаил", 1.34, 0);
        p2 = new Person("Татьяна", 2.12, 0);
        p3 = new Person("Игорь", 3.44, 0);
        p4 = new Person("Александр", 5.15, 0);
        p5 = new Person("Георг", 2.11, 0);
        p6 = new Person("Оля", 1.43, 0);
        p7 = new Person("Светлана", 4.25, 0);
        person.setWalletAccount(3199.10);
    }

    public void createPeople() {
        addPerson(p1);
        addPerson(p2);
        addPerson(p3);
        addPerson(p4);
        addPerson(p5);
        addPerson(p6);
        addPerson(p7);
    }

    public void addPerson(Person person) {
        if (people != null) {
            people.add(person);
        }
    }

    Person getPerson(Person personSeach) {
        Person person = people.stream()
                .filter(person1 -> person1.equals(personSeach))
                .findAny()
                .orElse(null);
        return person;
    }

    public void printPeople() {
        System.out.println("===================================================================");
        if (people != null && (!people.isEmpty())) {
            for (int i = 0; i < people.size(); i++) {
                System.out.println(people.get(i));
            }
        } else {
            System.out.println("List is empty...");
        }
    }

    public void walletCalc() {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        double appendFromBankForOneUser = 0;
        if (people != null && (!people.isEmpty())) {
            appendFromBankForOneUser = person.getWalletAccount() / people.size();
            for (int i = 0; i < people.size(); i++) {
                Person person = people.get(i);
                person.setAppendFromBank(appendFromBankForOneUser - person.getWallet());
            }
        } else {
            System.out.println("List is empty...");
        }
        System.out.println("===================================================================");
        System.out.println("appendFromBankForOneUser = " + formatter.format(appendFromBankForOneUser));
    }

    public void sortPeople(int countMinPerson) {
        List<Person> sortedPeople = null;
        System.out.println("========================== SORTED LIST ============================");
        if(countMinPerson > people.size()){
            System.out.println("The number of people with a minimum cannot be more than the list");
            countMinPerson = people.size();
        } else if (countMinPerson <= 0) {
            System.out.println("The number of people with a minimum cannot be less than 0");
        }

        if (people != null && (!people.isEmpty())) {
            sortedPeople = people.stream()
                    .sorted(Comparator.comparing(Person::getAppendFromBank))
                    .collect(Collectors.toList());
        } else {
            System.out.println("List is empty...");
        }
        for (int i = 0; i < countMinPerson; i++) {
            System.out.println(sortedPeople.get(i));
        }
        System.out.println("===================================================================");
    }

}
