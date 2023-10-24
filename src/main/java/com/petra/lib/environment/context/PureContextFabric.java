package com.petra.lib.environment.context;

public class PureContextFabric {

    ActivityContext createActivityContext(ActivityContext currentContext){
        return new ActivityContext(
                currentContext.getBusinessId(),

        )
    }
}
