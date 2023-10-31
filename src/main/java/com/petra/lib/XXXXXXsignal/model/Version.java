package com.petra.lib.XXXXXXsignal.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public class Version {
    Integer major;

    @EqualsAndHashCode.Exclude
    Integer minor;

    @EqualsAndHashCode.Exclude
    Integer patch;

    public String getVersionName(){
        return major.toString() + "." + minor.toString() + "." + patch.toString();
    }
}
