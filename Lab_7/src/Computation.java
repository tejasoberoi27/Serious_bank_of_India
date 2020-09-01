import java.util.ArrayList;

public class Computation implements Runnable {
    volatile private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private int low, high;

    public Computation(ArrayList<Account> accounts, ArrayList<Transaction> transactions, int low, int high) {
        this.accounts = accounts;
        this.transactions = transactions;
        this.low = low;
        this.high = high;
    }

    //assume	array.length%2=0
    @Override
    public void run() {
        for (int i = low; i < high; i++) {
            transactions.get(i).transfer_funds();
        }
    }

    public ArrayList<Transaction> getResultTrans() {
        return transactions;
    }

    public ArrayList<Account> getResultAccounts() {
        return accounts;
    }
}
