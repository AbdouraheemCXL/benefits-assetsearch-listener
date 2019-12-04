package com.cxloyalty.memberassetsearch.adaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cxloyalty.memberassetsearch.constant.AssetConstants;
import com.cxloyalty.memberassetsearch.domain.MemberAssetDomain;
import com.cxloyalty.memberassetsearch.domain.SearchParameters;
import com.cxloyalty.util.exception.BaseException;

import reactor.core.publisher.Mono;

/**
 * @author afall
 *
 */
@Service
public class AssetSearchAdaptor implements IAssetSearchAdaptor {
	
	private WebClient webClient;

	@Value("${fetch-asset-details-url}")
	private String assetDetailsUrl;

	@Autowired
	public AssetSearchAdaptor() {
		this.webClient = WebClient.builder()
								.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
								.build();		
	}

	@Override
	public Mono<MemberAssetDomain> getAssetDetailsByAssetId(SearchParameters<String> params) throws BaseException {
		return webClient.get()
					.uri(assetDetailsUrl, params.getParams().get(2))
					.header(HttpHeaders.CONTENT_TYPE, "application/json")
					.headers(x -> {
						x.add(AssetConstants.X_VISIBILITY_SCOPE_KEY, params.getParams().get(0));
						x.add(AssetConstants.CORRELATION_ID, params.getParams().get(1));
					})
					.retrieve()
					.bodyToMono(MemberAssetDomain[].class)
					.flatMap(asset -> asset.length > 0 ? Mono.just(asset[0]) : Mono.empty());	 
	}

}
