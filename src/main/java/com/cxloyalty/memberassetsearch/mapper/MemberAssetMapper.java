/**
 * 
 */
package com.cxloyalty.memberassetsearch.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cxloyalty.memberassetsearch.constant.AssetType;
import com.cxloyalty.memberassetsearch.constant.AssetConstants;
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
@Component
public class MemberAssetMapper implements IMemberAssetMapper {

	@Override
	public MemberAsset toMemberAssetDocument(MemberAssetDomain memberAssetDomain) {
		if(memberAssetDomain == null) {
			return new MemberAsset();
		} else {
			MemberAsset memberAsset = getMeta(memberAssetDomain);
			if (memberAssetDomain.getAttributes() != null) {
				List<AssetAttribute> attributes = memberAssetDomain.getAttributes().stream()
																					.map(this::setAttributeDetails)
																					.collect(Collectors.toList());
				memberAsset.setAssetAttributes(attributes);		
			}	
			memberAsset.setAudit(buildAudit(memberAssetDomain.getAudit()));		
			return memberAsset;
		}
	}

	@Override
	public MemberAssetDomain toMemberAssetDomain(MemberAsset memberAsset) {
		if(memberAsset == null) {
			return new MemberAssetDomain();
		}
		MemberAssetDomain memberAssetDomain = new MemberAssetDomain();
		Map<String, String> meta = new HashMap<>();
		if (memberAsset.getAssetId() != null) {
			meta.put(AssetConstants.ASSET_ID, memberAsset.getAssetId());
		}		
		if (memberAsset.getAssetName() != null) {
			meta.put(AssetConstants.ASSET_NAME, memberAsset.getAssetName());
		}
		if (memberAsset.getSourceSystem() != null) {
			meta.put(AssetConstants.SOURCE_SYSTEM_ID, memberAsset.getSourceSystem());
		}
		if (memberAsset.getStatus() != null) {
			meta.put(AssetConstants.ASSET_STATUS, memberAsset.getStatus());
		}
		if (memberAsset.getMemberId() != null) {
			meta.put(AssetConstants.MEMBER_ID, String.valueOf(memberAsset.getMemberId()));
		}
		if (memberAsset.getMembershipId() != null) {
			meta.put(AssetConstants.MEMBERSHIP_ID, String.valueOf(memberAsset.getMembershipId()));
		}
		if (memberAsset.getCategoryTag() != null) {
			meta.put(AssetConstants.CATEGORY_TAG, memberAsset.getCategoryTag());
		}
		if (memberAsset.getAssetType() != null) {
			meta.put(AssetConstants.ASSET_TYPE, memberAsset.getAssetType());
		}
		memberAssetDomain.setMeta(meta);
		memberAssetDomain.setAttributes(buildAttributesDomain(memberAsset.getAssetAttributes()));
		memberAssetDomain.setAudit(buildAuditDomain(memberAsset.getAudit()));	
		return memberAssetDomain;
	}

	private MemberAsset getAssetDetails(MemberAsset memberAsset, Map<String, String> assets) {
		if (memberAsset.getAssetType() != null) {
			if (memberAsset.getAssetType().equalsIgnoreCase(AssetType.ASSET_HELIX_PROTECTEDCARD.getAssetTypeValue())
					&& assets.get(AssetConstants.CARD_NUM) != null) {
				memberAsset.setMaskedDisplayValue(assets.get(AssetConstants.CARD_NUM));
			}
			if (memberAsset.getAssetType().equalsIgnoreCase(AssetType.ASSET_HELIX_BANKACCOUNT.getAssetTypeValue())
					&& assets.get(AssetConstants.ACCOUNT_NUM) != null) {
				memberAsset.setMaskedDisplayValue(assets.get(AssetConstants.ACCOUNT_NUM));
			}
		}
		return memberAsset;
	}

