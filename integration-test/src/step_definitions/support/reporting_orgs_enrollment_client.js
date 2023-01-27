const {post, get, del} = require("./common");

function healthCheckInfo() {
    return get(`/info`, {
        params: { frameworkType: 'micronaut' },
        headers: {
            'Ocp-Apim-Subscription-Key': process.env.ORG_ENROLLMENT_SUBSCRIPTION_KEY
        }
    })
}

function getOrganizations() {
    return get(`/organizations`, {
        params: { frameworkType: 'micronaut' },
        headers: {
            'Ocp-Apim-Subscription-Key': process.env.ORG_ENROLLMENT_SUBSCRIPTION_KEY
        }
    })
}

function getOrganization(idOrg) {
    return get(`/organizations/${idOrg}`, {
        params: { frameworkType: 'micronaut' },
        headers: {
            'Ocp-Apim-Subscription-Key': process.env.ORG_ENROLLMENT_SUBSCRIPTION_KEY
        }
    })
}

function createOrganization(idOrg) {
    return post(`/organizations/${idOrg}`, {
        params: { frameworkType: 'micronaut' },
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Ocp-Apim-Subscription-Key': process.env.ORG_ENROLLMENT_SUBSCRIPTION_KEY
        },
    })
}

function removeOrganization(idOrg) {
    return del(`/organizations/${idOrg}`, {
        params: { frameworkType: 'micronaut' },
        headers: {
            'Ocp-Apim-Subscription-Key': process.env.ORG_ENROLLMENT_SUBSCRIPTION_KEY
        }
    })
}


module.exports = {
	healthCheckInfo,
	getOrganizations,
    getOrganization,
    createOrganization,
    removeOrganization
}
