package com.cxloyalty.memberassetsearch.constant;

import lombok.Getter;

/**
 * @author afall
 *
 */
@Getter
public enum AssetType {

	ASSET_HELIX_PROTECTEDCARD("Asset_Helix_ProtectedCard"),
	ASSET_HELIX_BANKACCOUNT("Asset_Helix_BankAccount"),
	CSS_PAYMENTCARD("CSS_PaymentCard"),
	CSS_BANKACCOUNT_US("CSS_BankAccount_US"),
	CSS_MEMBERNAME("CSS_MemberName"),
	CSS_DOB("CSS_DOB"),
	CSS_POSTALADRESS("CSS_PostalAddress"),
	CSS_EMAILADRESS("CSS_EmailAddress"),
	CSS_KEYTAG("CSS_KeyTag"),
	CSS_IDENTITYCARD("CSS_IdentityCard"),
	CSS_PHONENUMBER("CSS_PhoneNumber"),
	CSS_PHONENUMBERMOBILE("CSS_PhoneNumberMobile"),
	CSS_PASSPORT("CSS_Passport"),
	CSS_DRIVERLICENCE("CSS_DriversLicense"),
	CSS_ADULTSSN_US("CSS_AdultSSN_US"),
	CSS_NATIONALID("CSS_NationalId"),
	CSS_CHILDSSN_US("CSS_ChildSSN_US");
	
	private String assetTypeValue;
	
	private AssetType(String assetTypeValue) {
		this.assetTypeValue = assetTypeValue;
	}

}