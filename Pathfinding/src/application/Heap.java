package application;

import java.util.ArrayList;
import java.util.List;

public class Heap<T extends IHeapItem<T>> {
	
	List<T> items;
	int currentItemCount;
	
	public Heap(int maxHeapSize) {
		items = new ArrayList<T>(maxHeapSize);
		for (int i = 0; i < maxHeapSize; i++) {
			items.add(null);
		}
		System.out.println(items);

//		items = (T[]) new Object[maxHeapSize];
	}
	
	public void Add(T item) {
		item.setIndex(currentItemCount);
		items.set(currentItemCount, item);
		SortUp(item);
		currentItemCount++;
	}

	public T RemoveFirst() {
		T firstItem = items.get(0);
		currentItemCount--;
		items.set(0, items.get(currentItemCount));
		items.get(0).setIndex(0);;
		SortDown(items.get(0));
		return firstItem;
	}

	public void UpdateItem(T item) {
		SortUp(item);
	}

	public int getCount() {
		return currentItemCount;
	}

	public boolean Contains(T item) {
		return items.get(item.getIndex()).equals(item);
	}

	void SortDown(T item) {
		while (true) {
			int childIndexLeft = item.getIndex() * 2 + 1;
			int childIndexRight = item.getIndex() * 2 + 2;
			int swapIndex = 0;

			if (childIndexLeft < currentItemCount) {
				swapIndex = childIndexLeft;

				if (childIndexRight < currentItemCount) {
					if (items.get(childIndexLeft).compareTo(items.get(childIndexRight)) < 0) {
						swapIndex = childIndexRight;
					}
				}

				if (item.compareTo(items.get(swapIndex)) < 0) {
					Swap (item,items.get(swapIndex));
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
			T parentItem = items.get(parentIndex);
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
		items.set(itemA.getIndex(), itemB);
		items.set(itemB.getIndex(), itemA);
		int itemAIndex = itemA.getIndex();
		itemA.setIndex(itemB.getIndex());
		itemB.setIndex(itemAIndex);
	}

	public boolean isEmpty() {
		if (items.size() == 0) {
			return true;
		}
		return false;
	}

}

