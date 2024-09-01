package tech.silva.orderSsm.config;

import jdk.jfr.Name;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigBuilder;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import tech.silva.orderSsm.entity.OrderEvents;
import tech.silva.orderSsm.entity.OrderStates;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states
                .withStates()
                .initial(OrderStates.NEW)
                .states(EnumSet.allOf(OrderStates.class))
                .end(OrderStates.COMPLETED)
                .end(OrderStates.CANCELLED);

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions
                .withExternal().source(OrderStates.NEW).target(OrderStates.VALIDATED).event(OrderEvents.VALIDATE)
                .action(validateOrderAction())
                .and()
                .withExternal().source(OrderStates.VALIDATED).target(OrderStates.PAID).event(OrderEvents.PAY)
                .action(payOrderAction())
                .and()
                .withExternal().source(OrderStates.PAID).target(OrderStates.SHIPPED).event(OrderEvents.SHIP)
                .action(shipOrderAction())
                .and()
                .withExternal().source(OrderStates.SHIPPED).target(OrderStates.COMPLETED).event(OrderEvents.COMPLETE)
                .and()
                .withExternal().source(OrderStates.VALIDATED).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL)
                .and()
                .withExternal().source(OrderStates.PAID).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL);
    }

    @Bean
    private Action<OrderStates, OrderEvents> shipOrderAction() {
        return context -> {
            System.out.println("Shipping order");
        };
    }

    @Bean
    private Action<OrderStates, OrderEvents> payOrderAction() {
        return context -> {
            System.out.println("Paying order");
        };
    }

    @Bean
    private Action<OrderStates, OrderEvents> validateOrderAction() {
        return context -> {
            System.out.println("Validating order");
        };
    }
}
