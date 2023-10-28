package com.petra.lib;

import com.petra.lib.state.handler.impl.UserHandlerExecutor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Компонент соединяющий петру и спринг
 */
//@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public final class PetraFactory implements ApplicationContextAware {

    final DataSource dataSource;
//    final PlatformTransactionManager platformTransactionManager;

    @Value("${petra.kafka.servers}")
    String kafkaServers;

    @Value("${petra.thread.count}")
    Integer threadsCount;

    final ConfigurableApplicationContext context;
    final EntityManagerFactory entityManagerFactory;
    final JpaTransactionManager jpaTransactionManager;

    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        dsasd

        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor()

//        ThreadManager.setPoolSize(threadsCount);
//        Map<String, Object> sourceHandlersBeans = applicationContext.getBeansWithAnnotation(WorkflowHandler.class);
//
//        Map<String, Object> workflowHandlers = sourceHandlersBeans.values().stream()
//                        .collect(Collectors.toMap( valForKey -> {
//                            return valForKey.getClass().getAnnotation(WorkflowHandler.class).name();
//                        }, Function.identity()));
//
//
//        JobRepository jobRepository = new JobRepository(jpaTransactionManager);
//        try {
//            Resource config = applicationContext.getResource("classpath:petra_config.json");
//            String configJson = new String(Files.readAllBytes(config.getFile().toPath()));
//            ObjectMapper objectMapper = new ObjectMapper();
//            ConfigModel configModel = objectMapper.readValue(configJson, ConfigModel.class);
//            BlockFactory blockFactory = new BlockFactory();
//
//            Collection<JobStaticManager> sourceExecutors = configModel.getSources().stream()
//                    .map(sourceModel -> blockFactory.createSource(sourceModel,
//                            (UserHandler) workflowHandlers.get(sourceModel.getName()),
//                            jobRepository,
//                            jpaTransactionManager,
//                            sourceModel.getExecutionSignal().getPath(),
//                            context.getBeanFactory(),
//                            entityManagerFactory
//                            )).collect(Collectors.toList());
//            sourceExecutors.forEach(JobStaticManager::start);
//
//            Collection<JobStaticManager> actionExecutors = configModel.getActions().stream()
//                    .map(actionModel -> {
//                        return blockFactory.createAction(
//                                actionModel,
//                                (UserHandler) workflowHandlers.get(actionModel.getName()),
//                                jobRepository,
//                                jpaTransactionManager,
//                                actionModel.getExecutionSignal().getPath(),
//                                context.getBeanFactory(),
//                                kafkaServers
//                        );
//                    }).collect(Collectors.toList());
//            actionExecutors.forEach(JobStaticManager::start);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
