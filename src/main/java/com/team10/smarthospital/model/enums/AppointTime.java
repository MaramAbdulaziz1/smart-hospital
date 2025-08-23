package com.team10.smarthospital.model.enums;

import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum AppointTime {
    Appoint_1(1, "09:00", "09:30"),
    Appoint_2(2, "09:30", "10:00"),
    Appoint_3(3, "10:00", "10:30"),
    Appoint_4(4, "10:30", "11:00"),
    Appoint_5(5, "11:00", "11:30"),
    Appoint_6(6, "11:30", "12:00"),
    Appoint_7(7, "14:00", "14:30"),
    Appoint_8(8, "14:30", "15:00"),
    Appoint_9(9, "15:00", "15:30"),
    Appoint_10(10, "15:30", "16:00"),
    Appoint_11(11, "16:00", "16:30"),
    Appoint_12(12, "16:30", "17:00");

    public static final Map<Integer, AppointTime> AVAILABLE_TIMES =
            Arrays.stream(values())
                    .collect(
                            Collectors.toUnmodifiableMap(
                                    AppointTime::getCode, Function.identity()));
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final Integer code;
    private final LocalTime startTime;
    private final LocalTime endTime;

    AppointTime(Integer code, String startTimeStr, String endTimeStr) {
        this.code = code;
        this.startTime = LocalTime.parse(startTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
        this.endTime = LocalTime.parse(endTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static String getAppointTime(Integer code) {
        for (AppointTime appointTime : AppointTime.values()) {
            if (appointTime.getCode().equals(code)) {
                return appointTime.getStartTimeStr();
            }
        }
        return null;
    }

    public String getStartTimeStr() {
        return startTime.format(OUTPUT_FORMATTER);
    }

    public String getEndTimeStr() {
        return endTime.format(OUTPUT_FORMATTER);
    }
}
