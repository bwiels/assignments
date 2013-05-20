import java.util.PriorityQueue;


public class DoublyLinkedList {

  private Node front;
	private Node end;
	int size;
	
	public DoublyLinkedList(){
		front= null;
		end= null;
		size = 0;
	}
	
	//adds items to the front of the list
	//used for the initial loading of points
	public void add(Node data){
		
		if (front == null)
		{
			front = data;
			end = data;
		}else if(front == end)
		{
			front.setNext(data);
			data.setPrev(front);
			end = data;
			
		}else{
			
			data.setPrev(end);
			end.setNext(data);
			end = data;
		}
		size++;
	}
	
	//since the priority queue data item has a pointer to its
	//corresponding item in the LL, we use this method to
	//insert directly as opposed to walking the list to search
	//for the correct spot.
	public void insert(Node newNode, Node ptr)
	{
		
		newNode.setNext(ptr.getNext());
		newNode.setPrev(ptr);
		ptr.getNext().setPrev(newNode);
		ptr.setNext(newNode);
		size++;
		
	}
	
	public double calcList(){
		
		Node curr = front;
		Node next = front.getNext();
		double total=0;
		
		while(next != null){
			
			total = total+Main.calculateArea(curr.getX(), next.getX());
			curr = next;
			next = next.getNext();
		}
		return total;
	}

	public int getSize(){
		return size;
	}
}
