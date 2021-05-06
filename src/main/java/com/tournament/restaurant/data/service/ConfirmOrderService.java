package com.tournament.restaurant.data.service;

import com.tournament.restaurant.data.entities.ConfirmedOrder;

import java.util.List;
import java.util.Set;

public interface ConfirmOrderService {
    void postConfirmOrder(ConfirmedOrder confirmedOrder);
    List<ConfirmedOrder> getConfirmOrderByDate(String date);
    List<ConfirmedOrder> getAllConfirms();
}
