package net.exotia.plugins.market.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class TimeUtil {
    public static String formatDate(Long time) {
        DateFormat format = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        return format.format(time);
    }

    public static String formatTime(Long time) {
        if (time <= 0L) {
            return "0 sek.";
        } else {
            long days = TimeUnit.MILLISECONDS.toDays(time);
            time = time - TimeUnit.DAYS.toMillis(days);
            long hours = TimeUnit.MILLISECONDS.toHours(time);
            time = time - TimeUnit.HOURS.toMillis(hours);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
            time = time - TimeUnit.MINUTES.toMillis(minutes);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(time);

            StringBuilder sb = new StringBuilder();
            if (days > 0L) {
                sb.append(days).append(" dni. ");
            }

            if (hours > 0L) {
                sb.append(hours).append(" godz. ");
            }

            if (minutes > 0L) {
                sb.append(minutes).append(" min. ");
            }

            if (seconds > 0L) {
                sb.append(seconds).append(" sek. ");
            }

            return sb.toString();
        }
    }

    public static long timeFromString(String string) {
        if (string != null && !string.isEmpty()) {
            Stack type = new Stack();
            StringBuilder value = new StringBuilder();
            boolean calc = false;
            long time = 0L;
            char[] charArray;
            int length = (charArray = string.toCharArray()).length;

            for(int j = 0; j < length; ++j) {
                char c = charArray[j];
                switch(c) {
                    case 'd':
                    case 'h':
                    case 'm':
                    case 's':
                        if (!calc) {
                            type.push(c);
                            calc = true;
                        }

                        if (calc) {
                            try {
                                long i = (long)Integer.valueOf(value.toString());
                                switch((Character)type.pop()) {
                                    case 'd':
                                        time += i * 86400000L;
                                        break;
                                    case 'h':
                                        time += i * 3600000L;
                                        break;
                                    case 'm':
                                        time += i * 60000L;
                                        break;
                                    case 's':
                                        time += i * 1000L;
                                }
                            } catch (NumberFormatException var12) {
                                return time;
                            }
                        }

                        type.push(c);
                        calc = true;
                        break;
                    default:
                        value.append(c);
                }
            }

            return time;
        } else {
            return 0L;
        }
    }
}