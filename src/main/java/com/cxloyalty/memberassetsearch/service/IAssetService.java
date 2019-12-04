/**
 * 
 */
package com.cxloyalty.memberassetsearch.service;

import com.cxloyalty.memberassetsearch.domain.MemberAssetDomain;
import com.cxloyalty.memberassetsearch.domain.SearchParameters;
import com.cxloyalty.util.exception.BaseException;

import reactor.core.publisher.Mono;

/**
 * @author afall
 *
 */
public interface IAssetService {
	
	public Mono<MemberAssetDomain> saveAsset(SearchParameters<String> parameters) throws BaseException;
	public Mono<MemberAssetDomain> updateAsset(SearchParameters<String> parameters) throws BaseException;

}
