package com.example.expensetracker.config;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Splitter;

@Configuration
public class BeanConfiguration {

  public static final String DEFAULT_HTTP_PARAMS = "maxConnections/20;maxConnectionsPerHost/10";
  public static final String SEPARATOR = ";";
  public static final String SLASH = "/";


  @Value("${solr.expense.collection}")
  private String expenseCollection;


  @Value("${solr.cloud.urls}")
  private String solrCloudUrls;

  @Value("${solr.zkConnection.timeout}")
  private String zkConnectionTimeout;

  @Value("${solr.zkClient.timeout}")
  private String zkClientTimeout;

  @Value("${solr.so.timeout}")
  private String soTimeout;

  @Value("${solr.connection.timeout}")
  private String connectionTimeout;

  @Value("${solr.http.params}")
  private String httpParams;



  @Bean(name = "expenseCollectionClient")
  public CloudSolrClient cloudSolrClient() {
    CloudSolrClient cloudSolrClient =
        new CloudSolrClient.Builder(Arrays.asList(solrCloudUrls.split(",")), Optional.empty()).withConnectionTimeout(
            Integer.parseInt(connectionTimeout)).withHttpClient(HttpClientUtil.createClient(getSolrParams(httpParams)))
            .withSocketTimeout(Integer.parseInt(soTimeout)).build();
    cloudSolrClient.setDefaultCollection(expenseCollection);
    cloudSolrClient.setZkConnectTimeout(Integer.valueOf(zkConnectionTimeout));
    cloudSolrClient.setZkClientTimeout(Integer.valueOf(zkClientTimeout));
    return cloudSolrClient;
  }

  @Bean(name = "solrClient")
  public SolrClient solrClient() {
    CloudSolrClient cloudSolrClient =
        new CloudSolrClient.Builder(Arrays.asList(solrCloudUrls.split(",")), Optional.empty()).withConnectionTimeout(
            Integer.parseInt(connectionTimeout)).withHttpClient(HttpClientUtil.createClient(getSolrParams(httpParams)))
            .withSocketTimeout(Integer.parseInt(soTimeout)).build();
    cloudSolrClient.setDefaultCollection(expenseCollection);
    cloudSolrClient.setZkConnectTimeout(Integer.valueOf(zkConnectionTimeout));
    cloudSolrClient.setZkClientTimeout(Integer.valueOf(zkClientTimeout));
    return cloudSolrClient;
  }

  private ModifiableSolrParams getSolrParams(String solrHttpParams) {
    ModifiableSolrParams params = new ModifiableSolrParams();
    String solrParams = Optional.ofNullable(solrHttpParams).orElse(DEFAULT_HTTP_PARAMS);
    if (StringUtils.isNotBlank(solrParams)) {
      Splitter.on(SEPARATOR).withKeyValueSeparator(SLASH).split(solrParams).forEach(params::add);
    }
    return params;
  }

}
