package com.tengnat.elasticsearch.service;

import com.tengnat.elasticsearch.from.FirstOrderIMFrom;

import java.util.List;

public interface OrderService {
    void addOrder(FirstOrderIMFrom orderIMFrom);

    List findByToOrFromAccount(String fromAccount, String to);

    String findClientIdByToOrFromAccount(String fromAccount, String to);

    String findEDIClientId();
}
