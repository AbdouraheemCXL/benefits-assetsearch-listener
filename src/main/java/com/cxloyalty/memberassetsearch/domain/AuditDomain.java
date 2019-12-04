/**
 * 
 */
package com.cxloyalty.memberassetsearch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author afall
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditDomain {
	private String createdBy;
	private String modifiedBy;
	private Long createdDate;
	private Long modifiedDate;
}
