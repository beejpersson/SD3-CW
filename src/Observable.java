
public interface Observable {
	
	public void registerObserver(OutputUpdater o);
	public void notifyObservers();
}
