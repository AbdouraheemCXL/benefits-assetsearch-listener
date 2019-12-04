/**
 * 
 */
package com.cxloyalty.memberassetsearch.listener;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cxloyalty.memberassetsearch.constant.AssetConstants;
import com.cxloyalty.memberassetsearch.constant.AssetType;
import com.cxloyalty.memberassetsearch.domain.AssetAttributeDomain;
import com.cxloyalty.memberassetsearch.domain.AuditDomain;
import com.cxloyalty.memberassetsearch.domain.MemberAssetDomain;
import com.cxloyalty.memberassetsearch.domain.MemberAssetPayload;
import com.cxloyalty.memberassetsearch.domain.SearchParameters;
import com.cxloyalty.memberassetsearch.service.IAssetService;
import com.cxloyalty.util.exception.BaseException;

import reactor.core.publisher.Mono;

/**
 * @author afall
 *
 */
@RunWith(SpringRunner.class)
public class MemberAssetIndexListenerTest {

	private MemberAssetIndexListener listener;
	
	@MockBean
	private IAssetService service;
	
	@Before
	public void setUp() throws Exception {
		listener = new MemberAssetIndexListener(service);
	}

	@Test
	public void saveIdex() throws BaseException {
		MemberAssetDomain memberDomainMock = Mockito.mock(MemberAssetDomain.class);
		when(memberDomainMock.getMeta()).thenReturn(buildMeta());
		when(service.saveAsset(buildParams())).thenReturn(Mono.just(buildMemberAssetDomain()));
		listener.saveIndex(buildPlayload());
		assertNotNull(buildMemberAssetDomain().getMeta());
	}
	
	@Test
	public void updateIdex() throws BaseException {
		MemberAssetDomain memberDomainMock = Mockito.mock(MemberAssetDomain.class);
		when(memberDomainMock.getMeta()).thenReturn(buildMeta());
		when(service.updateAsset(buildParams())).thenReturn(Mono.just(buildMemberAssetDomain()));
		listener.updateIndex(buildPlayload());
		assertNotNull(buildMemberAssetDomain().getMeta());
	}
	
	private MemberAssetDomain buildMemberAssetDomain() {
		MemberAssetDomain member = new MemberAssetDomain();
		member.setMeta(buildMeta());
		member.setAssets(buildAssets());
		member.setAttributes(buildDomainAttributes());
		member.setAudit(new AuditDomain("ENGAGE", "ENGAGE", Long.valueOf("1572982366009"), 0L));
		return member;
	}

	private Map<String, String> buildMeta() {
		Map<String, String> meta = new HashMap<>();
		meta.put(AssetConstants.ASSET_TYPE, AssetType.ASSET_HELIX_PROTECTEDCARD.getAssetTypeValue());
		meta.put(AssetConstants.ASSET_NAME, "Visa");
		meta.put(AssetConstants.CATEGORY_TAG, "Financial_Data");
		meta.put(AssetConstants.MEMBER_ID, "65658");
		meta.put(AssetConstants.MEMBERSHIP_ID, "48783");
		meta.put(AssetConstants.ASSET_ID, "5dbc70e7b03ca34a14ea98fb");
		meta.put(AssetConstants.SOURCE_SYSTEM_ID, "source");
		meta.put(AssetConstants.MASKED_DISPLAY_VALUE, "**1234");
		meta.put(AssetConstants.ASSET_STATUS, "active");
		meta.put(AssetConstants.CREATED_BY, "AGENT");
		meta.put(AssetConstants.UPDATED_BY, "AGENT");
		return meta; 
	}

	private Map<String, String> buildAssets() {
		Map<String, String> assets = new HashMap<>();
		assets.put(AssetConstants.CARD_NUM, "123456789");
		assets.put(AssetConstants.ACCOUNT_NUM, "123456789");
		return assets;
	}
	
	private List<AssetAttributeDomain> buildDomainAttributes() {
		List<AssetAttributeDomain> attributes = new ArrayList<>();
		attributes.add(new AssetAttributeDomain("Discover", "Discover"));
		return attributes;
	}

	private SearchParameters<String> buildParams() {
		return SearchParameters.<String>builder().param("12345").param("12345").param("12345").build();
	}
	
	private MemberAssetPayload buildPlayload() {
		return new MemberAssetPayload("12345", "12345", "12345");
	}
	
}
