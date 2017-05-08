package solution.question1;


public interface IQuoteQueue<Q> {
	
	// Add item to the queue
	public void add(Q quote);
	// Get and remove all items from the queue. If there are no quotes available then
	//this method will block until some are available.
	public java.util.Collection<Q> removeAll() throws InterruptedException;

}
