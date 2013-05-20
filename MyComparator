import java.util.Comparator;

//This comparator reverses the order of PriorityQueue, making it a max heap as opposed to a min heap.
public class MyComparator implements Comparator<QueueData>{

  @Override
	public int compare(QueueData arg0, QueueData arg1) {
		if(arg0.getErrorEstimate() < arg1.getErrorEstimate())
			return 1;
		else if(arg0.getErrorEstimate() > arg1.getErrorEstimate())
			return -1;
		else
			return 0;
	}

}
