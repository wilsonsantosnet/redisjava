package com.example.demo.config;

import java.time.LocalDateTime;

public class DatetimeSingleton {
    private LocalDateTime datetime;

    public DatetimeSingleton() {
        this.datetime = LocalDateTime.now();
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

}