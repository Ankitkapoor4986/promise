package com.ankit.promise;

import com.ankit.promise.model.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PromiseImplTest {

    @Test
    public void shouldRunPromiseToGetSumOfAge(){

        List<Person> persons = getPersons();
        Function<List<Person>, Integer> ageAdderFunction = (personList) -> personList.stream()
                .mapToInt(Person::getAge).sum();

        callPromise(persons, ageAdderFunction);

    }

    private void callPromise(List<Person> persons, Function<List<Person>, Integer> ageAdderFunction) {
        Promise<Integer> promise = new PromiseImpl<>();
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

    @Test
    public void shouldPrintMultipliedSal() {

        List<Person> persons = getPersons();
        Function<List<Person>, List<Person>> multipliedSalFunction = (personsList) -> personsList.stream().
                peek(person -> person.setSalary(person.getSalary() * 2)).collect(Collectors.toList());

        Promise<List<Person>> promise = new PromiseImpl<>();
        Promise<List<Person>> multipliedSalPromise = promise.then(multipliedSalFunction, persons);
        Promise<List<Person>> mulTwicePromise = multipliedSalPromise.then(multipliedSalFunction, multipliedSalPromise.getValueHolder().get());
        Consumer<List<Person>> consumerPersonList = (personList) -> personList.forEach(person -> System.out.println(person.getSalary()));
        mulTwicePromise.thenAccept(consumerPersonList, multipliedSalPromise.getValueHolder().get());

    }
}
