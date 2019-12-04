/**
 * 
 */
package com.cxloyalty.memberassetsearch.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cxloyalty.memberassetsearch.adaptor.IAssetSearchAdaptor;
import com.cxloyalty.memberassetsearch.constant.AssetConstants;
import com.cxloyalty.memberassetsearch.constant.AssetType;
import com.cxloyalty.memberassetsearch.document.Audit;
import com.cxloyalty.memberassetsearch.document.MemberAsset;
import com.cxloyalty.memberassetsearch.domain.AssetAttributeDomain;
import com.cxloyalty.memberassetsearch.domain.AuditDomain;
import com.cxloyalty.memberassetsearch.domain.MemberAssetDomain;
import com.cxloyalty.memberassetsearch.domain.SearchParameters;
import com.cxloyalty.memberassetsearch.mapper.IMemberAssetMapper;
import com.cxloyalty.memberassetsearch.repository.IMemberAssetIndexRepository;
import com.cxloyalty.util.exception.BaseException;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author afall
 *
 */
@RunWith(SpringRunner.class)
public class AssetServiceTest {

	private IAssetService service;

	@MockBean
	private IAssetSearchAdaptor adaptor;
	@MockBean
	private IMemberAssetIndexRepository repository;
	@MockBean
	private IMemberAssetMapper mapper;

	@Before
	public void setUp() throws Exception {
		service = new AssetService(repository, adaptor, mapper);
	}

	@Test
	public void saveIndex() throws BaseException {

		MemberAsset memberDocumentMock = Mockito.mock(MemberAsset.class);
		MemberAssetDomain memberDomainMock = Mockito.mock(MemberAssetDomain.class);
		when(memberDocumentMock.getAssetId()).thenReturn("12345");
		when(memberDomainMock.getMeta()).thenReturn(buildMeta());
		when(adaptor.getAssetDetailsByAssetId(Mockito.any())).thenReturn(Mono.just(buildMemberAssetDomain()));
		when(mapper.toMemberAssetDocument(Mockito.any())).thenReturn(buildMemberAssetDocument());
		when(mapper.toMemberAssetDomain(Mockito.any())).thenReturn(buildMemberAssetDomain());
		when(repository.save(Mockito.any(MemberAsset.class))).thenReturn(Mono.just(buildMemberAssetDocument()));
		Mono<MemberAssetDomain> memberAssetDomain = service.saveAsset(buildParams());

		Assert.assertNotNull(memberAssetDomain);
        StepVerifier.create(memberAssetDomain)
                .expectNextCount(1)
                .expectComplete();
        Assert.assertNotNull(memberAssetDomain.block().getMeta().get(AssetConstants.ASSET_ID));
	}
	
	@Test
	public void updateIndex() throws BaseException {		
		
		MemberAsset memberDocumentMock = Mockito.mock(MemberAsset.class);
		MemberAssetDomain memberDomainMock = Mockito.mock(MemberAssetDomain.class);
		when(memberDocumentMock.getAssetId()).thenReturn("12345");
		when(memberDomainMock.getMeta()).thenReturn(buildMeta());
		when(adaptor.getAssetDetailsByAssetId(Mockito.any())).thenReturn(Mono.just(buildMemberAssetDomain()));
		when(mapper.toMemberAssetDocument(Mockito.any())).thenReturn(buildMemberAssetDocument());
		when(mapper.toMemberAssetDomain(Mockito.any())).thenReturn(buildMemberAssetDomain());
		when(repository.save(Mockito.any(MemberAsset.class))).thenReturn(Mono.just(buildMemberAssetDocument()));
		Mono<MemberAssetDomain> memberAssetDomain = service.updateAsset(buildParams());

		Assert.assertNotNull(memberAssetDomain);
        StepVerifier.create(memberAssetDomain)
                .expectNextCount(1)
                .expectComplete();
        Assert.assertNotNull(memberAssetDomain.block().getMeta().get(AssetConstants.ASSET_ID)); 
	}
	
	@Test
	public void saveIndex_failure() throws BaseException {
		
		MemberAsset memberDocumentMock = Mockito.mock(MemberAsset.class);
		MemberAssetDomain memberDomainMock = Mockito.mock(MemberAssetDomain.class);
		when(memberDocumentMock.getAssetId()).thenReturn(null);
		when(memberDomainMock.getMeta()).thenReturn(buildMeta());
		when(adaptor.getAssetDetailsByAssetId(Mockito.any())).thenReturn(Mono.just(buildMemberAssetDomain()));
		when(mapper.toMemberAssetDocument(Mockito.any())).thenReturn(null);
		when(mapper.toMemberAssetDomain(Mockito.any())).thenReturn(null);
		when(repository.save(Mockito.any(MemberAsset.class))).thenReturn(Mono.empty());
		Mono<MemberAssetDomain> memberAssetDomain = service.saveAsset(buildParams());

		Assert.assertNotNull(memberAssetDomain);
        StepVerifier.create(memberAssetDomain)
                .expectNextCount(1)
                .expectComplete();        
	}
	
	@Test
	public void updateIndex_failure() throws BaseException {
		
		MemberAsset memberDocumentMock = Mockito.mock(MemberAsset.class);
		MemberAssetDomain memberDomainMock = Mockito.mock(MemberAssetDomain.class);
		when(memberDocumentMock.getAssetId()).thenReturn(null);
		when(memberDomainMock.getMeta()).thenReturn(buildMeta());
		when(adaptor.getAssetDetailsByAssetId(Mockito.any())).thenReturn(Mono.just(buildMemberAssetDomain()));
		when(mapper.toMemberAssetDocument(Mockito.any())).thenReturn(null);
		when(mapper.toMemberAssetDomain(Mockito.any())).thenReturn(null); 
		when(repository.save(Mockito.any(MemberAsset.class))).thenReturn(Mono.empty());
		Mono<MemberAssetDomain> memberAssetDomain = service.updateAsset(buildParams());

		Assert.assertNotNull(memberAssetDomain);
        StepVerifier.create(memberAssetDomain)
                .expectNextCount(1)
                .expectComplete();
        
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

	private MemberAsset buildMemberAssetDocument() {
		MemberAsset memberAsset = new MemberAsset();
		memberAsset.setAssetAttributes(new ArrayList<>());
		memberAsset.setAssetId("12345");
		memberAsset.setAssetName("Visa");
		memberAsset.setCorrelationId("556353555");
		memberAsset.setVisibilityScopeKey("De5286719346913");
		memberAsset.setCategoryTag("Financial_Data");
		memberAsset.setAssetType(AssetType.ASSET_HELIX_PROTECTEDCARD.getAssetTypeValue());
		memberAsset.setMemberId(Long.parseLong("65658"));
		memberAsset.setMembershipId(Long.parseLong("48783"));
		memberAsset.setMaskedDisplayValue("**1234");
		memberAsset.setOrgId(123);
		memberAsset.setSearchKey("asset_id");
		memberAsset.setStatus("active");
		memberAsset.setSourceSystem("source");
		memberAsset.setAudit(new Audit("ENGAGE", "ENGAGE", new Date(), new Date()));
		return memberAsset;
	}

	private SearchParameters<String> buildParams() {
		return SearchParameters.<String>builder().param("12345").param("12345").param("12345").build();
	}

}
