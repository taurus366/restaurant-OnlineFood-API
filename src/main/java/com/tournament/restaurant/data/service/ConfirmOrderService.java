package com.tournament.restaurant.data.service;

import com.tournament.restaurant.data.entities.ConfirmedOrder;

public interface ConfirmOrderService {
    void postConfirmOrder(ConfirmedOrder confirmedOrder);
}
