package com.tournament.restaurant.data.service.impl;

import com.tournament.restaurant.data.entities.ConfirmedOrder;
import com.tournament.restaurant.data.repositories.ConfirmOrderRepository;
import com.tournament.restaurant.data.service.ConfirmOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ConfirmOrderImpl implements ConfirmOrderService {

    @Autowired
    private final ConfirmOrderRepository confirmOrderRepository;

    public ConfirmOrderImpl(ConfirmOrderRepository confirmOrderRepository) {
        this.confirmOrderRepository = confirmOrderRepository;
    }


    @Override
    public void postConfirmOrder(ConfirmedOrder confirmedOrder) {
        confirmOrderRepository.save(confirmedOrder);
    }

    @Override
    public List<ConfirmedOrder> getConfirmOrderByDate(String date) {
        return confirmOrderRepository.findAllByConfirmDateContaining(date);
    }

    @Override
    public List<ConfirmedOrder> getAllConfirms(){
        return confirmOrderRepository.getAllOrdersDateOneRowPerDuplicate();
    }


}
