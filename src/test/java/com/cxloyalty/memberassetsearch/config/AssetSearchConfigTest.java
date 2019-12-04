package com.cxloyalty.memberassetsearch.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
public class AssetSearchConfigTest {
    private AssetSearchConfig config;

    @Before
    public void setup(){
        config = new AssetSearchConfig();
        ReflectionTestUtils.setField(config, "elasticsearchHost", "vpc-membersearch-dev-pst2tchesygo64is3eg2gp3ehm.eu-west-1.es.amazonaws.com:80");
    }

    @Test
    public void testMemberSearchConfig(){
        ReactiveElasticsearchClient client = config.reactiveElasticsearchClient();
        Assert.assertNotNull(client);
    }

}
