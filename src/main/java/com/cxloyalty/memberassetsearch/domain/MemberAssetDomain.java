package com.cxloyalty.memberassetsearch.domain;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties
@JsonInclude(value = Include.NON_NULL)
@Component
public class MemberAssetDomain {
	
	private Map<String, String> meta;
	private Map<String, String> assets;
	private List<AssetAttributeDomain> attributes;
	private AuditDomain audit;
	
}
