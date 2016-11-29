
public interface Observable { //Not working
	
	public void registerObserver(OutputUpdater o);
	public void notifyObservers();
}
