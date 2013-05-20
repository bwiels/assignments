
public class Node {

  private double x;
	private QueueData ptr;
	private Node next;
	private Node prev;
	
	public Node(double x, QueueData ptr, Node next, Node prev)
	{
		this.next = next;
		this.prev = prev;
		this.x = x;
		this.ptr = ptr;
	}
	public void setNext(Node next)
	{
		this.next = next;
	}
	public Node getNext()
	{
		return next;
	}
	public Node getPrev()
	{
		return prev;
	}
	public void setPrev(Node prev)
	{
		this.prev = prev;
	}
	public double getX(){
		
		return x;
	}
	public void setX(double x)
	{
		this.x = x;
	}
	public QueueData getPtr() 
	{
		return ptr;
	}
	public void setPtr(QueueData ptr) 
	{
		this.ptr = ptr;
	}
	public String toString()
	{
		return Double.toString(x);
	}
}
