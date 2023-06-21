package com.example.androidnoa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatComparator implements Comparator<Chat> {
    private SimpleDateFormat dateFormat;

    public ChatComparator() {
        dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
    }

    @Override
    public int compare(Chat chat1, Chat chat2) {
        List<Message> messages1 = chat1.getMessages();
        List<Message> messages2 = chat2.getMessages();

        // Check if messages lists are null or empty
        if (messages1 == null || messages1.isEmpty()) {
            if (messages2 == null || messages2.isEmpty()) {
                return 0; // Both lists are null or empty, consider them equal
            } else {
                return 1; // First list is null or empty, second list is not, consider first less than second
            }
        } else if (messages2 == null || messages2.isEmpty()) {
            return -1; // Second list is null or empty, first list is not, consider first greater than second
        }

        // Get the last messages from the lists
        Message lastMessage1 = messages1.get(messages1.size() - 1);
        Message lastMessage2 = messages2.get(messages2.size() - 1);


        // Compare the parsed created dates
        if (lastMessage1 != null && lastMessage2 != null) {
            return compareBySubstring(lastMessage1.getCreated(),lastMessage2.getCreated());
        } else if (lastMessage1 == null && lastMessage2 != null) {
            return -1; // First date is null, consider it less than the second date
        } else if (lastMessage1 != null && lastMessage2 == null) {
            return 1; // Second date is null, consider it less than the first date
        }

        // Both dates are null, consider them equal
        return 0;
    }

    public int compareBySubstring(String first, String second) {
        //Comparing by year
        String first_tmp = first.substring(15);
        String second_tmp = second.substring(15);
        try {
            int year1 = Integer.parseInt(first_tmp);
            int year2 = Integer.parseInt(second_tmp);
            if (year1 > year2) {
                return -1;
            } else if (year2 > year1) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Comparing by month
        first_tmp = first.substring(13, 15);
        second_tmp = second.substring(13, 15);
        try {
            int month1 = Integer.parseInt(first_tmp);
            int month2 = Integer.parseInt(second_tmp);
            if (month1 > month2) {
                return -1;
            } else if (month2 > month1) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Comparing by day
        first_tmp = first.substring(9, 11);
        second_tmp = second.substring(9, 11);
        try {
            int day1 = Integer.parseInt(first_tmp);
            int day2 = Integer.parseInt(second_tmp);
            if (day1 > day2) {
                return -1;
            } else if (day2 > day1) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Comparing by hour
        first_tmp = first.substring(0, 2);
        second_tmp = second.substring(0, 2);
        try {
            int hour1 = Integer.parseInt(first_tmp);
            int hour2 = Integer.parseInt(second_tmp);
            if (hour1 > hour2) {
                return -1;
            } else if (hour2 > hour1) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Comparing by minute
        first_tmp = first.substring(3, 5);
        second_tmp = second.substring(3, 5);
        try {
            int min1 = Integer.parseInt(first_tmp);
            int min2 = Integer.parseInt(second_tmp);
            if (min1 > min2) {
                return -1;
            } else if (min2 > min1) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Comparing by second
        first_tmp = first.substring(6, 8);
        second_tmp = second.substring(6, 8);
        try {
            int sec1 = Integer.parseInt(first_tmp);
            int sec2 = Integer.parseInt(second_tmp);
            if (sec1 > sec2) {
                return -1;
            } else if (sec2 > sec1) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
