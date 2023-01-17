package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 *
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<>();
		tail = new LLNode<>();
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element )
	{
		// TODO: Implement this method
		add(size,element); // add at the end
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index)
	{
		LLNode<E> node = getNode(index);
		return node != null ? node.data : null;
	}

	private LLNode<E> getNode(int index) {
		checkBoundary(index);
		int i = 0;
		LLNode<E> res = head.next;
		while(i < index) {
			res = res.next;
			i++;
		}
		return res;
	}

	/**
	 * Add an element to the list at the specified index.
	 * All other "add" method will call this. Why? cause we want to control size in one place only.
	 *   Having multiple add implementation means having to managed the size in multiple places.
	 * @param index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element )
	{
		// TODO: Implement this method
		checkBoundaryForAdd(index);
		LLNode<E> toInsert = new LLNode<>(element);
		int i = 0;
		if(size == 0) {
			head.next = toInsert;
			toInsert.prev = head;
			toInsert.next = tail;
			tail.prev = toInsert;
		}
		else {
			LLNode<E> tmp = head;
			if(index == size) {
				tmp = tail.prev;
			}
			else {
				while(i < index) {
					tmp = tmp.next;
					i++;
				}
			}
			tmp.next.prev = toInsert;
			toInsert.next = tmp.next;
			toInsert.prev = tmp;
			tmp.next = toInsert;
		}
		size++;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element)
	{
		LLNode<E> node = getNode(index);
		final E replaced = node.data;
		node.data = element;
		return replaced;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 *
	 */
	public E remove(int index)
	{
		// TODO: Implement this method
		LLNode<E> toRemove = getNode(index);
		LLNode<E> nodeBefore = toRemove.prev;
		LLNode<E> nodeAfter = toRemove.next;
		nodeBefore.next = toRemove.next;
		nodeAfter.prev = nodeBefore;
		toRemove.prev = null;
		toRemove.next = null;

		size--;
		return toRemove.data;
	}


	/** Return the size of the list */
	public int size()
	{
		// TODO: Implement this method
		return size;
	}


	/**
	 * Check if the given index (for get, remove operation) is valid, ie: >= 0 and < size
	 * @param index
	 * @throws IndexOutOfBoundsException
	 */
	private void checkBoundary(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
	}
	/**
	 * Check if the given index for add at index operation) is valid, ie: >= 0 and <= size
	 * @param index
	 * @throws IndexOutOfBoundsException
	 */
	private void checkBoundaryForAdd(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index > size)
			throw new IndexOutOfBoundsException();
	}
}

class LLNode<E>
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e)
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	public LLNode()
	{

	}

	@Override
	public String toString() {
		return "LLNode{" +
				"prev=" + (prev != null) +
				", next=" + (next != null)  +
				", data=" + data +
				'}';
	}
}
