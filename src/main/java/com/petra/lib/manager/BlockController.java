package com.petra.lib.manager;

import com.petra.lib.block.ExecutingBlock;
import com.petra.lib.signal.ConsumerSignal;
import com.petra.lib.signal.ProducerSignal;
import com.petra.lib.signal.model.Version;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Deprecated
public class BlockController {

    @EqualsAndHashCode.Include
    Long blockId;

    @EqualsAndHashCode.Include
    Version version;

    Collection<ConsumerSignal> consumerSignals;
    Collection<ProducerSignal> producerSignals;
    ExecutingBlock executingBlock;


    void execute

}
