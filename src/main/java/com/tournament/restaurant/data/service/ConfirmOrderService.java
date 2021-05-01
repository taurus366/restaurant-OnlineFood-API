package com.tournament.restaurant.data.service;

import com.tournament.restaurant.data.entities.ConfirmedOrder;

import java.util.List;

public interface ConfirmOrderService {
    void postConfirmOrder(ConfirmedOrder confirmedOrder);
    List<ConfirmedOrder> getConfirmOrderByDate(String date);
}
