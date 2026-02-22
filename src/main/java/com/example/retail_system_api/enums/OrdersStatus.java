package com.example.retail_system_api.enums;

public enum OrdersStatus {
    PENDING  ,      // cart / ordering
    CONFIRMED  ,    // stock deducted, waiting payment
    COMPLETED   ,   // payment success
    CANCELLED

    }
