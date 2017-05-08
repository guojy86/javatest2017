package solution.question3;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Q3 solution. Using concurrent hashmap and LinkedBlockingQueue to store and process client subscriptions
 * @author Jiayuan
 *
 * @param <K>
 * @param <L>
 * @param <E>
 */
public class CustomSubscriptionManager<K, L, E> extends SubscriptionManager<K, L, E> {
	
	// store a list of client subscription for a given key..
	private Map<K, BlockingQueue<L>> subscriptionMap;
	
	public CustomSubscriptionManager() {
		subscriptionMap = new ConcurrentHashMap<K, BlockingQueue<L>>();
	}

	@Override
	public synchronized void subscribe(K key, L listener) {
		BlockingQueue<L> subscriptionList = subscriptionMap.get(key);
		if (subscriptionList != null) {
			//update client subscriptions..
			if (!subscriptionList.contains(listener)) {
				subscriptionList.add(listener);
				subscriptionMap.put(key, subscriptionList);
			}
			
			System.out.println("Subscribe " + listener + " with " + key);
		} else {
			subscriptionList = new LinkedBlockingQueue<L>();
			subscriptionList.add(listener);
			subscriptionMap.put(key, subscriptionList);
			
			System.out.println("Subscribe " + listener + " with " + key);
			
			// new subscription.. perform real subscription
			realSubscribe(key);
			System.out.println("Perform real subscribe on " + key);
		}
	}

	@Override
	public synchronized void unsubscribe(K key, L listener) {
		BlockingQueue<L> subscriptionList = subscriptionMap.get(key);
		if (subscriptionList != null) {
			subscriptionList.remove(listener);
			System.out.println("Un-subscribe " + listener + " from " + key);
			
			if (subscriptionList.isEmpty()) {
				subscriptionMap.remove(key);
				
				// empty subscription list.. proceed to perform real un-subscribe
				realUnsubscribe(key);
				
				System.out.println("Perform real un-subscribe on " + key);
			}
		}
	}	
	

	@Override
	public synchronized void onEvent(K key, E event) {
		BlockingQueue<L> subscriptionList = subscriptionMap.get(key);
		if (subscriptionList != null) {
			for (L listener: subscriptionList) {
				//handle the event here...
				System.out.println("Handle event on " + listener);
			}
		}	
	}
	
	public Map<K, BlockingQueue<L>> getSubscription() {
		return subscriptionMap;
	}

}
