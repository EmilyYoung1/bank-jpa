package org.example;

public class BankException extends Exception {
    public final Type type;

    public BankException(Type type) {
        this.type = type;
    }

    static BankException invalidAmount() {
        return new BankException(Type.InvalidAmount);
    }

    static BankException unauthorizedOperation() {
        return new BankException(Type.UnauthorizedOperation);
    }

    static BankException insufficientFunds() {
        return new BankException(Type.InsufficientFunds);
    }

    static BankException unknownKey() {
        return new BankException(Type.UnknownKey);
    }

    public enum Type {
        InvalidAmount,
        UnauthorizedOperation,
        UnknownKey,
        InsufficientFunds
    }

}
