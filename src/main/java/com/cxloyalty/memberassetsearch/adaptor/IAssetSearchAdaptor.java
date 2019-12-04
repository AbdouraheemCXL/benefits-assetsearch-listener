package com.cxloyalty.memberassetsearch.adaptor;

import com.cxloyalty.memberassetsearch.domain.MemberAssetDomain;
import com.cxloyalty.memberassetsearch.domain.SearchParameters;
import com.cxloyalty.util.exception.BaseException;

import reactor.core.publisher.Mono;

/**
 * @author afall
 *
 */
public interface IAssetSearchAdaptor {

	public Mono<MemberAssetDomain> getAssetDetailsByAssetId(SearchParameters<String> params) throws BaseException;

}
