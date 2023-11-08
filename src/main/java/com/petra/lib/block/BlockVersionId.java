package com.petra.lib.block;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class BlockVersionId {
    @Getter
    Long blockId;
    Version version;
}
