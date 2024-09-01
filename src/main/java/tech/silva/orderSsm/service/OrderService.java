package tech.silva.orderSsm.service;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.silva.orderSsm.entity.OrderEvents;
import tech.silva.orderSsm.entity.OrderStates;

@Service
public class OrderService {

    private StateMachine<OrderStates, OrderEvents> stateMachine;

    private StateMachineFactory<OrderStates, OrderEvents> stateMachineFactory;

    public OrderService(StateMachine<OrderStates, OrderEvents> stateMachine, StateMachineFactory<OrderStates, OrderEvents> stateMachineFactory) {
        this.stateMachine = stateMachine;
        this.stateMachineFactory = stateMachineFactory;
    }

    public void newOrder(){
        initOrderSaga();
        validateOrder();
    }
    private void initOrderSaga() {
        System.out.println("Initializing order saga");
        stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.startReactively();
        System.out.println("Final state " + stateMachine.getState().getId());
    }

    private void validateOrder() {
        System.out.println("Validating order");
        stateMachine.sendEvent(Mono.just(
                        MessageBuilder.withPayload(OrderEvents.VALIDATE).build()))
                .subscribe(result -> System.out.println(result.getResultType()));
        System.out.println("Final state " + stateMachine.getState().getId());
    }

    private void payOrder() {
        System.out.println("Paying order");
        stateMachine.sendEvent(Mono.just(
                        MessageBuilder.withPayload(OrderEvents.PAY).build()))
                .subscribe(result -> System.out.println(result.getResultType()));
        System.out.println("Final state " + stateMachine.getState().getId());
    }

    private void shipOrder() {
        System.out.println("Shipping order");
        stateMachine.sendEvent(Mono.just(
                        MessageBuilder.withPayload(OrderEvents.SHIP).build()))
                .subscribe(result -> System.out.println(result.getResultType()));
        System.out.println("Final state " + stateMachine.getState().getId());
    }

    private void completeOrder() {
        System.out.println("Completing order");
        stateMachine.sendEvent(Mono.just(
                        MessageBuilder.withPayload(OrderEvents.COMPLETE).build()))
                .subscribe(result -> System.out.println(result.getResultType()));
        System.out.println("Final state " + stateMachine.getState().getId());
        stopOrderSaga();
    }

    private void stopOrderSaga() {
        System.out.println("Stopping order");
        stateMachine.stopReactively().subscribe();
    }
}
