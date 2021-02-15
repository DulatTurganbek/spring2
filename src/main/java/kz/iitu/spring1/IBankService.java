package kz.iitu.spring1;

public interface IBankService {
    int checkBalance(String name, String pin);
    void withdraw(String name, String pin, int money);
    void upToBalance(String name, String pin, int money);
}
