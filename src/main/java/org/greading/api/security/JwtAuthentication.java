package org.greading.api.security;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class JwtAuthentication {

    public final Long id;

    public final String memberId;

    public final String email;

    public JwtAuthentication(Long id, String memberId, String email) {
        notNull(id, "Id must be provided");
        notBlank(memberId, "Member id must be provided");
        notBlank(email, "Email must be provided.");

        this.id = id;
        this.memberId = memberId;
        this.email = email;
    }
}
