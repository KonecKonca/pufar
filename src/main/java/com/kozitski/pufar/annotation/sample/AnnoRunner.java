package com.kozitski.pufar.annotation.sample;

import com.kozitski.pufar.annotation.sample.entity.AccountOperationManager;
import com.kozitski.pufar.annotation.sample.entity.AccountOperationManagerImpl;

public class AnnoRunner {
    public static void main(String[] args) {

        AccountOperationManager securityAccount = AccountOperationManagerImpl.createAccountOperationManagerImpl();

        securityAccount.depositInCash(10128336, 6);
        securityAccount.withdraw(64092376, 2);

        securityAccount.convert(200);
        securityAccount.transfer(64092376, 300);

    }
}
