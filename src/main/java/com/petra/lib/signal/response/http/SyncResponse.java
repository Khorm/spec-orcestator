package com.petra.lib.signal.response.http;

import com.petra.lib.block.BlockId;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.signal.response.controller.SignalResponseHandler;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.dto.ResponseDto;
import com.petra.lib.signal.model.SignalModel;
import com.petra.lib.signal.response.ResponseSignal;
import com.petra.lib.signal.response.ResponseType;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import static org.springframework.web.servlet.function.RouterFunctions.route;

/**
 * Response for http request
 */
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SyncResponse implements ResponseSignal {

    /**
     * Handler for creating answer for request
     */
    SignalResponseHandler signalResponseHandler;
    /**
     * Асинхронный хранитель ответов для требования при ответе
     */
    AnswersMap answersMap = new AnswersMap();
    //    Version signalVersion;
    SignalId signalId;
    //    ObjectMapper objectMapper = new ObjectMapper();
    String signalName;

    public SyncResponse(SignalModel signalModel, ConfigurableListableBeanFactory beanFactory, SignalResponseHandler signalResponseHandler) {

        this.signalId = new SignalId(signalModel.getSignalId(), signalModel.getVersion().getMajor());
        this.signalName = signalModel.getSignalName() + "." + signalModel.getVersion().getVersionName();
        this.signalResponseHandler = signalResponseHandler;

        RouterFunction<ServerResponse> routerFunction = route().POST("/" + signalModel.getServiceName() + "/petra/" + signalName
                + "/" + signalModel.getVersion().getVersionName(), this::serve).build();
        beanFactory.registerSingleton("signal_" + signalName, routerFunction);
    }

    private ServerResponse serve(ServerRequest request) {
        try {
            RequestDto requestDto = request.body(RequestDto.class);
            log.info("Signal {} input {}", signalName, requestDto.toString());

            ResponseDto prevRequest = answersMap.addKey(requestDto.getScenarioId());
            if (prevRequest != null) {
                ResponseDto executingModel = createExecutingModel(requestDto.getScenarioId(), requestDto.getRequestBlockId());
                return ServerResponse.ok().body(executingModel);
            }

            signalResponseHandler.handleSignal(requestDto, this);
            ResponseDto answer = answersMap.waitAnswer(requestDto.getScenarioId());

            if (answer == null) {
                log.info("Signal {} not found {}", signalName, requestDto.toString());
            } else return ServerResponse.ok().body(answer);
        } catch (ServletException | IOException e) {
            log.error(e);
        }
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error");
    }


    private ResponseDto createExecutingModel(UUID scenarioId, BlockId requestBlockId) {
        return new ResponseDto(
                scenarioId,
                signalId,
                requestBlockId,
                null,
                ResponseType.EXECUTING,
                null
        );
    }


    @Override
    public SignalId getId() {
        return signalId;
    }

    @Override
    public void startSignal() {

    }

    @Override
    public void setAnswer(Collection<ProcessValue> signalAnswerVariables, BlockId requestBlockId, UUID scenarioId, BlockId responseBlockId) {
        ResponseDto responseDto = new ResponseDto(
                scenarioId,
                signalId,
                requestBlockId,
                responseBlockId,
                ResponseType.RESPONSE,
                signalAnswerVariables
        );
        answersMap.releaseAnswer(responseDto);
    }

    @Override
    public void setError(BlockId requestBlockId, UUID scenarioId, BlockId responseBlockId) {
        ResponseDto responseDto = new ResponseDto(scenarioId,
                signalId,
                requestBlockId,
                responseBlockId,
                ResponseType.ERROR,
                null
        );
        answersMap.releaseAnswer(responseDto);
    }
}