	private MemberAsset getMeta(MemberAssetDomain memberAssetDomain) {
		MemberAsset memberAsset = new MemberAsset();
		
		if(memberAssetDomain.getMeta() == null) {
			return memberAsset;
		}
		if (memberAssetDomain.getMeta().get(AssetConstants.ASSET_ID) != null) {
			memberAsset.setAssetId(memberAssetDomain.getMeta().get(AssetConstants.ASSET_ID));
		}
		if (memberAssetDomain.getMeta().get(AssetConstants.ASSET_TYPE) != null) {
			memberAsset.setAssetType(memberAssetDomain.getMeta().get(AssetConstants.ASSET_TYPE));
		}
		if (memberAssetDomain.getMeta().get(AssetConstants.ASSET_NAME) != null) {
			memberAsset.setAssetName(memberAssetDomain.getMeta().get(AssetConstants.ASSET_NAME));
		}
		if (memberAssetDomain.getMeta().get(AssetConstants.CATEGORY_TAG) != null) {
			memberAsset.setCategoryTag(memberAssetDomain.getMeta().get(AssetConstants.CATEGORY_TAG));
		}
		if (memberAssetDomain.getMeta().get(AssetConstants.MEMBER_ID) != null) {
			memberAsset.setMemberId(Long.parseLong(memberAssetDomain.getMeta().get(AssetConstants.MEMBER_ID)));
		}
		if (memberAssetDomain.getMeta().get(AssetConstants.MEMBERSHIP_ID) != null) {
			memberAsset.setMembershipId(Long.parseLong(memberAssetDomain.getMeta().get(AssetConstants.MEMBERSHIP_ID)));
		}
		if (memberAssetDomain.getMeta().get(AssetConstants.ASSET_STATUS) != null) {
			memberAsset.setStatus(memberAssetDomain.getMeta().get(AssetConstants.ASSET_STATUS));
		}
		if (memberAssetDomain.getMeta().get(AssetConstants.CREATED_BY) != null) {
			memberAsset.setCreatedBy(memberAssetDomain.getMeta().get(AssetConstants.CREATED_BY));
		}
		if (memberAssetDomain.getMeta().get(AssetConstants.UPDATED_BY) != null) {
			memberAsset.setUpdatedBy(memberAssetDomain.getMeta().get(AssetConstants.UPDATED_BY));
		}
		return getAssetDetails(memberAsset, memberAssetDomain.getAssets());
	}
	
	private AssetAttribute setAttributeDetails(AssetAttributeDomain assetAttributeDomain) {
		if(assetAttributeDomain == null) {
			return new AssetAttribute();
		} else {
			AssetAttribute attribute = new AssetAttribute();
			attribute.setAttributeName(assetAttributeDomain.getAttributeName() != null ? assetAttributeDomain.getAttributeName() : null);
			attribute.setAttributeValue(assetAttributeDomain.getAttributeValue() != null ? assetAttributeDomain.getAttributeValue() : null);
			return attribute;
		}
	}
	
	private AuditDomain buildAuditDomain(Audit audit) {
		if (audit == null) {
			return new AuditDomain();
		} else {
			AuditDomain auditDomain = new AuditDomain();
			auditDomain.setCreatedBy(audit.getCreatedBy() != null ? audit.getCreatedBy() : null);
			auditDomain.setCreatedBy(audit.getModifiedBy() != null ? audit.getModifiedBy() : null);
			auditDomain.setCreatedDate(audit.getCreatedDate() != null ? audit.getCreatedDate().getTime() : null);
			auditDomain.setModifiedDate(audit.getModifiedDate() != null ? audit.getModifiedDate().getTime() : null);
			return auditDomain;
		}
	}
	
	private Audit buildAudit(AuditDomain auditDomain) {
		if (auditDomain == null) {
			return new Audit();
		} else {
			Audit audit = new Audit();
			audit.setCreatedBy(auditDomain.getCreatedBy() != null ? auditDomain.getCreatedBy() : null);
			audit.setModifiedBy(auditDomain.getModifiedBy() != null ? auditDomain.getModifiedBy() : null);
			audit.setCreatedDate(auditDomain.getCreatedDate() != null ? new Date(auditDomain.getCreatedDate()) : null);
			audit.setModifiedDate(auditDomain.getModifiedDate() != null ? new Date(auditDomain.getModifiedDate()) : null);
			return audit;
		}
	}

	private List<AssetAttributeDomain> buildAttributesDomain(List<AssetAttribute> attributes) {
		
		if(attributes == null || attributes.isEmpty()) {
			return new ArrayList<>();
		} else {
			return attributes.stream()
							.map(this::buildAttributeDomain)
							.collect(Collectors.toList());
		}
	}
	
	private AssetAttributeDomain buildAttributeDomain(AssetAttribute attribute) {
		if(attribute == null) {
			return new AssetAttributeDomain();
		} else {
			AssetAttributeDomain attributeDomain = new AssetAttributeDomain();
			attributeDomain.setAttributeName(attribute.getAttributeName() != null ? attribute.getAttributeName() : null);
			attributeDomain.setAttributeValue(attribute.getAttributeValue() != null ? attribute.getAttributeValue() : null);
			return attributeDomain;
		}
	}

}
