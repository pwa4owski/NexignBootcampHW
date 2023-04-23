package nexign.bootcamp.brt.exception;

import java.util.NoSuchElementException;

public class NoSuchAbonentException extends NoSuchElementException {
    public NoSuchAbonentException(String s) {
        super(String.format("Абонент с номером %s не найден среди клиентов оператора Ромашка", s));
    }
}
