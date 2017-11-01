/**
 * My JavaScript mysports application
 *
 * @module mysports
 */

/** @namespace Namespace for Sporticus classes and functions. */
var Sporticus = Sporticus || {};

Sporticus = {

    init: function (baseUrl, verifyPath, failureUrl) {
        this.baseUrl = baseUrl;
        this.verifyPath = verifyPath;
        this.failureUrl = failureUrl;
    },

    defaultSuccessFn: function () {
        $('.modal.in').modal('hide');
    },

    defaultFailureFn: function (message) {
        alert(message);
    },

    /**
     * Test to see if the user has a valid session
     */
    ajaxCallWrapper: function (checkUrl, failureFunction, successFunction) {
        $.ajax({
            url: checkUrl,
            type: 'GET',
            data: '',
            dataType: 'json',
            async: false,
            beforeSend: function (x) {
                if (x) {
                    x.overrideMimeType("application/json;charset=UTF-8");
                }
            },
            success: function (result) {
                successFunction();
            },
            error: function () {
                failureFunction();
            },
            statusCode: {
                404: function () {
                    failureFunction();
                },
                406: function () {
                    failureFunction();
                }
            }
        }).done(function (data) {
        });
    },

    restCall: function (methodType, serviceUrl, inData, successFunction, failureFunction) {
        var me = this;
        this.ajaxCallWrapper(this.baseUrl + "/" + this.verifyPath, function () {
            window.location = me.failureUrl;
        }, function () {
            $.ajax({
                url: serviceUrl,
                type: methodType,
                data: inData ? JSON.stringify(inData) : null,
                dataType: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                cache: false,
                success: function (result) {
                    if (successFunction) {
                        successFunction(result);
                    }
                },
                failure: function (result) {
                    if (failureFunction) {
                        failureFunction(result);
                    }
                },
                statusCode: {
                    404: function () {
                        this.failure(null);
                    },
                    406: function () {
                        this.failure(null);
                    }
                }
            }).done(function () {
            });
        })
    },


    groupsUrl: function () {
        return this.baseUrl + "/group/";
    },

    readGroups: function (successFunction, failureFunction) {
        this.restCall('GET', this.groupsUrl(), null, successFunction, failureFunction);
    },

    createsport: function (sport, successFunction, failureFunction) {
        this.restCall('POST', this.sportsUrl(), sport, successFunction, failureFunction);
    },

    readsport: function (id, successFunction, failureFunction) {
        this.restCall('GET', this.sportsUrl() + id, null, successFunction, failureFunction);
    },

    readsports: function (successFunction, failureFunction) {
        this.restCall('GET', this.sportsUrl(), null, successFunction, failureFunction);
    },

    updatesport: function (sport, successFunction, failureFunction) {
        this.restCall('PUT', this.sportsUrl() + sport.id, sport, successFunction, failureFunction);
    },


    summaryUrl: function () {
        return this.baseUrl + "/report/summary/"
    },

    readSummary: function (successFunction, failureFunction) {
        this.restCall('GET', this.summaryUrl(), null, successFunction, failureFunction);
    },

    readProfile: function (successFunction, failureFunction) {
        this.restCall('GET', this.baseUrl + "/user/profile/", null, successFunction, failureFunction);
    },

    updateProfile: function (profile, successFunction, failureFunction) {
        this.restCall('PUT', this.baseUrl + "/user/profile/", profile, successFunction, failureFunction);
    },


    /**
     * Management functions
     */

    userManagementUrl: function () {
        return this.baseUrl + "/management/user/";
    },

    createUserManagement: function (user, successFunction, failureFunction) {
        this.restCall('POST', this.userManagementUrl(), user, successFunction, failureFunction);
    },

    readUserManagement: function (id, successFunction, failureFunction) {
        this.restCall('GET', this.userManagementUrl() + id, null, successFunction, failureFunction);
    },

    readUsersManagement: function (successFunction, failureFunction) {
        this.restCall('GET', this.userManagementUrl(), null, successFunction, failureFunction);
    },

    updateUserManagement: function (user, successFunction, failureFunction) {
        this.restCall('PUT', this.userManagementUrl() + user.id, user, successFunction, failureFunction);
    },


    organisationUrl: function () {
        return this.baseUrl + "/organisation/";
    },

    createOrganisation: function (organisation, successFunction, failureFunction) {
        this.restCall('POST', this.organisationUrl(), organisation, successFunction, failureFunction);
    },

    readOrganisations: function (successFunction, failureFunction) {
        this.restCall('GET', this.organisationUrl(), null, successFunction, failureFunction);
    },

    readOrganisation: function (id, successFunction, failureFunction) {
        this.restCall('GET', this.organisationUrl() + id, null, successFunction, failureFunction);
    },

    updateOrganisation: function (organisation, successFunction, failureFunction) {
        this.restCall('PUT', this.organisationUrl() + organisation.id, organisation, successFunction, failureFunction);
    },


    groupsManagementUrl: function () {
        return this.baseUrl + "/management/group/";
    },

    groupsForOrganisationManagementUrl: function () {
        return this.groupsManagementUrl() + "organisation/";
    },

    groupMembershipsForUserManagementUrl: function () {
        return this.groupsManagementUrl() + "membership/user/";
    },

    groupMembershipsForGroupManagementUrl: function () {
        return this.groupsManagementUrl() + "membership/group/";
    },


    // Groups

    readGroupsManagement: function (successFunction, failureFunction) {
        this.restCall('GET', this.groupsManagementUrl(), null, successFunction, failureFunction);
    },

    createGroupManagement: function (group, successFunction, failureFunction) {
        this.restCall('POST', this.groupsManagementUrl(), group, successFunction, failureFunction);
    },

    readGroupManagement: function (id, successFunction, failureFunction) {
        this.restCall('GET', this.groupsManagementUrl() + id, null, successFunction, failureFunction);
    },

    updateGroupManagement: function (group, successFunction, failureFunction) {
        this.restCall('PUT', this.groupsManagementUrl() + group.id, group, successFunction, failureFunction);
    },

    // Membership

    createGroupMemberManagement: function (group, successFunction, failureFunction) {
        this.restCall('POST', this.groupsManagementUrl() + "membership/", group, successFunction, failureFunction);
    },

    readGroupMemberManagement: function (id, successFunction, failureFunction) {
        this.restCall('GET', this.groupsManagementUrl() + "membership/" + id, null, successFunction, failureFunction);
    },

    updateGroupMemberManagement: function (group, successFunction, failureFunction) {
        this.restCall('PUT', this.groupsManagementUrl() + "membership/" + group.id, group, successFunction, failureFunction);
    }


};