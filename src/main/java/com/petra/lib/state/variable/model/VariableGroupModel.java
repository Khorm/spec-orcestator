package com.petra.lib.state.variable.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Set;

/**
 * Model contains a group of variables for each init signal in block.
 * Variables in each group can load in parallel and does not depend on each other in this group.
 * Also, it contains sets of group ids, which have a dependency to this group like parent or a child.
 *
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariableGroupModel {
    Long groupId;

    /**
     * Parent groups. Variables of this group can depend on those group variables.
     */
    Set<Long> parentGroups;

    /**
     * Child groups. Those variables can depend on this group variables/
     */
    Set<Long> childGroups;


    Collection<SourceLoaderModel> sourceLoaders;

}
