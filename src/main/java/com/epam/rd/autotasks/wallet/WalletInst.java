package com.epam.rd.autotasks.wallet;

import java.util.List;
import java.util.concurrent.locks.Lock;

public class WalletInst implements Wallet{
    private final List<Account> accounts;
    private final PaymentLog log;
    public WalletInst(List<Account> accounts, PaymentLog log){
        this.accounts = accounts;
        this.log = log;
    }
    @Override
    public void pay(String recipient, long amount) throws ShortageOfMoneyException {
        Account account = null;
        for (Account acc : accounts) {
            Lock lock = acc.lock();
            lock.lock();
            try {
                if (acc.balance() >= amount) {
                    account = acc;
                    break;
                }
            } finally {
                lock.unlock();
            }
        }
        if (account == null) {
            throw new ShortageOfMoneyException(recipient, amount);
        }
        Lock lock = account.lock();
        lock.lock();
        try {
            if (account.balance() < amount) {
                throw new ShortageOfMoneyException(recipient, amount);
            }
            account.pay(amount);
            log.add(account, recipient, amount);
        } finally {
            lock.unlock();
        }
    }
}
