
import java.util.Date;
import java.util.PriorityQueue;


/**
 * @author Brent Wieler
 * Student# 6742038
 * Comp 3170 - Assignement 1 Question 4
 *
 */

public class Main {

  public static void main(String args[])
	{
		
		PriorityQueue<QueueData> queue = new PriorityQueue<QueueData>(5, new MyComparator());	
		DoublyLinkedList newList = new DoublyLinkedList();
		initialLoad(queue,newList);
		runEstimate(queue, newList);
		
		double total = queue.poll().getAreaCal();
		while(!queue.isEmpty()){
			
			total = total+queue.poll().getAreaCal();
		}

		System.out.println("Number of points: "+newList.getSize());
		System.out.println("Tolerance in fitting: "+1e-07);
		System.out.println("Area from 0 to 2: "+total);
		System.out.println("Programmed by Brent Wieler");
		System.out.println("Date: "+new Date());
		System.out.println("End of Processing");

	}

	private static void initialLoad(PriorityQueue<QueueData> queue, DoublyLinkedList newList)
	{
		//we are going to begin with 5 points
		
		//load 0-0.5
		QueueData data = new QueueData(calculateErrorEstimate(0, 0.0000001, 0, 1, true, false), calculateArea(0, 0.0000001), null, 0, 0.0000001);
		Node node = new Node(0, data, null, null);
		data.setPtr(node);
		queue.offer(data);
		newList.add(node);
		
		//load 0.5-1
		data = new QueueData(calculateErrorEstimate(0.0000001, 1, 0, 1.5, false, false), calculateArea(0.0000001, 1), null, 0.0000001, 1);
		node = new Node(0.0000001, data, null, null);
		data.setPtr(node);
		queue.offer(data);
		newList.add(node);
		
		//load 1-1.5
		data = new QueueData(calculateErrorEstimate(1, 1.9999999, 0.0000001, 2, false, false), calculateArea(1, 1.9999999), null, 1, 1.9999999);
		node = new Node(1, data, null, null);
		data.setPtr(node);
		queue.offer(data);
		newList.add(node);
		
		//load 1.5-2
		data = new QueueData(calculateErrorEstimate(1.9999999, 2, 1, 0, false, true), calculateArea(1.9999999, 2), null, 1.9999999, 2);
		node = new Node(1.9999999, data, null, null);
		data.setPtr(node);
		queue.offer(data);
		newList.add(node);
		
		node = new Node(2, null, null, null);
		newList.add(node);
		
	}
	private static void runEstimate(PriorityQueue<QueueData> queue, DoublyLinkedList newList){
		
		//get largest error estimate
		QueueData current = queue.poll();
		Node nodePtr = current.getPtr();
		Node nodePtrPrev = nodePtr.getPrev();
		Node nodePtrNextFix = nodePtr.getNext();
		
		//some variables to help with fixing adjacent estimates
		boolean leftFix = false;
		boolean rightFix = false;
		QueueData left;
		QueueData right;
		Node nodePtrNext = nodePtr.getNext();
		if(nodePtrNext!= null)
			nodePtrNext = nodePtrNext.getNext();
		
		while(current.getErrorEstimate() > 1e-7)
		{
	
			//calculate new point to add into linked list
			double newPoint = (current.getIntervalStart() + current.getIntervalEnd())/2;
			
			//break out interval into two halves
			QueueData firstHalf = new QueueData(calculateErrorEstimate(current.getIntervalStart(), newPoint, nodePtrPrev!=null?nodePtrPrev.getX():0.0, current.getIntervalEnd(), nodePtrPrev==null?true:false, false), calculateArea(current.getIntervalStart(), newPoint), null, current.getIntervalStart(), newPoint );
			QueueData secondHalf = new QueueData(calculateErrorEstimate(newPoint, current.getIntervalEnd(), current.getIntervalStart(), nodePtrNext!=null?nodePtrNext.getX():0.0, false, nodePtrNext==null?true:false), calculateArea(newPoint, current.getIntervalEnd()), null, newPoint, current.getIntervalEnd() );
			
			//set queue object pointers inside Nodes
			nodePtr.setPtr(firstHalf);
			Node newNode = new Node(newPoint, secondHalf, null, null);	
			newList.insert(newNode, nodePtr);
			
			//set node pointers inside queue objects for fast insertion
			firstHalf.setPtr(nodePtr);
			secondHalf.setPtr(newNode);
			
			//re-introduce the two halves back into the queue
			queue.offer(firstHalf);
			queue.offer(secondHalf);
			
			//fix adjacent estimates
			if( nodePtrPrev != null)
			{
				if(nodePtrPrev != null){
					leftFix = queue.remove(nodePtrPrev.getPtr());
				}
				
				if(leftFix)
				{
					left = new QueueData(calculateErrorEstimate(nodePtrPrev.getX(), firstHalf.getIntervalStart(), nodePtrPrev.getPrev()!=null?nodePtrPrev.getPrev().getX():0.0, firstHalf.getIntervalEnd(), nodePtrPrev.getPrev()==null?true:false, false), calculateArea(nodePtrPrev.getX(), firstHalf.getIntervalStart()), null, nodePtrPrev.getX(), firstHalf.getIntervalStart());
					left.setPtr(nodePtrPrev);
					nodePtrPrev.setPtr(left);
					queue.offer(left);
				}
			}
			
			if(nodePtrNextFix.getNext()!= null)
			{
				if(nodePtrNextFix != null){
					rightFix = queue.remove(nodePtrNextFix.getPtr());
				}
				
				if(rightFix)
				{
					right = new QueueData(calculateErrorEstimate(nodePtrNextFix.getX(), nodePtrNextFix.getNext().getX(), secondHalf.getIntervalStart(), nodePtrNextFix.getNext().getNext()!=null?nodePtrNextFix.getNext().getNext().getX():0.0,false , nodePtrNextFix.getNext()==null?true:false), calculateArea(nodePtrNextFix.getX(), nodePtrNextFix.getNext().getX()), null, nodePtrNextFix.getX(), nodePtrNextFix.getNext().getX());
					right.setPtr(nodePtrNextFix);
					nodePtrNextFix.setPtr(right);
					queue.offer(right);
				}
			}

			//grab information for next iteration
			current = queue.poll();
			nodePtr = current.getPtr();
			nodePtrNextFix = nodePtr.getNext();
			nodePtrPrev = nodePtr.getPrev();
			nodePtrNext = nodePtr.getNext();
			if(nodePtrNext != null)
				nodePtrNext = nodePtrNext.getNext();
			
		}
		
		//while loop is done, reintroduce the queue item back into the heap since we did not do work on it.
		queue.offer(current);
	}

	
	private static double calculateErrorEstimate(double start, double end, double beforeStart, double afterEnd, boolean farLeft, boolean farRight){
		
		double a = 0.0;
		double aPrime = 0.0;
		double aDoublePrime = 0.0;
		
		a = ((end-start)*((getSinOfaPoint(end)+getSinOfaPoint(start))/2.0));
		
		if(!farRight)
			aPrime = ((afterEnd-start)*((getSinOfaPoint(afterEnd)+getSinOfaPoint(start))/2.0)) - ((afterEnd-end)*((getSinOfaPoint(afterEnd)+getSinOfaPoint(end))/2.0));
		if(!farLeft)
			aDoublePrime = ((end-beforeStart)*((getSinOfaPoint(end)+getSinOfaPoint(beforeStart))/2.0)) - ((start-beforeStart)*((getSinOfaPoint(start)+getSinOfaPoint(beforeStart))/2.0));

		if(farRight)
			return (Math.max(a, aDoublePrime) - Math.min(a, aDoublePrime));
		else if(farLeft)
			return (Math.max(a, aPrime) - Math.min(a, aPrime));
		else
			return (Math.max(Math.max(a, aPrime), aDoublePrime) - Math.min(Math.min(a,aPrime), aDoublePrime));
		
	}
	public static double calculateArea(double start, double end){
		
		double result = (end-start)*((getSinOfaPoint(end)+getSinOfaPoint(start))/2);
		
		return result;

	}
	private static double getSinOfaPoint(double x){
		
		if(x == 0.0)
			return 0.0;
			else
		return x*Math.sin(1/x);
	}

}
