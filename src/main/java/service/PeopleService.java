package service;

import models.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private Person person;
    private List<Person> people = new ArrayList<>();

    public void InitPersons(){

    }

    public void addPerson(Person person){
        if(people != null){
            people.add(person);
        }
    }

    Person getPerson(Person personSeach){
        Person person = people.stream()
                .filter(person1 -> person1.equals(personSeach))
                .findAny()
                .orElse(null);
        return person;
    }

    public void printPeople(){
        for (int i = 0; i < people.size(); i++) {
            System.out.println(people.get(i));
        }
    }

}
