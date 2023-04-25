package com.petra.lib;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.annotation.SourceHandler;
import com.petra.lib.handler.UserHandler;
import com.petra.lib.manager.block.BlockFactory;
import com.petra.lib.worker.manager.JobStaticManager;
import com.petra.lib.worker.manager.JobStaticManagerImpl;
import com.petra.lib.manager.models.ConfigModel;
import com.petra.lib.registration.JobRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public final class PetraFactory implements ApplicationContextAware {

    final DataSource dataSource;
    final PlatformTransactionManager platformTransactionManager;

    @Value("petra.kafka.servers")
    String kafkaServers;
    final ConfigurableApplicationContext context;

    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(context);
        Map<String, Object> sourceHandlers = applicationContext.getBeansWithAnnotation(SourceHandler.class);
        JobRepository jobRepository = new JobRepository(dataSource);

        try {
            Resource config = applicationContext.getResource("classpath:petra_config.json");
            String configJson = new String(Files.readAllBytes(config.getFile().toPath()));
            ObjectMapper objectMapper = new ObjectMapper();
            ConfigModel configModel = objectMapper.readValue(configJson, ConfigModel.class);
            log.debug(configModel.toString());
            Collection<JobStaticManager> sourceExecutors = configModel.getSources().stream()
                    .map(actionModel -> BlockFactory.createSource(actionModel,
                            (UserHandler) sourceHandlers.get(actionModel.getName()),
                            jobRepository,
                            platformTransactionManager,
                            actionModel.getExecutionSignal().getPath(),
                            context.getBeanFactory()
                            )).collect(Collectors.toList());
            sourceExecutors.forEach(JobStaticManager::start);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
