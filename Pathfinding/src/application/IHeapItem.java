package application;

public interface IHeapItem<T> extends Comparable<T> {
	
	public int getIndex();
	public void setIndex(int heapIndex);
	
}
