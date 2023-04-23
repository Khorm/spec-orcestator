package com.petra.lib.manager.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ConfigModel {
    Collection<ActionModel> actions;
    Collection<ActionModel> sources;
}
