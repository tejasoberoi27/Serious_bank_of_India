import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SBI {
    private volatile ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private Random random;


    private int nextId() {
        return random.nextInt((int) 1e4);
    }

    private long calcSum()
    {
        long sum=0;
        for(int i=0;i<accounts.size();i++)
        {
            sum+=(accounts.get(i).getBalance());
        }
        return sum;
    }





    private int nextAmount(int bar) {
        int min = 1;
        int max = bar;
//        float x = min + random.nextInt() * (max - min);
//        while (x == 0) {
//            x = min + random.nextInt() * (max - min);
//        }
        return random.nextInt(100);

//        return (int)x;
    }

    private ArrayList<Account> generateAccounts() {
        ArrayList<Account> accounts = new ArrayList<Account>((int) 1e4);


        for (int j = 0; j < (int) 1e4; j++) {
            accounts.add(new Account(j));
        }
//        if(accounts==null)
//        {
//            System.out.println("TRUE");
//        }
        return accounts;
    }

    private ArrayList<Transaction> generateTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>((int) 1e7);

        for (int i = 0; i < ((int) 1e7); i++) {
            int source_id = nextId();
            int target_id = nextId();
            while (source_id == target_id) {
                target_id = nextId();
            }
//            System.out.println(accounts.get(0).getId());
            int balance_source = accounts.get(source_id).getBalance();
            int amount = nextAmount(balance_source);
//            System.out.println("s = "+transactions.size());
            transactions.add(new Transaction(accounts.get(source_id), accounts.get(target_id), amount));
        }
        return transactions;
    }

    public SBI() {
        this.random = new Random();
        this.accounts = generateAccounts();
        this.transactions = generateTransactions();


    }

    public void complete() throws InterruptedException {

        for (int i = 1; i <= 4; i++) {
            long initial_sum = calcSum();
            System.out.println(initial_sum);
            ExecutorService exec = Executors.newFixedThreadPool(i);

            Instant start = Instant.now();
//            ArrayList <Transaction> Trans = new ArrayList<Transaction>(transactions.size());
//            ArrayList<Transaction> Trans = new ArrayList<Transaction>(transactions.size());
            ArrayList<Computation> tasks = new ArrayList<Computation>(i);
            int low = 0;
            int increment = transactions.size() / i;
//            System.out.println("i = "+increment);
            int high = increment;

            int size = transactions.size();
            for (int j = 0; j < i; j++) {
//                System.out.println(low);
//                System.out.println(high);
//                if (high == size) {
//                    high = size - 1;
//                    System.out.println(true);
//                }
                tasks.add(new Computation(accounts, transactions, low, high));
                low = high;
                high += increment;
            }

            for (int j = 0; j < i; j++) {
                exec.execute(tasks.get(j));
            }

            if (!exec.isTerminated()) {
                exec.shutdown();
                exec.awaitTermination(5L, TimeUnit.SECONDS);
            }



            Instant end = Instant.now();
            Duration period = Duration.between(start, end);
            System.out.println("NUMBER OF THREADS---------------"+ i);
            System.out.println("TIME ELAPSED -------------------"+ period.toMillis());

            long final_sum = calcSum();
            System.out.println(final_sum);
            if(initial_sum==final_sum)
            {
                System.out.println("PASSED");
            }
            else
            {
                System.out.println("FAIL");
            }
        }




    }


}
