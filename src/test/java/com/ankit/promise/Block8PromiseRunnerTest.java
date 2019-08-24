package com.ankit.promise;

import com.ankit.promise.model.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Block8PromiseRunnerTest {

    @Test
    public void shouldRunPromiseToGetSumOfAge(){

        List<Person> persons = getPersons();
        Function<List<Person>, Integer> ageAdderFunction = (personList) -> personList.stream()
                .mapToInt(Person::getAge).sum();

        callPromise(persons, ageAdderFunction);

    }

    private void callPromise(List<Person> persons, Function<List<Person>, Integer> ageAdderFunction) {
        Promise<Integer> promise = new Block8Promise<>();
        Promise<Integer> sumAgeHolderPromise = promise.then(ageAdderFunction, persons);
        sumAgeHolderPromise.thenAccept((age) -> System.out.println(age.get()),
                sumAgeHolderPromise.getValueHolder());


    }

    private List<Person> getPersons() {
        Person person1 = new Person(32,"Ankit",21);
        Person person2 = new Person(20,"X",10);
        Person person3 = new Person(40,"Y",40);

        return Arrays.asList(person1,person2,person3);
    }
}
