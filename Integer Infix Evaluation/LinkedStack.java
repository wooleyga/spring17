/**
 * An implementation of the stack ADT using linked data.
 * 
 * @author Gavin Wooley
 *
 * @param <T>
 * 		The data type of the stack.
 */
public class LinkedStack<T> implements StackInterface<T> {
	private Node firstNode;
	
	/**
	 * Creates a linked stack.
	 */
	public LinkedStack(){
		firstNode = null;
	}
	
	/**
	 * Pushes data to the top of the stack.
	 * 
	 * @param newEntry
	 * 		The data to be pushed into the stack.
	 */
	@Override
	public void push(T newEntry) {
		Node temp = new Node(newEntry, firstNode);
		firstNode = temp;
	}

	/**
	 * Pops data off the top of the stack.
	 * 
	 * @return
	 * 		The popped data.
	 */
	@Override
	public T pop() {
		Node poppedNode = firstNode;
		firstNode = firstNode.next;
		return poppedNode.getData();
	}

	/**
	 * Peeks at the data at the top of the stack.
	 * 
	 * @return
	 * 		The data at the top of the stack.
	 */
	@Override
	public T peek() {
		return firstNode.getData();
	}
	
	/**
	 * Checks if the stack is empty.
	 * 
	 * @return
	 * 		True if empty, false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return firstNode == null;
	}

	/**
	 * Clears the stack.
	 */
	@Override
	public void clear() {
		firstNode = null;
	}
	
	/**
	 * An implementation of a node in linked data.
	 * 
	 * @author Gavin Wooley
	 *
	 */
	private class Node{
		private T data;
		private Node next;
		
		/**
		 * Creates a node with the given data and pointer to the next node.
		 * 
		 * @param data
		 * 		The data stored in the node
		 * @param next
		 * 		The reference to the next node
		 */
		private Node(T data, Node next){
			this.data = data;
			this.next = next;
		}
		
		/**
		 * Creates a node with the given data.
		 * 
		 * @param data
		 * 		The data stored in the node.
		 */
		private Node(T data){
			this.data = data;
		}
		
		/**
		 * Obtains the data stored in the node.
		 * 
		 * @return
		 * 		The data stored in the node
		 */
		private T getData(){
			return data;
		}
		
	}
}
