package solution.question3;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.BlockingQueue;

import org.junit.Before;
import org.junit.Test;

public class SubscriptionManagerTest {
	
	private CustomSubscriptionManager<Key, Listener, Event> subscriptionManager;
	private final String eventkey = "Event1";
	
	@Before
	public void setup () {
		subscriptionManager = new CustomSubscriptionManager<Key, Listener, Event>();
	}
	
	@Test
	public void concurrentSubscribeAndUnSubscribeShldSucceed() throws InterruptedException {
		Key key = new Key(eventkey);
		Listener listener1 = new Listener("client1");
		Listener listener2 = new Listener("client2");
		Listener listener3 = new Listener("client3");
		
		createSubscriberThread(key, listener1).start();
		createSubscriberThread(key, listener2).start();
		
		Thread.sleep(10);
		
		createSubscriberThread(key, listener3).start();
		
		Thread.sleep(10);
		
		createUnSubscriberThread(key, listener2).start();
		
		Thread.sleep(100);
		
		BlockingQueue<Listener> queue = subscriptionManager.getSubscription().get(key);
		assertTrue(queue != null && queue.contains(listener1) && queue.contains(listener3));	
	}
	
	@Test
	public void realUnsubscribeShldBeCalledIfAllClientsHaveUnsubscribedFromTheGivenKey() throws InterruptedException {
		Key key = new Key(eventkey);
		Listener listener1 = new Listener("client1");
		
		createSubscriberThread(key, listener1).start();
		createUnSubscriberThread(key, listener1).start();
		
		createSubscriberThread(key, listener1).start();
		
		Thread.sleep(100);
		
		createUnSubscriberThread(key, listener1).start();
		
		Thread.sleep(10);
		
		BlockingQueue<Listener> queue = subscriptionManager.getSubscription().get(key);
		assertTrue(queue == null);
		
	}
	
	@Test
	public void unsubscribeShldNotChangeSubscriptionIfClientsAreNotFound() throws InterruptedException {
		Key key = new Key(eventkey);
		Listener listener1 = new Listener("client1");
		Listener listener2 = new Listener("client2");
		
		createSubscriberThread(key, listener1).start();
		createSubscriberThread(key, listener2).start();
		
		Thread.sleep(10);
		
		createUnSubscriberThread(key, new Listener("xyz")).start();
		createUnSubscriberThread(key, new Listener("123")).start();
		
		Thread.sleep(100);
		
		BlockingQueue<Listener> queue = subscriptionManager.getSubscription().get(key);
		assertTrue(queue != null && queue.size() == 2);
	}
	
	
	private Thread createSubscriberThread(Key key, Listener listener) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				subscriptionManager.subscribe(key, listener);
			}
		});
	}
	
	private Thread createUnSubscriberThread(Key key, Listener listener) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				subscriptionManager.unsubscribe(key, listener);
			}
		});
	}

}
