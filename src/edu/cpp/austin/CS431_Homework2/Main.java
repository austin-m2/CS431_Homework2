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

        int test = first_come_first_served(master_list);
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

        LinkedList<SeekRequest> queue = new LinkedList<>();
        LinkedList<SeekRequest> finished_list = new LinkedList<>();

        for (index = 0; index < 10; index++) {
            queue.add(new SeekRequest(time, master_list[index]));
        }

        while (index < master_list.length && !queue.isEmpty()) {
            //add new requests to the queue if necessary
            if (queue.size() < 5 && index < ) {

            }
        }







        return 0;
    }

}
