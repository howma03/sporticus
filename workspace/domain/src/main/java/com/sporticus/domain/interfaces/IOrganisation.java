package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;
import java.util.Date;

/**
 * Created by mark on 17/02/2017.
 */
public interface IOrganisation {

    static IOrganisation COPY(final IOrganisation from, final IOrganisation to) {
        if(from == null) {
            return null;
        }
        if(to == null) {
            return null;
        }
        BeanUtils.copyProperties(from, to);
        if(from.isEnabled() != null) {
            to.setEnabled(from.isEnabled());
        }

        return to;
    }

    default Long getId() {
        return null;
    }

	String getUrlFragment();

	Date getCreated();

    IOrganisation setCreated(Date created);

    String getCreatedString();

    String getName();

    IOrganisation setName(String name);

    Long getOwnerId();

    IOrganisation setOwnerId(Long ownerId);

    default IOrganisation setEnabled(final Boolean flag) {
        return this;
    }

    default Boolean isEnabled() {
        return false;
    }

    String getAddress();

    IOrganisation setAddress(String address);

    String getDomain();

    IOrganisation setDomain(String domain);

    IOrganisation setUrlFragment(String urlFragment);

    IOrganisation setPostcode(String postcode);

    String getPostcode();
}
