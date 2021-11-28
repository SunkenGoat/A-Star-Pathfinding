package application;

import java.lang.reflect.Array;

public class Heap<T extends IHeapItem<T>> {
	
	T[] items;
	int currentItemCount;
	
	@SuppressWarnings("unchecked")
	public Heap(int maxHeapSize) {
		items = (T[]) Array.newInstance(getClass(), maxHeapSize);

//		items = (T[]) new Object[maxHeapSize];
	}
	
	public void Add(T item) {
		item.setIndex(currentItemCount);
		items[currentItemCount] = item;
		SortUp(item);
		currentItemCount++;
	}

	public T RemoveFirst() {
		T firstItem = items[0];
		currentItemCount--;
		items[0] = items[currentItemCount];
		items[0].setIndex(0);;
		SortDown(items[0]);
		return firstItem;
	}

	public void UpdateItem(T item) {
		SortUp(item);
	}

	public int getCount() {
		return currentItemCount;
	}

	public boolean Contains(T item) {
		return items[item.getIndex()].equals(item);
	}

	void SortDown(T item) {
		while (true) {
			int childIndexLeft = item.getIndex() * 2 + 1;
			int childIndexRight = item.getIndex() * 2 + 2;
			int swapIndex = 0;

			if (childIndexLeft < currentItemCount) {
				swapIndex = childIndexLeft;

				if (childIndexRight < currentItemCount) {
					if (items[childIndexLeft].compareTo(items[childIndexRight]) < 0) {
						swapIndex = childIndexRight;
					}
				}

				if (item.compareTo(items[swapIndex]) < 0) {
					Swap (item,items[swapIndex]);
				}
				else {
					return;
				}

			}
			else {
				return;
			}

		}
	}
	
	void SortUp(T item) {
		int parentIndex = (item.getIndex()-1)/2;
		
		while (true) {
			T parentItem = items[parentIndex];
			if (item.compareTo(parentItem) > 0) {
				Swap (item,parentItem);
			}
			else {
				break;
			}

			parentIndex = (item.getIndex()-1)/2;
		}
	}
	
	void Swap(T itemA, T itemB) {
		items[itemA.getIndex()] = itemB;
		items[itemB.getIndex()] = itemA;
		int itemAIndex = itemA.getIndex();
		itemA.setIndex(itemB.getIndex());
		itemB.setIndex(itemAIndex);
	}

	public boolean isEmpty() {
		if (items.length == 0) {
			return true;
		}
		return false;
	}

}

