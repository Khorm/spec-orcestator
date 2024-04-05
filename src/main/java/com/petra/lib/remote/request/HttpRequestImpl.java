package com.petra.lib.remote.request;

import com.petra.lib.block.BlockId;
import com.petra.lib.remote.model.BlockServiceModel;
import com.petra.lib.remote.response.ResponseDto;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.SignalId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


/**
 * Менеджер отправки сигнала к слушателю.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class HttpRequestImpl implements Request {

    SignalId signalId;
    RestTemplate restTemplate = new RestTemplate();
    BlockServiceModel sendModel;


    public ResponseDto send(UUID scenarioId, BlockId responseActionId,
                            VariablesContainer signalContainer, BlockId currentActionId) {
        RequestDto requestDto = new RequestDto(
                scenarioId,
                signalId,
                signalContainer,
                sendModel.getRequestBlockId(),
                sendModel.getRequestServiceName(),
                currentActionId,
                responseActionId
        );

        return restTemplate.postForObject("http://" + sendModel.getResponseServiceName() + "/execute",
                requestDto, ResponseDto.class);

    }

}
