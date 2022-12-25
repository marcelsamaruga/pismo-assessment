package com.pismo.assessment.util;

import com.pismo.assessment.dto.AccountDto;
import com.pismo.assessment.dto.TransactionDto;
import com.pismo.assessment.entity.Account;
import com.pismo.assessment.entity.OperationTypeEnum;
import com.pismo.assessment.entity.Transaction;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface Converter {

    Converter INSTANCE = Mappers.getMapper(Converter.class);

    @ValueMapping(source = "UNRECOGNIZED", target = MappingConstants.NULL)
    AccountDto toAccountDto(Account account);

    @ValueMapping(source = "UNRECOGNIZED", target = MappingConstants.NULL)
    @Mapping(target = "accountId", source = "account", qualifiedByName = "toAccountId")
    @Mapping(target = "operationTypeId", source = "operationType", qualifiedByName = "toOperationTypeId")
    TransactionDto toTransactionDto(Transaction account);

    @Named("toAccountId")
    default Long toAccountId(Account account) {
        return account.getAccountId();
    }

    @Named("toOperationTypeId")
    default Long toOperationTypeId(OperationTypeEnum operationType) {
        return operationType.getId();
    }
}
