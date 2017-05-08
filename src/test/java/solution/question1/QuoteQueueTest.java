package solution.question1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.Thread.State;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class QuoteQueueTest {

	private QuoteQueue<Quote> queue;
	
	@Before
	public void setup() {
		queue = new QuoteQueue<Quote>(1);
	}
	
	
	@Test
	public void addToQueueShldSucceedForAnyQueueCapacity() throws InterruptedException {
		createProducerThread(10).start();
		createProducerThread(20).start();
		createProducerThread(30).start();
		
		Thread.sleep(100);
		
		assertTrue(queue.size() > 0);
	}
	
	@Test
	public void removeAllFromQueueShldBlockIfQueueIsEmpty() throws InterruptedException {
		Thread consumer = createConsumerThread();
		
		consumer.start();
		
		Thread.sleep(10);
		
		assertFalse(State.TERMINATED.equals(consumer.getState()));
		
		createProducerThread(10).start();
		
		Thread.sleep(10);
		
		assertTrue(State.TERMINATED.equals(consumer.getState()));
	}
	
	@Test
	public void quoteShldBeConsumedInFIFOManner() throws InterruptedException {
		createProducerThread(10).start();
		createProducerThread(20).start();
		
		Thread.sleep(100);
		
		List<Quote> producerList = queue.newQuoteList();
		
		for(int i = 0; i < producerList.size(); i ++) {
			assertTrue(producerList.get(i).equals(queue.remove()));
		}
		
	}
	
	private Thread createProducerThread(int threadId) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				Random random = new Random();
				int n = random.nextInt(3) + 1;
				for(int i = 1; i <= n; i++) {
					queue.add(new Quote(i + threadId));
					System.out.println("Thread " + threadId + "- added new quote " + (i + threadId));
				}
			}
		});
	}
	
	private Thread createConsumerThread() {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Collection<Quote> list = queue.removeAll();
					System.out.println("Consume " + list.size() + " quote(s).");
					
				} catch (InterruptedException e) {
					System.out.println("Consumer thread interrupted.");
				}
				
			}
		});
	}
	
}
