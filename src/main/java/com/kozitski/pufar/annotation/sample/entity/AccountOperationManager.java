package com.kozitski.pufar.annotation.sample.entity;

public interface AccountOperationManager {

    void depositInCash(int accountNumber, int amount);
    void withdraw(int accountNumber, int amount);
    void convert(double amount);
    void transfer(int accountNumber, double amount);

}
