package com.petra.lib.block;

import com.petra.lib.XXXXXXsignal.model.Version;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class VersionId {
    Long blockId;
    Version version;
}
