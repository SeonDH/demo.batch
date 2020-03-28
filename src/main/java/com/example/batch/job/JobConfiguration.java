package com.example.batch.job;

import com.example.batch.entity.External;
import com.example.batch.repository.CustomRepository;
import com.example.batch.service.WebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class JobConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private WebService webService;
    private CustomRepository customRepository;
    private DataSource dataSource;


    JobConfiguration(JobBuilderFactory jobBuilderFactory,
                     StepBuilderFactory stepBuilderFactory,
                     WebService webService,
                     CustomRepository customRepository,
                     @Qualifier("externalDataSource") DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.webService = webService;
        this.customRepository = customRepository;
        this.dataSource = dataSource;
    }

    private static final int chunkSize = 100;

    @Bean
    public Job itemReader() throws Exception {
        return jobBuilderFactory.get("itemReader")
                                .start(ItemReaderStep())
                                .build();
    }

    @Bean
    public Step ItemReaderStep() throws Exception {
        return stepBuilderFactory.get("itemReaderStep")
                .<External, External>chunk(chunkSize)
                .reader(jdbcPagingItemReader())
                .writer(itemWriter())
                .build();
    }


    @Bean
    public JdbcPagingItemReader<External> jdbcPagingItemReader() throws Exception {
        return new JdbcPagingItemReaderBuilder<External>()
                .pageSize(chunkSize)
                .fetchSize(chunkSize)
                .dataSource(dataSource) // externalDataSource
                .rowMapper(new BeanPropertyRowMapper<>(External.class))
                .queryProvider(createQueryProvider())
                .name("jdbcPagingItemReader")
                .build();
    }

    private ItemWriter<External> itemWriter() {
        return list -> {
            for (External external : list) {
                webService.sendRequest(external.toRequest());
                customRepository.save(external.toCustom());
            }
        };
    }


    @Bean
    public PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("select id, external_data");
        queryProvider.setFromClause("from Externals");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);

        return queryProvider.getObject();
    }
}
