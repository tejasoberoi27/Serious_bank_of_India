public class Account {
    private final int id;
    private volatile int balance;

    public Account(int id) {
        this.id = id;
        this.balance = 10000;
    }

    public synchronized void debit(float amount) {
        this.balance -= amount;
    }

    public synchronized void credit(float amount) {
        this.balance += amount;
    }

    public synchronized void transfer(float amount,
                                      Account target) {
        debit(amount);
        target.credit(amount);
    }


    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }


}
