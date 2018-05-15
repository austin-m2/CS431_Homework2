package edu.cpp.austin.CS431_Homework2;

public class SeekRequest {
    private int time_entered;
    private int time_finished;
    private int cylinder;
    private int delay;

    public SeekRequest(int time, int cylinder) {
        time_entered = time;
        this.cylinder = cylinder;
    }

    public int get_delay() {
        return delay;
    }

    public void set_delay() {
        delay = time_finished - time_entered;
    }

    public double get_score() {
        return delay * Math.sqrt((double) delay);
    }

    public int get_time_satisfied() {
        return time_finished;
    }

    public void set_time_satisfied(int time_satisfied) {
        this.time_finished = time_satisfied;
    }

    public int get_cylinder() {
        return cylinder;
    }
}
