package hexlet.code.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateService {

    public static String getFormattedCreatedAt(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return createdAt.format(formatter);
    }
}
