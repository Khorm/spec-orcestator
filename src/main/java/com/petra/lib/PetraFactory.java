package com.petra.lib;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.annotation.SourceHandler;
import com.petra.lib.worker.handler.UserHandler;
import com.petra.lib.manager.state.BlockFactory;
import com.petra.lib.manager.thread.ThreadManager;
import com.petra.lib.manager.block.JobStaticManager;
import com.petra.lib.manager.models.ConfigModel;
import com.petra.lib.worker.repo.JobRepository;
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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public final class PetraFactory implements ApplicationContextAware {

    final DataSource dataSource;
//    final PlatformTransactionManager platformTransactionManager;

    @Value("petra.kafka.servers")
    String kafkaServers;

    @Value("${petra.thread.count}")
    Integer threadsCount;

    final ConfigurableApplicationContext context;
    final EntityManagerFactory entityManagerFactory;
    final JpaTransactionManager jpaTransactionManager;

    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ThreadManager.setPoolSize(threadsCount);
        Map<String, Object> sourceHandlersBeans = applicationContext.getBeansWithAnnotation(SourceHandler.class);

        Map<String, Object> sourceHandlers = sourceHandlersBeans.values().stream()
                        .collect(Collectors.toMap( valForKey -> {
                            return valForKey.getClass().getAnnotation(SourceHandler.class).name();
                        }, Function.identity()));
//        sourceHandlers.forEach((key,value) ->{
//            System.out.println(key);
//            System.out.println(value);
//
//            System.out.println(value.getClass().getAnnotation(SourceHandler.class).name());
//        });

//        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setDataSource(dataSource);
//        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);


        JobRepository jobRepository = new JobRepository(jpaTransactionManager);
        try {
            Resource config = applicationContext.getResource("classpath:petra_config.json");
            String configJson = new String(Files.readAllBytes(config.getFile().toPath()));
            ObjectMapper objectMapper = new ObjectMapper();
            ConfigModel configModel = objectMapper.readValue(configJson, ConfigModel.class);
            BlockFactory blockFactory = new BlockFactory();
            Collection<JobStaticManager> sourceExecutors = configModel.getSources().stream()
                    .map(actionModel -> blockFactory.createSource(actionModel,
                            (UserHandler) sourceHandlers.get(actionModel.getName()),
                            jobRepository,
                            jpaTransactionManager,
                            actionModel.getExecutionSignal().getPath(),
                            context.getBeanFactory(),
                            entityManagerFactory
                            )).collect(Collectors.toList());
            sourceExecutors.forEach(JobStaticManager::start);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
