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
    public void shouldRunPromiseToGetSumOfAge() {

        List<Person> persons = getPersons();
        Function<List<Person>, Integer> ageAdderFunction = getAgeAdderFunction();

        callPromise(persons, ageAdderFunction);

    }

    private Function<List<Person>, Integer> getAgeAdderFunction() {
        return (people) -> people.stream().mapToInt(Person::getAge).sum();
    }

    @Test
    public void shouldRunPromiseWithAcceptAndRejectFunction() {
        List<Person> persons = getPersons();
        Function<List<Person>, Integer> ageAdderFunction = getAgeAdderFunction();
        Promise<Integer> promise = new PromiseImpl<>();
        Function<List<Person>, Integer> ageSubtract5ThenSum = (people) -> people.stream().mapToInt(Person::getAge)
                .map(age -> age - 5).sum();
        Promise<Integer> sumAgeAdderPromise = promise.then(ageAdderFunction, persons, ageSubtract5ThenSum, people -> people.size() < 3);
        printSum(sumAgeAdderPromise);


    }




    private void callPromise(List<Person> persons, Function<List<Person>, Integer> ageAdderFunction) {
        Promise<Integer> promise = new PromiseImpl<>();
        Promise<Integer> sumAgeHolderPromise = promise.then(ageAdderFunction, persons);
        printSum(sumAgeHolderPromise);


    }

    private void printSum(Promise<Integer> sumAgeHolderPromise) {

        sumAgeHolderPromise.thenAccept((sum) -> System.out.println(sum),
                sumAgeHolderPromise.getValueHolder().get());
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
        Function<List<Person>, List<Person>> multipliedSalFunction = getMulSalaryFunction();

        Promise<List<Person>> promise = new PromiseImpl<>();
        Promise<List<Person>> multipliedSalPromise = promise.then(multipliedSalFunction, persons);
        Promise<List<Person>> mulTwicePromise = multipliedSalPromise.then(multipliedSalFunction, multipliedSalPromise.getValueHolder());
        Consumer<List<Person>> consumerPersonList = (people) -> people.forEach(person -> System.out.println(person.getSalary()));
        mulTwicePromise.thenAccept(consumerPersonList, multipliedSalPromise.getValueHolder());

    }

    private Function<List<Person>, List<Person>> getMulSalaryFunction() {
        return (people) -> people.stream().
                    peek(person -> person.setSalary(person.getSalary() * 2)).collect(Collectors.toList());
    }


}
