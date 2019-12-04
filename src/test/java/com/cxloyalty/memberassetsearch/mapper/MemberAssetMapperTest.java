/**
 * 
 */
package com.cxloyalty.memberassetsearch.mapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.cxloyalty.memberassetsearch.constant.AssetConstants;
import com.cxloyalty.memberassetsearch.constant.AssetType;
import com.cxloyalty.memberassetsearch.document.AssetAttribute;
import com.cxloyalty.memberassetsearch.document.Audit;
import com.cxloyalty.memberassetsearch.document.MemberAsset;
import com.cxloyalty.memberassetsearch.domain.AssetAttributeDomain;
import com.cxloyalty.memberassetsearch.domain.AuditDomain;
import com.cxloyalty.memberassetsearch.domain.MemberAssetDomain;

/**
 * @author afall
 *
 */
@RunWith(SpringRunner.class)
public class MemberAssetMapperTest {

	private IMemberAssetMapper mapper;

	@Before
	public void setUp() throws Exception {
		mapper = new MemberAssetMapper();
	}

	@Test
	public void toMemberAssetDocument() {
		
		MemberAsset memberAsset = mapper.toMemberAssetDocument(buildMemberAssetDomain());
		assertNotNull(memberAsset);
		assertThat(memberAsset.getAssetId(), equalTo("5dbc70e7b03ca34a14ea98fb"));
		assertThat(memberAsset.getAssetName(), equalTo("Visa"));
		assertThat(memberAsset.getAssetType(), equalTo(AssetType.ASSET_HELIX_PROTECTEDCARD.getAssetTypeValue()));
		assertThat(memberAsset.getMemberId(), equalTo(Long.valueOf("65658")));
		assertThat(memberAsset.getMembershipId(), equalTo(Long.valueOf("48783")));
		assertThat(memberAsset.getAssetAttributes().size(), equalTo(1));
		assertNull(memberAsset.getSourceSystem());
		assertNotNull(memberAsset.getMaskedDisplayValue());
		assertNotNull(memberAsset.getStatus());
		assertNotNull(mapper.toMemberAssetDocument(new MemberAssetDomain()));
		assertNotNull(mapper.toMemberAssetDocument(buildMemberAssetDomain()).toString());
		assertNotNull(mapper.toMemberAssetDocument(null));
		
		MemberAssetDomain asset = new MemberAssetDomain();
		asset.setAttributes(new ArrayList<>());
		asset.getAttributes().add(new AssetAttributeDomain());
		asset.getAttributes().add(null);
		asset.setAudit(null);
		assertNotNull(mapper.toMemberAssetDocument(asset).getAssetAttributes());
		assertNotNull(mapper.toMemberAssetDocument(asset).getAudit());
		
		MemberAssetDomain membAsset = new MemberAssetDomain();
		membAsset.setMeta(new HashMap<>());
		membAsset.setAssets(new HashMap<>());
		membAsset.getMeta().put(AssetConstants.ASSET_TYPE, AssetType.ASSET_HELIX_BANKACCOUNT.getAssetTypeValue());
		membAsset.getAssets().put(AssetConstants.ACCOUNT_NUM, "123456789");
		assertNotNull(mapper.toMemberAssetDocument(membAsset).getMaskedDisplayValue());
		
		membAsset = new MemberAssetDomain();
		membAsset.setMeta(new HashMap<>());
		membAsset.setAssets(new HashMap<>());
		membAsset.getAssets().put(AssetConstants.ACCOUNT_NUM, "123456789");
		assertNull(mapper.toMemberAssetDocument(membAsset).getMaskedDisplayValue());
		
		membAsset = new MemberAssetDomain();
		membAsset.setMeta(new HashMap<>());
		membAsset.setAssets(new HashMap<>());
		membAsset.getMeta().put(AssetConstants.ASSET_TYPE, AssetType.ASSET_HELIX_BANKACCOUNT.getAssetTypeValue());
		assertNull(mapper.toMemberAssetDocument(membAsset).getMaskedDisplayValue());
		
		membAsset = new MemberAssetDomain();
		membAsset.setMeta(new HashMap<>());
		membAsset.setAssets(new HashMap<>());
		membAsset.getMeta().put(AssetConstants.ASSET_TYPE, AssetType.ASSET_HELIX_PROTECTEDCARD.getAssetTypeValue());
		assertNull(mapper.toMemberAssetDocument(membAsset).getMaskedDisplayValue());
		
		membAsset = new MemberAssetDomain();
		membAsset.setAudit(new AuditDomain());
		assertNull(mapper.toMemberAssetDocument(membAsset).getAudit().getCreatedBy());
	}
	
	@Test
	public void toMemberAssetDomain() {

		MemberAssetDomain assetDomain = mapper.toMemberAssetDomain(buildMemberAssetEntity());
		assertNotNull(assetDomain);
		assertThat(assetDomain.getMeta().get(AssetConstants.ASSET_ID), equalTo("12345"));
		assertThat(assetDomain.getMeta().get(AssetConstants.ASSET_NAME), equalTo("Visa"));
		assertNotNull(assetDomain.getMeta().get(AssetConstants.ASSET_TYPE));
		assertThat(assetDomain.getMeta().get(AssetConstants.CATEGORY_TAG), equalTo("Financial_Data"));
		assertThat(assetDomain.getMeta().get(AssetConstants.MEMBER_ID), equalTo("65658"));
		assertThat(assetDomain.getMeta().get(AssetConstants.MEMBERSHIP_ID), equalTo("48783"));
		assertThat(assetDomain.getAttributes().size(), equalTo(1));
		assertNotNull(assetDomain.getAttributes().get(0));
		assertNotNull(assetDomain.getAudit());
		assertNotNull(assetDomain.getAudit().getCreatedBy());
		assertNotNull(mapper.toMemberAssetDomain(new MemberAsset()));
		assertNotNull(mapper.toMemberAssetDomain(buildMemberAssetEntity()).toString());
		assertNotNull(mapper.toMemberAssetDomain(null));
		
		MemberAsset asset = new MemberAsset();
		asset.setAssetAttributes(new ArrayList<>());
		asset.getAssetAttributes().add(new AssetAttribute());
		asset.getAssetAttributes().add(null);
		asset.setAudit(null);
		assertNotNull(mapper.toMemberAssetDomain(asset).getAttributes());
		assertNotNull(mapper.toMemberAssetDomain(asset).getAudit());
		
		asset = new MemberAsset();
		asset.setAudit(new Audit());
		asset.setAssetAttributes(new ArrayList<>());
		assertNull(mapper.toMemberAssetDomain(asset).getAudit().getCreatedBy());
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
	
	private MemberAsset buildMemberAssetEntity() {
		MemberAsset memberAsset = new MemberAsset();
		memberAsset.setAssetAttributes(buildAttributes());
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
		memberAsset.setStatus("active");
		memberAsset.setSourceSystem("source");
		memberAsset.setAudit(new Audit("ENGAGE", "ENGAGE", new Date(), new Date()));
		return memberAsset;
	}
	
	private List<AssetAttribute> buildAttributes() {
		List<AssetAttribute> attributes = new ArrayList<>();
		attributes.add(new AssetAttribute("Discover", "Discover"));
		return attributes;
	}

}
