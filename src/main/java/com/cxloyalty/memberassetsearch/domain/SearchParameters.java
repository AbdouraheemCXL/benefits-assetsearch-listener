/**
 * 
 */
package com.cxloyalty.memberassetsearch.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Builder
@Getter
public class SearchParameters<T> {
	
	@Singular
	private List<T> params ;
	
}
