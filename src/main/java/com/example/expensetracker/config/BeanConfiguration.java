package com.example.expensetracker.config;

import java.util.Arrays;
import java.util.Optional;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {


  @Value("${solr.expense.collection}")
  private String expenseCollection;

  @Value("${solr.zkConnection.timeout}")
  private String zkConnectionTimeout;

  @Value("${solr.zkClient.timeout}")
  private String zkClientTimeout;

  @Value("${solr.so.timeout}")
  private String soTimeout;


  @Bean(name = "expenseCollectionClient")
  public CloudSolrClient expenseCollectionClient() {
    String zkHost = "localhost:2181/solr";
    CloudSolrClient cloudSolrClient =
        new CloudSolrClient.Builder().withZkHost(zkHost).withSocketTimeout(Integer.parseInt(soTimeout)).build();
    cloudSolrClient.setDefaultCollection(expenseCollection);
    cloudSolrClient.setZkConnectTimeout(Integer.valueOf(zkConnectionTimeout));
    cloudSolrClient.setZkClientTimeout(Integer.valueOf(zkClientTimeout));
    return cloudSolrClient;
  }

}
