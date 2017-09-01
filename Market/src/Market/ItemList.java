package Market;

import java.util.Comparator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ItemList implements Comparator<Item> {
	Node<Item> head;

	class Node<T> {
		public T item;
		Node<T> next;
		Node<T> prev;
		
		public Node(T item) {
			this.item = item;
			next = null;
			prev = null;		
		}

		public Node() {
			this.item = null;
			next = null;
			prev = null;
		}
	}
	
	public ItemList() {
		head = new Node<Item>();
		head.next = null;
		head.prev = null;
	}
	
	public class ItemIterator implements ListIterator<Item> {
		Node<Item> nodeIterator;
		int index;
		
		public ItemIterator() {
			index = -1;
			nodeIterator = head;
		}
		
		@Override
		public void add(Item item) {
			 Node<Item> newNode = new Node<Item>();
			 newNode.item = item;
			 newNode.next = nodeIterator.next;
			 newNode.prev = nodeIterator;
			 
			 nodeIterator.next = newNode;
			 if (newNode.next != null)
				 newNode.next.prev = newNode;
		}

		@Override
		public boolean hasNext() {
			if (nodeIterator.next != null)
				return true;
			else
				return false;
		}

		@Override
		public boolean hasPrevious() {
			if (nodeIterator.prev != null)
				return true;
			else 
				return false;
		}

		@Override
		public Item next() {
			if (nodeIterator.next != null) {
				nodeIterator = nodeIterator.next;
				index++;
				return nodeIterator.item;
			}
			else throw new NoSuchElementException();
		}	

		@Override
		public int nextIndex() {
			if (hasNext())
				return index + 1;
			else
				return -1;
		}

		@Override
		public Item previous() {
			if (nodeIterator.prev != null) {
				nodeIterator = nodeIterator.prev;
				index--;
				return nodeIterator.item;
			}
			else throw new NoSuchElementException();	
		}

		@Override
		public int previousIndex() {
			if (hasPrevious())
				return index - 1;
			else
				return -1;
		}

		@Override
		public void remove() {
			if (nodeIterator != null) {
				Node<Item> nextNode = nodeIterator.next;
				Node<Item> prevNode = nodeIterator.prev;
				prevNode.next = nextNode;
				if (nextNode != null) 
					nextNode.prev = prevNode;
				index--;
				nodeIterator = prevNode;
			}
			else throw new IllegalStateException();
		}

		@Override
		public void set(Item e) {
			if (nodeIterator != null)
				nodeIterator.item = e;
			else throw new IllegalStateException();
		}
	}
	
	public ItemIterator getIterator() {
		return new ItemIterator();
	}
	
	public boolean add(Item element) {
		ItemIterator iterator = new ItemIterator();

		while (iterator.hasNext()) {
			if (compare(element, iterator.next()) != 1) 
				break;
		}
		
		iterator.add(element);
		
		return true;
	}
	
	public Item getItem(int index) {
		ItemIterator iterator = new ItemIterator();
		
		while (iterator.nextIndex() != index)
			iterator.next();
		
		return iterator.next();
	}
	
	public boolean contains(Item item) {
		ItemIterator iterator = new ItemIterator();
		
		while (iterator.hasNext()) {
			if (iterator.next().getID() == item.getID())
				return true;
		}
		
		return false;
	}
	
	public Item remove(int index) {
		ItemIterator iterator = new ItemIterator();
		Item item = null;
		
		while (iterator.nextIndex() != index)
			item = iterator.next();
		
		iterator.remove();
		
		return item;
	}
	
	public boolean remove(Item item) {
		ItemIterator iterator = new ItemIterator();
		
		while (iterator.hasNext()) {
			if (iterator.next().getID() == item.getID()) {
				iterator.remove();
				return true;
			}
			
			iterator.next();
		}
		
		return false;
	}
	
	public boolean isEmpty() { 
		if (head.next == null)
			return true;
		else 
			return false;
	}
	
	public double getTotalPrice() {
		ItemIterator iterator = new ItemIterator();
		double totalPrice = 0;
		
		while (iterator.hasNext())
			totalPrice += iterator.next().getPrice();
		
		return totalPrice;
	}
	
	public int getSize() {
		ItemIterator iterator = new ItemIterator();
		int size = 0;
		
		while (iterator.hasNext())  {
			size += 1;
			iterator.next();
		}
		
		return size;
	}
	
	@Override
	public int compare(Item item1, Item item2) {
		if (item1.getID() < item2.getID())
			return 1;
		else 
			if (item1.getID() > item2.getID())
				return -1;
			else
				return 0;
	}
}
