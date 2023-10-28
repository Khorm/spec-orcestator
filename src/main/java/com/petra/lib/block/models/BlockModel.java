package com.petra.lib.block.models;

import com.petra.lib.block.VersionBlockId;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.XXXXXXsignal.model.Version;
import com.petra.lib.variable.pure.PureVariable;
import com.petra.lib.variable.mapper.MapperVariableModel;
import com.petra.lib.state.variable.model.VariableGroupModel;
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

    Version version;

    BlockType blockType;

    /**
     * Block name
     */
    String name;

    Collection<SignalId> initSignals;

    /**
     * Block execution variables
     */
    Collection<PureVariable> pureVariables;

    /**
     * Groups for load variables variables
     */
    Collection<VariableGroupModel> variableGroups;

    /**
     * Mappers for response signals
     */
    Collection<MapperVariableModel> mappedVariablesToResponseSignals;

    public VersionBlockId getBlockId() {
        return new VersionBlockId(id, version);
    }

//    /**
//     * block requires sources
//     */
//    Collection<SourceSignalModel> sources;
//
//    /**
//     * Starter signals
//     */
//    Collection<SignalModel> requestSignals;
//
//    /**
//     * AnswerSignal
//     */
//    SignalModel responseSignal;
}
