package com.petra.lib.block;

import com.petra.lib.signal.model.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.List;


@EqualsAndHashCode
@Getter
public abstract class AbsExecutingBlock implements ExecutingBlock{
    private final Long blockId;
    @EqualsAndHashCode.Exclude
    private final List<Long> transactions;
    private final Version version;

    public AbsExecutingBlock(Long blockId, List<Long> transactions, Version version){
        this.blockId = blockId;
        this.transactions = Collections.unmodifiableList(transactions);
        this.version = version;
    }

    public List<Long> getTransactionsId(){
        return transactions;
    }
}
