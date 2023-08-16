package com.auditing.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {
    @NotBlank(message = "Account number should not be empty")
    String accountNumber;
    @DecimalMin(value = "0", inclusive = false, message = "Invalid Transfer amount")
    BigDecimal amount;
}
