package com.kozitski.pufar.annotation.sample.entity;

import com.kozitski.pufar.annotation.sample.SecurityFactory;
import com.kozitski.pufar.annotation.sample.annotation.BankingAnnotation;
import com.kozitski.pufar.annotation.sample.annotation.SecurityLevelEnum;

public class AccountOperationManagerImpl implements AccountOperationManager {

    private AccountOperationManagerImpl() { }
    public static AccountOperationManager createAccountOperationManagerImpl(){
        return SecurityFactory.createSecurityObject(new AccountOperationManagerImpl());
    }

    @Override
    @BankingAnnotation(securityLevel = SecurityLevelEnum.HIGH)
    public void depositInCash(int accountNumber, int amount) {

        System.out.println("\t---- // зачисление на депозит\n");
        // зачисление на депозит

    }

    @Override
    @BankingAnnotation(securityLevel = SecurityLevelEnum.HIGH)
    public void withdraw(int accountNumber, int amount) {

        System.out.println("\t---- // снятие суммы, если не превышает остаток\n");
        // снятие суммы, если не превышает остаток

    }

    @Override
    @BankingAnnotation(securityLevel = SecurityLevelEnum.LOW)
    public void convert(double amount) {

        System.out.println("\t---- // конвертировать сумму\n");
        // конвертировать сумму

    }

    @Override
    @BankingAnnotation
    public void transfer(int accountNumber, double amount) {

        System.out.println("\t---- // перевести сумму на счет\n");
        // перевести сумму на счет

    }

}
