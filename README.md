# Concurrent Wallet

1. A wallet which allows using multiple accounts and process payments using a suitable account from the available ones.
2. There are interfaces `Account`, `PaymentLog` and `Wallet`, as well as `ShortageOfMoneyException` and `WalletFactory`.
3. `WalletFactory` provides only one method - `wallet(List<Account>,PaymentLog)`, that creates a new instance of `Wallet` and passes a list of accounts to it as well as paymenmt log. 
4. `Wallet` interface has only one method `pay(String, long)`,

This method finds an account whose balance exceeds requested amount,
decrease the amount of the account balance by specific amount and log this operation by sending information about the recipient and the amount to payment log. If no account can handle the payment, throwing `ShortageOfMoneyException` including information about
recipient and the requested amount. Any other exception will be considered as
invalid. This method works
correctly in multi-threaded environment, because it will be called from multiple threads simultaneously.

