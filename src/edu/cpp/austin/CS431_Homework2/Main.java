package edu.cpp.austin.CS431_Homework2;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner sc = new Scanner(System.in);
        int size;

        System.out.println("How many seeks to generate?");
        size = sc.nextInt();

        int[] master_list = generate_master_list(size);

        int test = shortest_seek_first(master_list);
        System.out.println(test);
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

}
