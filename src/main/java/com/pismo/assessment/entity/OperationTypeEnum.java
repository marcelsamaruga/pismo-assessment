package com.pismo.assessment.entity;

import com.pismo.assessment.exception.InvalidOperationTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum OperationTypeEnum {

    NORMAL_PURCHASE(1L, "Normal Purchase", (amount) -> amount.negate()),
    PURCHASE_INSTALLMENTS(2L, "Purchase with installments", (amount) -> amount.negate()),
    WITHDRAWAL(3L, "Withdrawal", (amount) -> amount.negate()),
    CREDIT_VOUCHER(4L, "Credit Voucher", (amount) -> amount);

    private Long id;
    private String description;
    private Function<BigDecimal, BigDecimal> amountRule;

    public static OperationTypeEnum findById(Long id) {
        return Arrays.stream(values())
                .filter(type -> type.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new InvalidOperationTypeException("Operation Type " + id + " not found"));

    }
}
