/**
 * 
 */
package com.cxloyalty.memberassetsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxloyalty.memberassetsearch.adaptor.IAssetSearchAdaptor;
import com.cxloyalty.memberassetsearch.document.MemberAsset;
import com.cxloyalty.memberassetsearch.domain.MemberAssetDomain;
import com.cxloyalty.memberassetsearch.domain.SearchParameters;
import com.cxloyalty.memberassetsearch.mapper.IMemberAssetMapper;
import com.cxloyalty.memberassetsearch.repository.IMemberAssetIndexRepository;
import com.cxloyalty.util.exception.BaseException;

import reactor.core.publisher.Mono;

/**
 * @author afall
 *
 */
@Service
public class AssetService implements IAssetService {

	private IMemberAssetIndexRepository repository;
	private IAssetSearchAdaptor adaptor;
	private IMemberAssetMapper mapper;

	@Autowired
	public AssetService(IMemberAssetIndexRepository repository, IAssetSearchAdaptor adaptor, IMemberAssetMapper mapper) {
		this.repository = repository;
		this.adaptor = adaptor;
		this.mapper = mapper;
	}

	@Override
	public Mono<MemberAssetDomain> saveAsset(SearchParameters<String> parameters) throws BaseException {	
		Mono<MemberAssetDomain> memberAssetDomain = adaptor.getAssetDetailsByAssetId(parameters);
		return memberAssetDomain
				.flatMap(asset -> setVisibilityScopeKeyAndCorrelationId(mapper.toMemberAssetDocument(asset), parameters))
				.flatMap(assetToSave -> repository.save(assetToSave))			
				.flatMap(result -> result.getAssetId() != null ? Mono.just(mapper.toMemberAssetDomain(result)) : Mono.empty());	
	}

	@Override
	public Mono<MemberAssetDomain> updateAsset(SearchParameters<String> parameters) throws BaseException {
		Mono<MemberAssetDomain> memberAssetDomain = adaptor.getAssetDetailsByAssetId(parameters);
		return memberAssetDomain
				.flatMap(asset -> setVisibilityScopeKeyAndCorrelationId(mapper.toMemberAssetDocument(asset), parameters))
				.flatMap(assetToSave -> repository.save(assetToSave))			
				.flatMap(result -> result.getAssetId() != null ? Mono.just(mapper.toMemberAssetDomain(result)) : Mono.empty());	
	}
	
	private Mono<MemberAsset> setVisibilityScopeKeyAndCorrelationId(MemberAsset memberAsset, SearchParameters<String> params) {
		
		if(memberAsset != null && params != null) {
			memberAsset.setVisibilityScopeKey(params.getParams().get(0));
			memberAsset.setCorrelationId(params.getParams().get(1));
		}
		
		return Mono.just(memberAsset);
	}

}
