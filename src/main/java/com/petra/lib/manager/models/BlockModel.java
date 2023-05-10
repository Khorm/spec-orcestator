package com.petra.lib.manager.models;

import com.petra.lib.manager.block.SourceSignalModel;
import com.petra.lib.signal.SignalModel;
import com.petra.lib.variable.factory.VariableModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString
public class BlockModel {
    /**
     * Block id
     */
    Long id;

    /**
     * Block name
     */
    String name;

    /**
     * Block execution variables
     */
    Collection<VariableModel> variables;

    /**
     * block requires sources
     */
    Collection<SourceSignalModel> sources;

    /**
     * Starter signals
     */
    Collection<SignalModel> requestSignals;

    /**
     * AnswerSignal
     */
    SignalModel responseSignal;
}
