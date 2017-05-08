package solution.question3;

public abstract class SubscriptionManager<K, L, E> {
	
	public abstract void subscribe(K key, L listener);
	public abstract void unsubscribe(K key, L listener);
	/**
	* Performs real subscription for a given key
	* @param key the given subscription key
	*/
	protected void realSubscribe(K key) {
	}
	/**
	* Performs real un-subscription for a given key
	* @param key the given subscription key
	*/
	protected void realUnsubscribe(K key) {
	}
	public abstract void onEvent(K key, E event);

}
