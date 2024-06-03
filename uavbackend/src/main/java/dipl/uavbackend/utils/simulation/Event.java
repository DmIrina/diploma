package dipl.uavbackend.utils.simulation;


abstract class Event implements Comparable<Event> {
    private double time;

    public Event(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }

    public abstract void process();
}


