package com.petra.lib.XXXXXXsignal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Deprecated
class SignalId {
    Long id;
    Integer majorVersion;
}
