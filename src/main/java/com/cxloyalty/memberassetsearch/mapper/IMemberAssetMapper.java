/**
 * 
 */
package com.cxloyalty.memberassetsearch.mapper;

import com.cxloyalty.memberassetsearch.document.MemberAsset;
import com.cxloyalty.memberassetsearch.domain.MemberAssetDomain;

/**
 * @author afall
 *
 */
public interface IMemberAssetMapper {
	
	public MemberAsset toMemberAssetDocument(MemberAssetDomain memberAssetDomain);
	public MemberAssetDomain toMemberAssetDomain(MemberAsset memberAsset);

}
