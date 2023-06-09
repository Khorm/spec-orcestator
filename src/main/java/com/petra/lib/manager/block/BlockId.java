package com.petra.lib.manager.block;

import com.petra.lib.manager.Id;
import com.petra.lib.signal.model.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BlockId {
  Long blockId;
  Version version;
}
