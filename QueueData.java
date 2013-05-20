
public class QueueData {

  private double errorEstimate;
	private Node ptr;
	private double intervalStart;
	private double intervalEnd;
	private double areaCal;
	
	public QueueData(double errorEstimate, double areaCal, Node ptr, double intervalStart, double intervalEnd){
		this.errorEstimate = errorEstimate;
		this.ptr = ptr;
		this.intervalEnd = intervalEnd;
		this.intervalStart = intervalStart;
		this.areaCal = areaCal;
	}

	public double getErrorEstimate() {
		return errorEstimate;
	}

	public void setErrorEstimate(double errorEstimate) {
		this.errorEstimate = errorEstimate;
	}

	public double getAreaCal() {
		return areaCal;
	}

	public void setAreaCal(double areaCal) {
		this.areaCal = areaCal;
	}

	public double getIntervalStart() {
		return intervalStart;
	}

	public void setIntervalStart(double intervalStart) {
		this.intervalStart = intervalStart;
	}

	public double getIntervalEnd() {
		return intervalEnd;
	}

	public void setIntervalEnd(double intervalEnd) {
		this.intervalEnd = intervalEnd;
	}

	public Node getPtr() {
		return ptr;
	}

	public void setPtr(Node ptr) {
		this.ptr = ptr;
	}

	public String toString(){
		
		return Double.toString(errorEstimate);
	}
}
