package com.maintenance.entities.services;

import com.maintenance.entities.domain.dtos.ResponseDTO;
import com.maintenance.entities.domain.dtos.ShoppingDTO;

public interface IShoppingService {

    ResponseDTO shop(ShoppingDTO shoppingDTO);

}
