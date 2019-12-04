/**
 * 
 */
package com.cxloyalty.memberassetsearch.constant;

/**
 * @author afall
 *
 */
public class AssetConstants {

	private AssetConstants() {
	}

	public static final String ASSET_INDEX = "asset";
	public static final String INDEX_TYPE = "_doc";
	public static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss";
	public static final String DATE_OF_BIRTH_FORMAT = "MMddyyyy";

	public static final String ASSET_ID = "asset_id";
	public static final String MEMBER_ID = "memberId";
	public static final String MEMBERSHIP_ID = "membershipId";
	public static final String ASSET_NAME = "name";
	public static final String SOURCE_SYSTEM_ID = "sourceSystemId";
	public static final String CATEGORY_TAG = "category_tag";
	public static final String ASSET_TOKEN = "asset_token";
	public static final String ASSET_TYPE = "asset_type";
	public static final String MASKED_DISPLAY_VALUE = "masked_display_value";
	public static final String ASSET_STATUS = "asset_status";
	public static final String CREATED_BY = "created_by";
	public static final String UPDATED_BY = "updated_by";
	public static final String SEARCH_KEY = "search_key";
	public static final String CARD_NUM = "CardNumber";
	public static final String ACCOUNT_NUM = "AccountNumber";

	public static final String CORRELATION_ID = "correlationId";
	public static final String ORG_ID = "orgId";
	public static final String X_VISIBILITY_SCOPE_KEY = "X-Visibility-Scope-Key";
	public static final String X_API_KEY = "x-api-as-key";

	public static final String MEMBER_CREATED_TOPIC_NAME = "memberasset.created";
	public static final String MEMBER_UPDATED_TOPIC_NAME = "memberasset.updated";
	public static final String MEMBER_LISTENER_FACTORY_NAME = "kafkaListenerContainerFactory";

	public static final String BASE_EXCEPTION_CREATE_MESSAGE = "EVENT = ERROR; msg = An Exception occurred while saving Member Asset in Index ";
	public static final String BASE_EXCEPTION_UPDATE_MESSAGE = "EVENT = ERROR; msg = An Exception occurred while updating Member Asset in Index ";
	public static final String EXCEPTION_CREATE_MESSAGE = "EVENT = ERROR; msg = An Exception occurred while saving Member Asset in Index ";
	public static final String EXCEPTION_UPDATE_MESSAGE = "EVENT = ERROR; msg = An Exception occurred while updating Member Asset in Index ";
	public static final String PLAYLOAD_RECEIVED_MSG = "Message Received for Indexing Member Asset : ";
	public static final String CREATE_INDEX_SUCCESS_MSG = "Member Asset Successfully Created in Index";
	public static final String CREATE_INDEX_ERROR_MSG = "An Error occured while creating Member Asset in Index";
	public static final String UPDATE_INDEX_SUCCESS_MSG = "Member Asset Successfully Updated in Index";
	public static final String UPDATE_INDEX_ERROR_MSG = "An Error occured while updating Member Asset in Index";

}
