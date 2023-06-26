const {post, get, del} = require("./common");

function healthCheckInfo() {
    let config = {
        params: {
            "frameworkType": "micronaut"
        },
        headers: {
            'Ocp-Apim-Subscription-Key': process.env.ORG_ENROLLMENT_SUBSCRIPTION_KEY
        }
    };
    return get(`/info`, config);
}

function getOrganizations() {
    let config = {
        params: {
            "frameworkType": "micronaut"
        },
        headers: {
            'Ocp-Apim-Subscription-Key': process.env.ORG_ENROLLMENT_SUBSCRIPTION_KEY
        }
    };
    return get(`/organizations`, config);
}

function getOrganization(idOrg) {
    let config = {
        params: {
            "frameworkType": "micronaut"
        },
        headers: {
            'Ocp-Apim-Subscription-Key': process.env.ORG_ENROLLMENT_SUBSCRIPTION_KEY
        }
    };
    return get(`/organizations/${idOrg}`, config);
}

function createOrganization(idOrg) {
    let config = {
        params: {
            "frameworkType": "micronaut"
        },
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Ocp-Apim-Subscription-Key': process.env.ORG_ENROLLMENT_SUBSCRIPTION_KEY
        }
    };
    return post(`/organizations/${idOrg}`, {}, config);
}

function removeOrganization(idOrg) {
    let config = {
        params: {
            "frameworkType": "micronaut"
        },
        headers: {
            'Content-Type': 'application/json',
            'Ocp-Apim-Subscription-Key': process.env.ORG_ENROLLMENT_SUBSCRIPTION_KEY
        }
    };
    return del(`/organizations/${idOrg}`, config);
}


module.exports = {
	healthCheckInfo,
	getOrganizations,
    getOrganization,
    createOrganization,
    removeOrganization
}
