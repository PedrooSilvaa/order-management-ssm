package tech.silva.orderSsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.silva.orderSsm.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/new")
    public String newOrder(){
        orderService.newOrder();
        return "new order";
    }
    @PostMapping("/pay")
    public String payOrder(){
        orderService.payOrder();
        return "new pay";
    }
    @PostMapping("/ship")
    public String shipOrder(){
        orderService.shipOrder();
        return "new ship";
    }
    @PostMapping("/complete")
    public String completeOrder(){
        orderService.completeOrder();
        return "new complete";
    }

}
