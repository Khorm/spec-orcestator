package com.petra.lib.manager.block;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ProcessVariableDto {

    private Long id;

    @Getter
    private String value;

    public ProcessVariableDto(Long id, String json) {
        this.id = id;
        this.value = json;
    }
}
