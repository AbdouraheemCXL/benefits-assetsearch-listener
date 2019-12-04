/**
 * 
 */
package com.cxloyalty.memberassetsearch.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.cxloyalty.memberassetsearch.domain.SearchParameters.SearchParametersBuilder;

/**
 * @author afall
 *
 */
@RunWith(SpringRunner.class)
public class SearchParametersTest {
    private SearchParameters<String> searchParameters;
    private SearchParameters<String> searchParameters2;
    private SearchParametersBuilder<String> searchParamBuilder;

    @Before
    public void setup(){
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("name","value");
        searchParamBuilder = SearchParameters.<String>builder()
                .param("1232534");
		searchParameters = searchParamBuilder
                .build();

        List<String> params = new ArrayList<>();
        params.add("262362");
        params.add("262362");
        params.add("262363");
        searchParameters2 = SearchParameters.<String>builder()
                .params(params)
                .clearParams()
                .build();
    }

    @Test
    public void testSearchParams(){
        Assert.assertNotNull(searchParameters);
        Assert.assertNotNull(searchParameters.getParams());
        Assert.assertEquals(1, searchParameters.getParams().size());

        Assert.assertNotNull(searchParameters2);
        Assert.assertNotNull(searchParameters2.getParams());
        Assert.assertEquals(0, searchParameters2.getParams().size());
        Assert.assertNotNull("Builder init failed!", searchParamBuilder.toString());
        
        Assert.assertNotNull(new MemberAssetPayload());
        
    }
}
