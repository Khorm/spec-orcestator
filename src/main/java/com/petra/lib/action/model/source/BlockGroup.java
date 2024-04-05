package com.petra.lib.action.model.source;

import lombok.Getter;

import java.util.List;

@Getter
public class BlockGroup {
    private List<BlockSourceHandlerModel> sourceHandlerModels;
    private List<BlockRemoteSourceModel> blockRemoteSourceModels;
}
