package com.cxloyalty.memberassetsearch.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.cxloyalty.memberassetsearch.constant.AssetConstants;
import com.cxloyalty.memberassetsearch.domain.MemberAssetDomain;
import com.cxloyalty.memberassetsearch.domain.MemberAssetPayload;
import com.cxloyalty.memberassetsearch.domain.SearchParameters;
import com.cxloyalty.memberassetsearch.service.IAssetService;
import com.cxloyalty.util.exception.BaseException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MemberAssetIndexListener {
	
	private IAssetService service;

	@Autowired
	public MemberAssetIndexListener(IAssetService service) {
		this.service = service;
	}

	@KafkaListener(topics = AssetConstants.MEMBER_CREATED_TOPIC_NAME, containerFactory = AssetConstants.MEMBER_LISTENER_FACTORY_NAME)
	public void saveIndex(MemberAssetPayload memberAssetPayload) {
		log.info(AssetConstants.PLAYLOAD_RECEIVED_MSG + memberAssetPayload);
		try {
			SearchParameters<String> params = SearchParameters.<String>builder()
															.param(memberAssetPayload.getVisibilityScopeKey())
															.param(memberAssetPayload.getCorrelationId())
															.param(memberAssetPayload.getAssetId())
															.build();
			Mono<MemberAssetDomain> mbr = service.saveAsset(params);
			mbr.subscribe(memberAsset -> assetCreationMessageLogging(memberAsset, AssetConstants.MEMBER_CREATED_TOPIC_NAME));

		} catch (BaseException e) {
			log.error(AssetConstants.BASE_EXCEPTION_CREATE_MESSAGE, e);
		} catch (Exception e) {
			log.error(AssetConstants.EXCEPTION_CREATE_MESSAGE, e);
		}
	}

	@KafkaListener(topics = AssetConstants.MEMBER_UPDATED_TOPIC_NAME, containerFactory = AssetConstants.MEMBER_LISTENER_FACTORY_NAME)
	public void updateIndex(MemberAssetPayload memberAssetPayload) {
		log.info(AssetConstants.PLAYLOAD_RECEIVED_MSG + memberAssetPayload);
		try {
			SearchParameters<String> params = SearchParameters.<String>builder()
					.param(memberAssetPayload.getVisibilityScopeKey())
					.param(memberAssetPayload.getCorrelationId())
					.param(memberAssetPayload.getAssetId())
					.build();
			Mono<MemberAssetDomain> mbr = service.saveAsset(params);
			mbr.subscribe(memberAsset -> assetCreationMessageLogging(memberAsset, AssetConstants.MEMBER_UPDATED_TOPIC_NAME));

		} catch (BaseException e) {
			log.error(AssetConstants.BASE_EXCEPTION_UPDATE_MESSAGE, e);
		} catch (Exception e) {
			log.error(AssetConstants.EXCEPTION_UPDATE_MESSAGE, e);
		}
	}
	
	private void assetCreationMessageLogging(MemberAssetDomain memberAsset, String action) {

		if (action.equalsIgnoreCase(AssetConstants.MEMBER_CREATED_TOPIC_NAME)) {

			if (memberAsset != null && memberAsset.getMeta() != null
					&& memberAsset.getMeta().get(AssetConstants.ASSET_ID) != null) {
				log.info(AssetConstants.CREATE_INDEX_SUCCESS_MSG);
			} else {
				log.error(AssetConstants.CREATE_INDEX_ERROR_MSG);
			}

		} else if (action.equalsIgnoreCase(AssetConstants.MEMBER_UPDATED_TOPIC_NAME)) {

			if (memberAsset != null && memberAsset.getMeta() != null
					&& memberAsset.getMeta().get(AssetConstants.ASSET_ID) != null) {
				log.info(AssetConstants.UPDATE_INDEX_SUCCESS_MSG);
			} else {
				log.error(AssetConstants.UPDATE_INDEX_ERROR_MSG);
			}

		}
	}

}
