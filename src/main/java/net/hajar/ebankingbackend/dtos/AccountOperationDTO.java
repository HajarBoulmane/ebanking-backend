// dtos/AccountOperationDTO.java
package net.hajar.ebankingbackend.dtos;

import lombok.Data;
import net.hajar.ebankingbackend.enums.OperationType;
import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    private OperationType type;
}