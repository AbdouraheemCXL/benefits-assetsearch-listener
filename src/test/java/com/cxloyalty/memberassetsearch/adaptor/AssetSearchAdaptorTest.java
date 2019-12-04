/**
 * 
 */
package com.cxloyalty.memberassetsearch.adaptor;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.cxloyalty.memberassetsearch.domain.MemberAssetDomain;
import com.cxloyalty.memberassetsearch.domain.SearchParameters;
import com.cxloyalty.util.exception.BaseException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author afall
 *
 */
@RunWith(SpringRunner.class)
public class AssetSearchAdaptorTest {

	private IAssetSearchAdaptor adaptor;
	private static MockWebServer mockWebServer;
	private String assetResponseJSON = "[{\"meta\":{\"memberId\":1242348,\"name\":\"SavingsAcct\",\"sourceSystemId\":\"ENGAGE\",\"asset_status\":\"Active\",\"asset_type\":\"Asset_Helix_BankAccount\",\"asset_id\":\"5da0d694ab034ab5df701b59\",\"category_tag\":\"Financial_Data\",\"created_by\":\"Client\",\"masked_display_value\":\"****1234\",\"updated_by\":\"Agent\"},\"audit\":{\"createdBy\":\"Client\",\"modifiedBy\":\"Agent\",\"createdDate\":1568264400000,\"modifiedDate\":1568264400000},\"attributes\":[{\"attributeName\":\"Account_Type\",\"attributeValue\":\"Savings\"},{\"attributeName\":\"Ragu_Vamsi\",\"attributeValue\":\"QA\"}]}]";

	@Before
	public void setup() throws IOException {
		mockWebServer = new MockWebServer();
		adaptor = new AssetSearchAdaptor();		
		mockWebServer.start();		
		ReflectionTestUtils.setField(adaptor, "assetDetailsUrl", "http://localhost:9000/benefit-asset-service/member-assets/{assetID}");
	}
	


	@Test
	public void test_getAssetDetailsByAssetId() throws BaseException {

		mockWebServer.enqueue(new MockResponse()
                .setBody(assetResponseJSON)
                .addHeader("Content-Type", "application/json")
        );

		SearchParameters<String> params = SearchParameters.<String>builder()
															.param("24")
															.param("d24t-23fd827-2d32-23d23")
															.param("1234354").build();
		Mono<MemberAssetDomain> result = adaptor.getAssetDetailsByAssetId(params);
		Assert.assertNotNull(result);
        StepVerifier.create(result)
                .expectNextCount(1)
                .expectComplete();
	}
		
}
