import models.Person;
import service.PeopleService;

public class Main {
    public static void main(String[] args) {
        PeopleService service = new PeopleService(new Person());
        service.InitPersons();
        service.createPeople();
        service.walletCalc();
        service.printPeople();
        service.sortPeople(3);
    }
}
