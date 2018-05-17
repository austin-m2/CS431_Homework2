//Austin Morris
package edu.cpp.austin.CS431_Homework2;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int size;

        System.out.println("How many seeks to generate?");
        size = sc.nextInt();

        int[] master_list = generate_master_list(size);

        System.out.println("\nFirst come first serve:");
        int first_come = first_come_first_served(master_list);
        System.out.println("Total time:    " + first_come);

        System.out.println("\nShortest seek first:");
        int shortest_seek = shortest_seek_first(master_list);
        System.out.println("Total time:    " + shortest_seek);

        System.out.println("\nElevator:");
        int elevator = elevator(master_list);
        System.out.println("Total time:    " + elevator);

        System.out.println("\nModified ssf:");
        int modified_ssf = modified_ssf(master_list);
        System.out.println("Total time:    " + modified_ssf);

    }

    public static int[] generate_master_list(int size) {
        Random random = new Random(System.currentTimeMillis());
        int[] list = new int[size];
        for (int i = 0; i < size; i++) {
            list[i] = random.nextInt(100) + 1;
        }
        return list;
    }

    public static int first_come_first_served(int[] master_list) {
        int arm_position = 50;
        int total_time = 0;
        int difference;

        for(int i = 0; i < master_list.length; i++) {
            difference = Math.abs(arm_position - master_list[i]);
            total_time += difference;
            arm_position = master_list[i];
        }

        return total_time;
    }

    public static int shortest_seek_first(int[] master_list) {
        int time = 0;
        int cylinder = 50;
        int index;
        double average_delay = 0;
        int    max_delay     = 0;
        double average_score = 0;
        double max_score     = 0;

        LinkedList<SeekRequest> queue = new LinkedList<>();
        LinkedList<SeekRequest> finished_list = new LinkedList<>();

        for (index = 0; index < 10; index++) {
            queue.add(new SeekRequest(time, master_list[index]));
        }

        while (index < master_list.length || !queue.isEmpty()) {

            //add new requests to the queue if necessary
            if (queue.size() < 5) {
                for (int i = index ; index < i + 10; index++) {
                    if (index > master_list.length - 1) { break; }
                    queue.add(new SeekRequest(time, master_list[index]));
                }
            }

            //find next cylinder to process
            int next_cylinder = Integer.MAX_VALUE;
            int next_cylinder_index = Integer.MAX_VALUE;
            for (int i = 0; i < queue.size(); i++) {
                if (Math.abs(queue.get(i).get_cylinder() - cylinder) < Math.abs(next_cylinder - cylinder)) {
                    next_cylinder = queue.get(i).get_cylinder();
                    next_cylinder_index = i;
                }
            }

            //process next cylinder
            time += Math.abs(next_cylinder - cylinder);
            cylinder = next_cylinder;
            queue.get(next_cylinder_index).set_time_satisfied(time);
            finished_list.add(queue.get(next_cylinder_index));
            queue.remove(next_cylinder_index);

            //update tallies
            SeekRequest c = finished_list.getLast();
            average_delay += c.get_delay();
            average_score += c.get_score();
            if (c.get_delay() > max_delay) {
                max_delay = c.get_delay();
            }
            if (c.get_score() > max_score) {
                max_score = c.get_score();
            }
        }

        average_delay = average_delay / master_list.length;
        average_score = average_score / master_list.length;


        System.out.println("Average delay: " + average_delay);
        System.out.println("Average score: " + average_score);
        System.out.println("Maximum delay: " + max_delay);
        System.out.println("Maximum score: " + max_score);

        return time;
    }

    public static int elevator(int[] master_list) {
        int time = 0;
        int cylinder = 50;
        int index;
        int dir = 1;
        double average_delay = 0;
        int    max_delay     = 0;
        double average_score = 0;
        double max_score     = 0;

        LinkedList<SeekRequest> queue = new LinkedList<>();
        LinkedList<SeekRequest> finished_list = new LinkedList<>();

        for (index = 0; index < 10; index++) {
            queue.add(new SeekRequest(time, master_list[index]));
        }

        while (index < master_list.length || !queue.isEmpty()) {

            //add new requests to the queue if necessary
            if (queue.size() < 5) {
                for (int i = index ; index < i + 10; index++) {
                    if (index > master_list.length - 1) { break; }
                    queue.add(new SeekRequest(time, master_list[index]));
                }
            }

            //find next cylinder to process
            int next_cylinder = Integer.MAX_VALUE;
            int next_cylinder_index = Integer.MAX_VALUE;

            for (int i = 0; i < queue.size(); i++) {
                SeekRequest c = queue.get(i);
                if (Math.abs(c.get_cylinder() - cylinder) < Math.abs(next_cylinder - cylinder)
                        && (c.get_cylinder() - cylinder) * dir >= 0 ) {
                    next_cylinder = queue.get(i).get_cylinder();
                    next_cylinder_index = i;
                }

                //switch directions if we reach the end, and restart loop
                if (i == queue.size() - 1 && next_cylinder == Integer.MAX_VALUE) {
                    dir = dir * -1;
                    i = -1;
                }
            }

            //process next cylinder
            time += Math.abs(next_cylinder - cylinder);
            cylinder = next_cylinder;
            queue.get(next_cylinder_index).set_time_satisfied(time);
            finished_list.add(queue.get(next_cylinder_index));
            queue.remove(next_cylinder_index);

            //update tallies
            SeekRequest c = finished_list.getLast();
            average_delay += c.get_delay();
            average_score += c.get_score();
            if (c.get_delay() > max_delay) {
                max_delay = c.get_delay();
            }
            if (c.get_score() > max_score) {
                max_score = c.get_score();
            }
        }

        average_delay = average_delay / master_list.length;
        average_score = average_score / master_list.length;

        System.out.println("Average delay: " + average_delay);
        System.out.println("Average score: " + average_score);
        System.out.println("Maximum delay: " + max_delay);
        System.out.println("Maximum score: " + max_score);

        return time;
    }

    public static int modified_ssf(int[] master_list) {
        int time = 0;
        int cylinder = 50;
        int index;
        double average_delay = 0;
        int    max_delay     = 0;
        double average_score = 0;
        double max_score     = 0;
        final int CUTOFF     = 3000;

        LinkedList<SeekRequest> queue = new LinkedList<>();
        LinkedList<SeekRequest> finished_list = new LinkedList<>();

        for (index = 0; index < 10; index++) {
            queue.add(new SeekRequest(time, master_list[index]));
        }

        while (index < master_list.length || !queue.isEmpty()) {

            //add new requests to the queue if necessary
            if (queue.size() < 5) {
                for (int i = index ; index < i + 10; index++) {
                    if (index > master_list.length - 1) { break; }
                    queue.add(new SeekRequest(time, master_list[index]));
                }
            }

            //find next cylinder to process
            int next_cylinder = Integer.MAX_VALUE;
            int next_cylinder_index = Integer.MAX_VALUE;
            for (int i = 0; i < queue.size(); i++) {
                if (Math.abs(queue.get(i).get_cylinder() - cylinder) < Math.abs(next_cylinder - cylinder)) {
                    next_cylinder = queue.get(i).get_cylinder();
                    next_cylinder_index = i;
                }
            }

            //find cylinder with highest score
            double highest_score = 0;
            int highest_score_index = -1;
            for (int i = 0; i < queue.size(); i++) {
                if (queue.get(i).get_current_score(time) > highest_score) {
                    highest_score = queue.get(i).get_current_score(time);
                    highest_score_index = i;
                }
            }

            //use the highest scoring cylinder if it is high enough
            if(highest_score > CUTOFF) {
                next_cylinder = queue.get(highest_score_index).get_cylinder();
                next_cylinder_index = highest_score_index;
            }


            //process next cylinder
            time += Math.abs(next_cylinder - cylinder);
            cylinder = next_cylinder;
            queue.get(next_cylinder_index).set_time_satisfied(time);
            finished_list.add(queue.get(next_cylinder_index));
            queue.remove(next_cylinder_index);

            //update tallies
            SeekRequest c = finished_list.getLast();
            average_delay += c.get_delay();
            average_score += c.get_score();
            if (c.get_delay() > max_delay) {
                max_delay = c.get_delay();
            }
            if (c.get_score() > max_score) {
                max_score = c.get_score();
            }
        }

        average_delay = average_delay / master_list.length;
        average_score = average_score / master_list.length;

        System.out.println("Average delay: " + average_delay);
        System.out.println("Average score: " + average_score);
        System.out.println("Maximum delay: " + max_delay);
        System.out.println("Maximum score: " + max_score);

        //return time;
        return time;
    }
}
