package com.petra.lib.signal.sync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.manager.thread.ThreadManager;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.signal.SignalResponseListener;
import com.petra.lib.signal.SignalType;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.model.Version;
import com.petra.lib.variable.process.ProcessVariable;
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

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SyncResponse implements ResponseSignal {

    SignalResponseListener handler;
    /**
     * Асинхронный хранитель ответов для требования при ответе
     */
    AnswersMap answersMap = new AnswersMap();
    Version version;
    Long signalId;
    Long blockId;
    ObjectMapper objectMapper = new ObjectMapper();
    String signalName;

    public SyncResponse(String hostname, String signalName,
                        Long blockId, Version version,
                        Long signalId, ConfigurableListableBeanFactory beanFactory) {
        this.version = version;
        this.signalId = signalId;
        this.blockId = blockId;
        this.signalName = signalName;

        RouterFunction<ServerResponse> routerFunction = route().POST("/" + hostname, this::serve).build();
        beanFactory.registerSingleton(hostname, routerFunction);
    }

    private ServerResponse serve(ServerRequest request) {
        try {
            SignalTransferModel signalTransferModel = request.body(SignalTransferModel.class);
            log.info("Signal {} input {}", signalName, signalTransferModel.toString());
            SignalTransferModel prevRequest = answersMap.addKey(signalTransferModel.getScenarioId());
            if (prevRequest != null) {
                SignalTransferModel errorModel = createErrorModel(signalTransferModel.getScenarioId());
                return ServerResponse.ok().body(errorModel);
            }

            ThreadManager.execute(() -> handler.executeSignal(signalTransferModel));
            answersMap.waitAnswer(signalTransferModel.getScenarioId());

            SignalTransferModel answer = answersMap.remove(signalTransferModel.getScenarioId());
            if (answer == null) {
                log.info("Signal {} not found {}", signalName, signalTransferModel.toString());
                SignalTransferModel errorModel = createErrorModel(signalTransferModel.getScenarioId());
                return ServerResponse.ok().body(errorModel);
            } else return ServerResponse.ok().body(answer);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error");
    }


    private SignalTransferModel createErrorModel(UUID scenarioId){
        return new SignalTransferModel(
                scenarioId,
                signalId,
                version,
                blockId,
                SignalType.ERROR,
                null
                );
    }


    @Override
    public Long getId() {
        return signalId;
    }

    @Override
    public void startSignal() {

    }

    @Override
    public void setAnswer(Collection<ProcessVariable> contextVariables, UUID scenarioId) {
        SignalTransferModel signalTransferModel = new SignalTransferModel(
                scenarioId,
                signalId,
                version,
                blockId,
                SignalType.RESPONSE,
                contextVariables
        );
        answersMap.putOnlyIfExists(signalTransferModel);
    }

    @Override
    public void executionError(UUID scenarioId) {
        SignalTransferModel errorTransferModel = createErrorModel(scenarioId);
        answersMap.putOnlyIfExists(errorTransferModel);
    }

    @Override
    public void setListener(SignalResponseListener signalResponseListener) {
        this.handler = signalResponseListener;
    }
}
