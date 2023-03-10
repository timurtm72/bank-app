package service;

import models.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Service {
    private Person person;
    private List<Person> people = new ArrayList<>();
    private List<Person> sdPeople = new ArrayList<>();
    public Service() {
    }

    public Service(Person person) {
        this.person = person;
    }

    public List<Person> getSortedPeople() {
        return sdPeople;
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
        if (people != null && (!people.isEmpty())) {
            for (int i = 0; i < people.size(); i++) {
                System.out.println(people.get(i));
            }
        } else {
            System.out.println("Список пустой...");
        }
    }

    public void writeXmlToFile(List<Person> people,List<Person> sortPeople,int countMinPerson ) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document = builder.newDocument();
        Element total = document.createElement("total");
        Element result = document.createElement("result");
        document.appendChild(total);
        total.appendChild(result);
        Person getPerson = null;
        Element person_xml = null;
        for (int i = 0; i < people.size(); i++) {
            getPerson = people.get(i);
            person_xml = document.createElement("Person");
            result.appendChild(person_xml);
            person_xml.setAttribute("name", getPerson.getName());
            person_xml.setAttribute("wallet", getPerson.getWallet().toString());
            person_xml.setAttribute("x_appendFromBank", getPerson.getAppendFromBank().toString());
        }
        Element minimum = document.createElement("minimum");
        total.appendChild(minimum);
        Element person_xml1 = null;
        for (int i = 0; i < countMinPerson; i++) {
            getPerson = sortPeople.get(i);
            person_xml1 = document.createElement("Person");
            minimum.appendChild(person_xml1);
            person_xml1.setAttribute("name", getPerson.getName());
            person_xml1.setAttribute("wallet", getPerson.getWallet().toString());
            person_xml1.setAttribute("x_appendFromBank", getPerson.getAppendFromBank().toString());
        }


        Transformer t = null;
        StringWriter writer = new StringWriter();
        try {
            t = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        try {
            t.transform(new DOMSource(document), new StreamResult(writer));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        String output = writer.getBuffer().toString();
        output = output.replaceAll("x_", "");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("result.xml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            fos.write(output.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void walletCalc() {
        Person person = null;
        BigDecimal appendFromBankForOneUser = BigDecimal.valueOf(0);
        BigDecimal amountOfAccruedMoney = BigDecimal.valueOf(0);
        BigDecimal amountOfMoney = BigDecimal.valueOf(0);
        BigDecimal difOfMoney = BigDecimal.valueOf(0);
        BigDecimal copeycy = BigDecimal.valueOf(0);

        amountOfMoney = Person.getWalletAccount().setScale(2, RoundingMode.CEILING);

        if (people != null && (!people.isEmpty())) {
            person = people.get(0);
            appendFromBankForOneUser = amountOfMoney.divide(BigDecimal.valueOf(people.size()), 2, RoundingMode.HALF_DOWN);
            for (int i = 0; i < people.size(); i++) {
                person = people.get(i);
                person.setAppendFromBank(appendFromBankForOneUser.subtract(person.getWallet(), new MathContext(0, RoundingMode.HALF_DOWN)));
                amountOfAccruedMoney = amountOfAccruedMoney.add(person.getWallet().add(person.getAppendFromBank()));
            }
        } else {
            System.out.println("Список пуст...");
        }

        writeXmlToFile(people,sortPeople(people),3);

        System.out.println("==================== ВЫЧИСЛИЛИ КОМУ СКОЛЬКО  ======================");
        printPeople();
         if (amountOfMoney.compareTo(amountOfAccruedMoney) == 0) {
            System.out.println("Суммы равны");
        } else if (amountOfMoney.compareTo(amountOfAccruedMoney) == 1) {
            System.out.println("Сумма выданная для всех людей больше суммы вычисленная для всех людей");
            difOfMoney = amountOfMoney.subtract(amountOfAccruedMoney);
        } else if (amountOfMoney.compareTo(amountOfAccruedMoney) == -1) {
            System.out.println("Сумма вычисленная для всех людей больше суммы выданная для всех людей");
            difOfMoney = amountOfMoney.subtract(amountOfAccruedMoney);
        }
        copeycy = difOfMoney.multiply(BigDecimal.valueOf(100));
        System.out.println("===================================================================");
        System.out.println("Сумма вычисленная для каждого человека " + appendFromBankForOneUser);
        System.out.println("Сумма выданная для всех людей " + amountOfMoney);
        System.out.println("Сумма вычисленная для всех людей " + amountOfAccruedMoney);
        System.out.println("Разница сумм " + difOfMoney.multiply(BigDecimal.valueOf(100)) + " копейки");
        int[] data = new int[people.size()];
        for (int i = 0; i < people.size(); i++) {
            data[i] = people.size() * 2;
        }
        boolean isPresent = false;
        for (int i = 0; i < copeycy.intValue(); i++) {
            int genData = generateRandomNumber(people.size() - 1);
            for (int j = 0; j < copeycy.intValue(); j++) {
                if (data[j] == genData) {
                    isPresent = true;
                    break;
                } else {
                    isPresent = false;
                }
            }
            if (isPresent) {
                --i;
            } else {
                data[i] = genData;
            }
        }
        System.out.println("Массив индексов кому раздать " + copeycy.intValue() + " копейки");
        for (int i = 0; i < copeycy.intValue(); i++) {
            System.out.print(data[i]);
            System.out.print(" ,");
        }
        System.out.println();
        printSortPeople(3);
        for (int i = 0; i < copeycy.intValue(); i++) {
            person = people.get(data[i]);
            person.setWallet(person.getWallet().add(difOfMoney.divide(copeycy)));
        }
        if (people != null && (!people.isEmpty())) {
            person = people.get(0);
            appendFromBankForOneUser = amountOfMoney.divide(BigDecimal.valueOf(people.size()), 2, RoundingMode.HALF_DOWN);
            for (int i = 0; i < people.size(); i++) {
                person = people.get(i);
                person.setWallet(person.getWallet().add(person.getAppendFromBank()));
                person.setAppendFromBank(BigDecimal.valueOf(0));
            }
        } else {
            System.out.println("Список пуст...");

        }
        System.out.println("====================== ИТОГОВЫЙ РЕЗУЛЬТАТ  ========================");
        printPeople();
        System.out.println("===================================================================");
    }

    public List<Person> sortPeople(List<Person> people) {
        List<Person> sortedPeople = null;
        if (people != null && (!people.isEmpty())) {
            sortedPeople = people.stream()
                    .sorted(Comparator.comparing(Person::getAppendFromBank))
                    .collect(Collectors.toList());
        } else {
            System.out.println("Список пуст...");
        }
        return sortedPeople;
    }
    public boolean printSortPeople(int countMinPerson){
        List<Person> sortPeople = sortPeople(people);
        System.out.println("==================== ОТСОРТИРОВАННЫЙ СПИСОК ======================");
        if (countMinPerson > sortPeople.size()) {
            System.out.println("Количество людей не может быть больше длины списка");
            return false;
        } else if (countMinPerson <= 0) {
            System.out.println("Количество людей не может быть равно 0 и меньше 0");
            return false;
        }
        for (int i = 0; i < countMinPerson; i++) {
            System.out.println(sortPeople.get(i));
        }
        return true;
    }
    public static int generateRandomNumber(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative");
        }

        // генерируем случайное число от 0 до `n`
        return new Random().nextInt(n + 1);
    }

    public void readAndParsingFile() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document document = null;
        try {
            document = builder.parse(new File("task.xml"));
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();

        BigDecimal bWallet = BigDecimal.valueOf(Double.valueOf(root.getAttribute("wallet")));
        Person.setWalletAccount(bWallet);

        NodeList nList = document.getElementsByTagName("Person");
        for (int i = 0; i < nList.getLength(); i++) {
            person = new Person();
            Element personElement = (Element) nList.item(i);
            person.setName(personElement.getAttribute("name"));
            BigDecimal res = BigDecimal.valueOf(Double.valueOf(personElement.getAttribute("wallet")));
            person.setWallet(res.setScale(2, RoundingMode.HALF_DOWN));
            person.setAppendFromBank(BigDecimal.valueOf(0));
            addPerson(person);
        }
    }

}
