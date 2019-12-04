/**
 * 
 */
package com.cxloyalty.memberassetsearch.repository;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

import com.cxloyalty.memberassetsearch.document.MemberAsset;

/**
 * @author afall
 *
 */
public interface IMemberAssetIndexRepository extends ReactiveElasticsearchRepository<MemberAsset, String> {
}
