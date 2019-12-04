package com.cxloyalty.memberassetsearch.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author afall
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "asset", type = "_doc")
@ToString
public class MemberAsset {

	@Id
	private String assetId;
	private int orgId;
	private String assetType;
	private String assetToken;
	private String assetName;
	private String visibilityScopeKey;
	private String correlationId;
	private String sourceSystem;
	private String maskedDisplayValue;
	private String categoryTag;
	private String status;
	private Long memberId;
	private Long membershipId;
	private String searchKey;
	private String createdBy;
	private String updatedBy;
	private List<AssetAttribute> assetAttributes;
	private Audit audit;
	
}
