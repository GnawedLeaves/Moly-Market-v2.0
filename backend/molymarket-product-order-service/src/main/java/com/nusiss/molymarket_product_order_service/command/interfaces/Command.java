package com.nusiss.molymarket_product_order_service.command.interfaces;

public interface Command {
    void execute();
    void undo();
}
