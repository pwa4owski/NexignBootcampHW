package nexign.bootcamp.brt.exception;

public class NotPositiveBalanceException extends RuntimeException{
    public NotPositiveBalanceException(String s) {
        super(String.format("Для авторизации абонента с номером %s, его баланс должен быть больше 0", s));
    }
}
