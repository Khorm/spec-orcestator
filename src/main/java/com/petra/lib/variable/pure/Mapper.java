package com.petra.lib.variable.pure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class Mapper {

    private final Map<Long, PureVariable> consumerIdsBySourceIds ;

    Mapper(Collection<PureVariable> pureVariables){
        consumerIdsBySourceIds = new HashMap<>();
        for (PureVariable pureVariable : pureVariables){
            for (Long sourceVariablesId : pureVariable.getSourceVariableIds()){
                consumerIdsBySourceIds.put(sourceVariablesId, pureVariable);
            }
        }
    }

    PureVariable parseSourceIdToLocalId(Long sourceId){
        return consumerIdsBySourceIds.get(sourceId);
    }

}
