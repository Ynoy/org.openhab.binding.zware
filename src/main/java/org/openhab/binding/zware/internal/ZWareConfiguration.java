/**
 * Copyright (c) 2014,2018 by the respective copyright holders.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.zware.internal;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ZWareConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author CheneyHao - Initial contribution
 */
public class ZWareConfiguration {
    private final Logger logger = LoggerFactory.getLogger(ZWareConfiguration.class);

    public String usrname;
    public String passwd;
    public String hostAddress;

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public String getPasswrd() {
        return passwd;
    }

    public void setPasswrd(String passwrd) {
        this.passwd = passwrd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(ZWareBindingConstants.UserUsrname, this.getUsrname())
                .append(ZWareBindingConstants.UserPasswd, this.getPasswrd())
                .append(ZWareBindingConstants.HOSTS, this.getHostAddress()).toString();
    }
}
