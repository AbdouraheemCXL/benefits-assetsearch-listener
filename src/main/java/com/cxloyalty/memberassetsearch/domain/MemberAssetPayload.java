/**
 * 
 */
package com.cxloyalty.memberassetsearch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author afall
 *
 */
@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
public class MemberAssetPayload {
	
	private String assetId;
	private String correlationId;
	private String visibilityScopeKey;
	
}
