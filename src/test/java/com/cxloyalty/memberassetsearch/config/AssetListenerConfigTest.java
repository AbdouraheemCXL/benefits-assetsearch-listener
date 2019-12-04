package com.cxloyalty.memberassetsearch.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
public class AssetListenerConfigTest {
    private AssetListenerConfig config;

    @Before
    public void setup(){
        config = new AssetListenerConfig();
        ReflectionTestUtils.setField(config,"bootstrapServers", "localhost:9092");
        ReflectionTestUtils.setField(config,"consumerGroupId", "asset_search_indexing");
    }

    @Test
    public void testListenerConfig(){
        Assert.assertNotNull(config);
        Assert.assertNotNull(config.consumerFactory());
        Assert.assertNotNull(config.kafkaListenerContainerFactory());
    }

}
