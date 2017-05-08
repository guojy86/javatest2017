package solution.question1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Q1 solution. Implemented an unbounded blocking queue backed by an ArrayList 
 * @author Jiayuan
 *
 * @param <Q>
 */
public class QuoteQueue<Q> implements IQuoteQueue<Q> {
	
	private List<Q> quoteList;
	
	public QuoteQueue(int capacity) {
		quoteList = new ArrayList<Q>(capacity);
	}

	@Override
	public synchronized void add(Q quote) {
		quoteList.add(quote);
		
		notifyAll();
	}

	@Override
	public synchronized Collection<Q> removeAll() throws InterruptedException {
		while (quoteList.isEmpty()) {
			wait();
		}
		
		List<Q> newQuoteList = new ArrayList<Q>(quoteList);
		
		quoteList.clear();
		
		return newQuoteList;
	}
	
	public synchronized Q remove() throws InterruptedException {
		while (quoteList.isEmpty()) {
			wait();
		}
		
		return quoteList.remove(0);
	}
	
	public synchronized int size() {
		return quoteList.size();
	}
	
	public synchronized void clear() {
		quoteList.clear();
	}

	public List<Q> newQuoteList() {
		return new ArrayList<Q>(quoteList);
	}

}
