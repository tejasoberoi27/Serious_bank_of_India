public class Transaction{

    private Account source,target;
    private float amount;

    public Transaction(Account source, Account target, float amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    public void transfer_funds() {
        Object	obj1	=	null,	obj2	=	null;
        if(source.getId()	>	target.getId())	{
            obj1=target;	obj2=source;
        }
        else	{	obj1=source;	obj2=target;}
        synchronized(obj1)	{
            synchronized(obj2)	{
            source.transfer(amount,	target);
        }
        }
    }
}
